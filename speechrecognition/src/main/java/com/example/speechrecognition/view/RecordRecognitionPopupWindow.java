package com.example.speechrecognition.view;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.example.speechrecognition.R;
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

import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AlphaConfig;
import razerdp.util.animation.AnimationHelper;
import timber.log.Timber;

import static com.example.speechrecognition.utils.ControlViewUtils.SPEECH_LANGUAGE;

/**
 * 语音识别 高德语音识别
 */

public class RecordRecognitionPopupWindow extends BasePopupWindow {

    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    // 语音听写对象
    private SpeechRecognizer mIat;

    private TextView textView;

    private RecordClickPopupWindow.RecorePopupListener mRecorePopupListener = null;

    private LottieAnimationView lottie_view;

    public RecordRecognitionPopupWindow(Context context, RecordClickPopupWindow.RecorePopupListener recoredPopupListener) {
        super(context);
        try {
            setPopupGravity(Gravity.CENTER);
//            setBackground(Color.TRANSPARENT);
            //初始化识别无UI识别对象
//使用SpeechRecognizer对象，可根据回调消息自定义界面；
            mIat = SpeechRecognizer.createRecognizer(context, mInitListener);


            mRecorePopupListener = recoredPopupListener;
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
            String language = SPUtils.getInstance().getString(SPEECH_LANGUAGE,"mandarin");
            mIat.setParameter(SpeechConstant.ACCENT, language);
// 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
//取值范围{1000～10000}
            mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
//设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
//自动停止录音，范围{0~10000}
            mIat.setParameter(SpeechConstant.VAD_EOS, "3000");
//设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
            mIat.setParameter(SpeechConstant.ASR_PTT, "1");
            TextView startTextView =findViewById(R.id.tv_tack);
            startTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //开始识别，并设置监听器
                    if(!mIat.isListening()) {
                        mIat.startListening(mRecognizerListener);
                    }
                    startTextView.setVisibility(View.GONE);
//                    if (null != mIat) {
//                        // 退出时释放连接
//                        mIat.cancel();
//                        mIat.destroy();
//                    }
//                    dismiss();
                }
            });
            textView = findViewById(R.id.tv_load);
            lottie_view = findViewById(R.id.lottie_view);

        } catch (Exception e) {
            e.printStackTrace();
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
     * 听写监听器。
     */

    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        private boolean isPlay = true;

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            Timber.e("开始说话");
            textView.setText("倾听中");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            Timber.e(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            lottie_view.cancelAnimation();
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            textView.setText("识别中");
            Timber.e("结束说话");

        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            textView.setText("识别结束");
            printResult(results, isLast);

        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            Timber.e("当前正在说话，音量大小：" + volume);
            Timber.e("返回音频数据：" + data.length);
            if (data.length > 0 && isPlay && volume > 0) {
                isPlay = false;
                Timber.e("play");
                lottie_view.playAnimation();
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
            dismiss();
        }

    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.public_record_view);
    }


    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation()
                .withAlpha(AlphaConfig.IN)
                .toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationHelper.asAnimation()
                .withAlpha(AlphaConfig.OUT)
                .toDismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }

}
