package com.example.speechrecognition.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.airbnb.lottie.LottieAnimationView;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.example.speechrecognition.utils.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

import timber.log.Timber;

import static com.example.speechrecognition.utils.ControlViewUtils.SPEECH_LANGUAGE;
import static com.example.speechrecognition.utils.ControlViewUtils.SPEECH_MODE;

/**
 * 自定义录音文件
 */
public class RecordView extends LottieAnimationView {
    // 语音听写对象
    private SpeechRecognizer mIat;
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_TO_CANCEL = 3;
    private static final int DISTANCE_Y_CANCEL = 50;
    private int mCurrentState = STATE_NORMAL;

    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    //按下和移动时的时间，用于判断是否是长按事件
    long timeDown, timeMove;
    //是否是长按事件
    boolean isLongClick;

    // 已经开始录音
    private boolean isRecording = false;
    private float mTime = 0;
    // 是否触发了onLongClick，准备好了

    private RecordClickPopupWindow.RecorePopupListener mRecorePopupListener = null;

    public RecordView(Context context) {
        super(context, null);
    }

    public RecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
//使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(context, mInitListener);
//设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数；或直接清空所有参数，具体可参考 DEMO 的示例。
        mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
        mIat.setParameter(SpeechConstant.SUBJECT, null);
//设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
//此处engineType为“cloud”
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
//设置语音输入语言，zh_cn为简体中文
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//设置结果返回语言
        String language = SPUtils.getInstance().getString(SPEECH_LANGUAGE, "mandarin");
        mIat.setParameter(SpeechConstant.ACCENT, language);
// 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
//取值范围{1000～10000}
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
//设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
//自动停止录音，范围{0~10000}
        mIat.setParameter(SpeechConstant.VAD_EOS, "2000");
//设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");
        String mode = SPUtils.getInstance().getString(SPEECH_MODE, "click_mode");
        if (mode.equals("long_click_mode")) {
            isLongClick = true;
        } else {
            isLongClick = false;
        }


        setOnLongClickListener(v -> {
            if (isLongClick) {
                startRecording();
            }
            return false;
        });
    }


    public void setmRecorePopupListener(RecordClickPopupWindow.RecorePopupListener mRecorePopupListener) {
        this.mRecorePopupListener = mRecorePopupListener;
    }

    /**
     * 听写监听器。
     */

    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        private boolean isPlay = true;

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            Timber.e("开始说话");
//            textView.setText("倾听中");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            Timber.e(error.getPlainDescription(true));
            /**
             *  不松手就继续
             */
            if (error.getErrorCode() == 10118) {
                Timber.e("isAnimating" + isAnimating());
                mIat.startListening(mRecognizerListener);
            }
        }

        @Override
        public void onEndOfSpeech() {
//            lottie_view.cancelAnimation();
//            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
//            textView.setText("识别中");
            Timber.e("结束说话");

        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
//            textView.setText("识别结束");
            printResult(results, isLast);

        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            Timber.e("当前正在说话，音量大小：" + volume);
            Timber.e("返回音频数据：" + data.length);
            if (data.length > 0 && isPlay && volume > 0) {
                isPlay = false;
                Timber.e("play");
//                lottie_view.playAnimation();
            }else {
//                Timber.e("isAnimating" + isAnimating());
//                if(!isAnimating()){
//                    if (mIat.isListening()) {
//                        mIat.stopListening();
//                    }
//                }
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void printResult(RecognizerResult results, boolean isLast) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        if (ObjectUtils.isNotEmpty(mRecorePopupListener) && isLast) {
            mRecorePopupListener.onRecordCreate(resultBuffer.toString());
            mIatResults.clear();
            /**
             * 停止录音
             */
            mIat.stopListening();
        }

    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {

            }
        }
    };

    /**
     * 直接复写这个监听函数
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isLongClick) {
            int action = event.getAction();
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    timeDown = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    timeMove = System.currentTimeMillis();
                    long durationMs = timeMove - timeDown;
                    //Log.d(TAG, "onTouch: durationMs="+durationMs);
                    if (durationMs > 500) {
                        changeState(STATE_RECORDING);
                    }
                    if (isRecording) {
                        // 根据x，y来判断用户是否想要取消
                        if (wantToCancel(x, y)) {
                            changeState(STATE_WANT_TO_CANCEL);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    // 首先判断是否有触发onLongClick事件，没有的话直接返回reset
//                    if (!mReady) {
//                        reset();
//                        return super.onTouchEvent(event);
//                    }
                    // 如果按的时间太短，还没准备好或者时间录制太短，就离开了，则显示这个dialog
//                if (mCurrentState == STATE_RECORDING) {//正常录制结束
//                    mIat.stopListening();
//                    cancelAnimation();
//                } else if (mCurrentState == STATE_WANT_TO_CANCEL) {
//                    mIat.stopListening();
//                    cancelAnimation();
//                }
                    reset();// 恢复标志位
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    /*
     * 恢复标志位以及状态
     */
    private void reset() {
        isRecording = false;
        changeState(STATE_NORMAL);
        mTime = 0;
    }

    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {// 判断是否在左边，右边，上边，下边
            return true;
        }
        if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        }
        return false;
    }

    /**
     * 开始录音
     */
    private void startRecording() {
        try {
            //开始动画
            playAnimation();
        } catch (Exception e) {

        }
    }


    //改变状态
    private void changeState(int state) {
        if (mCurrentState != state) {
            mCurrentState = state;
            switch (mCurrentState) {
                case STATE_NORMAL:
//                    this.setText("按住 说话");
                    setProgress(0);
                    cancelAnimation();
                    if (mIat.isListening()) {
                        mIat.stopListening();
                    }
                    break;
                case STATE_RECORDING:
                    //开始识别，并设置监听器
                    isRecording = true;
                    if (!mIat.isListening()) {
                        mIat.startListening(mRecognizerListener);
                    }
                    break;
                case STATE_WANT_TO_CANCEL:
//                    this.setText("松开手指，取消录音");
                    // dialog want to cancel
                    /* 取消动画

                     */

                    setProgress(0);
                    cancelAnimation();
                    if (mIat.isListening()) {
                        mIat.stopListening();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
