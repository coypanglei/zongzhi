package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.customview.RecordView;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import razerdp.basepopup.BasePopupWindow;
import timber.log.Timber;

/**
 * 录音弹出框
 */
public class RecordPopup extends BasePopupWindow {

    private RecordView recordview;
    private ImageView recordBtn;
    private TimerTask timeTask;
    private Timer timeTimer = new Timer(true);
    int countdown = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int db = (int) (Math.random() * 100);

            recordview.setVolume(db);
            countdown++;
        }
    };
    private MediaRecorder mRecorder;
    private MediaPlayer player;

    private String eventFileName = "/audiorecordtest.mp4";
    private File file;


    public interface RecorePopupLisenter {
        void onRecordCreate(String path);
    }

    /**
     * @param context context
     * @param model   @RecordView MODEL_PLAY || MODEL_RECORD
     * @param path    path
     */
    @SuppressLint("ClickableViewAccessibility")
    public RecordPopup(Context context, int model, String path, RecorePopupLisenter lisenter) {
        super(context);
        try {
            if (model != RecordView.MODEL_PLAY && model != RecordView.MODEL_RECORD) {
                Timber.i("设置录音类型失败");
                return;
            }
            setPopupGravity(Gravity.BOTTOM);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            setBackgroundColor(ArmsUtils.getColor(context, R.color.transparent48));
            setOutSideDismiss(true);
            recordview = findViewById(R.id.record_view);
            recordview.setCountdownTime(120);
            recordview.setModel(model);
            recordBtn = findViewById(R.id.record_btn);
            if (model == RecordView.MODEL_RECORD) {
                recordBtn.setOnTouchListener((v, event) -> {
                    try {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            recordBtn.setImageResource(R.drawable.record_on);
                            recordview.start();
                            timeTimer.schedule(timeTask = new TimerTask() {
                                @Override
                                public void run() {
                                    Message msg = new Message();
                                    msg.what = 1;
                                    handler.sendMessage(msg);
                                }
                            }, 20, 20);
                            recordview.setOnCountDownListener(() -> {
                                Timber.i("倒计时结束");
                                dismiss();
                            });
                            initMediaRecoder();
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            recordBtn.setImageResource(R.drawable.record_off);
                            stopRecording();
                            lisenter.onRecordCreate(file.getPath());
                            dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
            } else {
                recordBtn.setOnClickListener(v -> {
                    player = new MediaPlayer();
                    try {
                        recordview.start();
                        player.setDataSource(path);
                        player.prepare();
                        player.start();
                    } catch (IOException e) {
                        Timber.e("prepare() failed");
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMediaRecoder() {
        try {
            file = new File(Constant.getDir() + eventFileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            mRecorder = new MediaRecorder();
            mRecorder.setOnErrorListener(null);
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setOutputFile(file.getAbsolutePath());
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mRecorder.setAudioChannels(1);
            // 设置录音文件的清晰度
            mRecorder.setAudioSamplingRate(44100);
            mRecorder.setAudioEncodingBitRate(192000);
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDismiss() {
        stopRecording();
        handler.removeCallbacksAndMessages(null);
        super.onDismiss();
    }

    public void stopRecording() {
        try {
            if (recordview != null) {
                recordview.cancel();
                //倒计时取消,handle 内存泄漏
                timeTask.cancel();
            }
            if (mRecorder != null) {
                mRecorder.setOnErrorListener(null);
                mRecorder.setOnInfoListener(null);
                mRecorder.setPreviewDisplay(null);
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            }
        } catch (IllegalStateException e) {
            Log.e("IllegalStateException", e.getMessage() + "");
        } catch (RuntimeException e) {
            Log.e("RuntimeException", e.getMessage() + "");
        } catch (Exception e) {
            Log.e("Exception", e.getMessage() + "");
        }

    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.layout_record_popup);
    }
}
