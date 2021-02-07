package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ObjectUtils;
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
 * 只有一个的编辑框
 *
 * @author Administrator
 */
public class RecordPopupWindow extends BasePopupWindow {

    private RecordView recordview;
    private ImageView recordBtn;
    private TimerTask timeTask;
    private EditText editText;
    private MyTextWatcher myTextWatcher;

    private Timer timeTimer = new Timer(true);
    int countdown = 0;
    private Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            @SuppressLint("HandlerLeak") int db = (int) (Math.random() * 100);

            recordview.setVolume(db);
            countdown++;
        }
    };
    private MediaRecorder mRecorder;

    private String eventFileName = "/speech_recognition.mp4";
    private File file;


    public interface RecorePopupLisenter {
        void onRecordCreate(String path);
    }

    /**
     * @param context context
     * @param model   @RecordView MODEL_PLAY || MODEL_RECORD
     */
    @SuppressLint("ClickableViewAccessibility")
    public RecordPopupWindow(Context context, int model, RecordPopup.RecorePopupLisenter lisenter) {
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
            editText = findViewById(R.id.edit);
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
//                                dismiss();
                                Timber.e(file.getPath());
                            });
                            initMediaRecoder();
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            recordBtn.setImageResource(R.drawable.record_off);
                            stopRecording();
                            lisenter.onRecordCreate(file.getPath());
//                            dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setEditText(EditText edit) {
        try {
            KeyboardUtils.showSoftInput(editText);

            editText.requestFocus();
            editText.setText("");
            KeyboardUtils.registerSoftInputChangedListener(getContext(), height -> {
                if (height > 0) {

                } else {
                    dismiss();
                }
            });
            if (ObjectUtils.isNotEmpty(edit)) {
                myTextWatcher = new MyTextWatcher(edit);
                editText.addTextChangedListener(myTextWatcher);
                editText.setOnKeyListener((v, keyCode, event) -> {
                    try {
                        if (event.getAction() == KeyEvent.ACTION_DOWN&&keyCode == KeyEvent.KEYCODE_DEL) {
                            int index = edit.getSelectionStart();
                            if (index > 0) {
                                String startStr;
                                String endStr;
                                String content = edit.getText().toString();
                                startStr = content.substring(0, index);
                                endStr = content.substring(index);
                                String addStr = startStr.substring(0,startStr.length()-1);
                                String string = addStr + endStr;
                                edit.setText(string);
                                edit.setSelection(addStr.length());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private class MyTextWatcher implements TextWatcher {

        private EditText mEditText;
        private String text;
        private int index = 0;
        private String startStr;
        private String endStr;


        public MyTextWatcher(EditText editText) {
            mEditText = editText;
            text = editText.getText().toString();
            index = editText.getSelectionStart();
            startStr = text.substring(0, index);
            endStr = text.substring(index);

        }

        public MyTextWatcher() {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                Timber.e(s.toString());
                index = editText.getSelectionStart();
                String addStr = startStr + s.toString();
                String string = addStr + endStr;
                mEditText.setText(string);
                mEditText.setSelection(addStr.length());
            }
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

        super.onDismiss();
        handler.removeCallbacksAndMessages(null);
        editText.removeTextChangedListener(myTextWatcher);
        KeyboardUtils.hideSoftInput(editText);
        countdown = 0;
        stopRecording();
    }

    public void stopRecording() {
        if (recordview != null) {
            recordview.cancel();
            //倒计时取消,handle 内存泄漏
            timeTask.cancel();
        }

        if (mRecorder != null) {
            mRecorder.setOnErrorListener(null);
            mRecorder.setOnInfoListener(null);
            mRecorder.setPreviewDisplay(null);
            try {


                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;

            } catch (IllegalStateException e) {
                Log.e("IllegalStateException", e.getMessage() + "");
            } catch (RuntimeException e) {
                Log.e("RuntimeException", e.getMessage() + "");
            } catch (Exception e) {
                Log.e("Exception", e.getMessage() + "");
            } finally {
                mRecorder.release();
                mRecorder = null;
            }

        }
    }


    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.layout_record_popup_new);
    }
}
