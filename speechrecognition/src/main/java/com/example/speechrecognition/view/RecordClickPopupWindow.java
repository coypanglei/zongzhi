package com.example.speechrecognition.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.example.speechrecognition.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AlphaConfig;
import razerdp.util.animation.AnimationHelper;
import timber.log.Timber;

import static com.example.speechrecognition.utils.ControlViewUtils.SPEECH_MODE;

/**
 * @author Administrator
 */
public class RecordClickPopupWindow extends BasePopupWindow {

    private EditText editText;
    private RecordView lottieAnimationView;
    private MyTextWatcher myTextWatcher;

    private RecorePopupListener mRecorePopupListener = null;


    public RecordClickPopupWindow(Context context) {
        super(context);
        try {
            setOutSideDismiss(false);
            setOutSideTouchable(true);
            setPopupGravity(Gravity.CENTER);
            setBackgroundColor(Color.TRANSPARENT);
            editText = findViewById(R.id.edit);
            lottieAnimationView = findViewById(R.id.animation_view);

            String mode = SPUtils.getInstance().getString(SPEECH_MODE, "click_mode");
            if (!mode.equals("long_click_mode")) {
                lottieAnimationView.setOnClickListener(view -> {
                    if (ObjectUtils.isNotEmpty(mRecorePopupListener)) {
                        RecordRecognitionPopupWindow recordRecognitionPopupWindow = new RecordRecognitionPopupWindow(context, mRecorePopupListener);
                        if (null != recordRecognitionPopupWindow && !recordRecognitionPopupWindow.isShowing()) {
                            recordRecognitionPopupWindow.showPopupWindow();
                        }
                        dismiss();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 回调 语音接口
     */
    public interface RecorePopupListener {
        void onRecordCreate(String str);


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
    public View onCreateContentView() {
        return createPopupById(R.layout.public_record_click);
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
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
//                printResult(results,isLast);
        }

        /**
         * 识别回调错误.
         */
        @Override
        public void onError(SpeechError error) {
            Timber.e(error.getPlainDescription(true));

        }

    };


    public void setContent(EditText edit) {
        int index = edit.getSelectionStart();
        String text = edit.getText().toString();
        String startStr = text.substring(0, index);
        String endStr = text.substring(index);
        if (ObjectUtils.isNotEmpty(myTextWatcher)) {
            myTextWatcher.setStartStr(startStr);
            myTextWatcher.setEndStr(endStr);
        }
    }


    /**
     * 长按应为没有切换 所以需要把语音识别的内容清空
     *
     * @param content
     */
    public void clearContent(String content) {
//        String text = editText.getText().toString().replace(content,"");
//        editText.setText(text);
        String text = editText.getText().toString();
        String str = text + content;
        editText.setText(str);
        editText.setSelection(str.length());
    }

    public void setEditText(EditText edit, RecorePopupListener recorePopupListener) {
        try {

            mRecorePopupListener = recorePopupListener;
            lottieAnimationView.setmRecorePopupListener(mRecorePopupListener);
            KeyboardUtils.showSoftInput(editText);
            editText.setInputType(edit.getInputType());
            editText.requestFocus();
            editText.setText("");
            KeyboardUtils.registerSoftInputChangedListener(getContext(), height -> {
                if (height > 0) {

                } else {
                    dismiss();
                }
            });
            if (ObjectUtils.isNotEmpty(myTextWatcher)) {
                editText.removeTextChangedListener(myTextWatcher);
            }
            myTextWatcher = new MyTextWatcher(edit);
            editText.addTextChangedListener(myTextWatcher);
            editText.setOnKeyListener((view, keyCode, event) -> {
                try {
                    if (event != null && event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {

                        int index = edit.getSelectionStart();
                        String startStr;
                        String endStr;
                        String content = edit.getText().toString();
                        startStr = content.substring(0, index);
                        endStr = content.substring(index);
                        String addStr = startStr.substring(0, startStr.length() - 1);
                        String string = addStr + endStr;
                        edit.setText(string);
                        edit.setSelection(addStr.length());
                        setContent(edit);
                    }
                    if ((event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                        Timber.e("换行" + editText.getText().toString());
//                        setContent(edit);
                        //处理事件
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        KeyboardUtils.hideSoftInput(editText);
        editText.removeTextChangedListener(myTextWatcher);
    }

    private class MyTextWatcher implements TextWatcher {

        private EditText mEditText;
        private String text;
        private int index;
        private String startStr;
        private String endStr;


        public MyTextWatcher(EditText editText) {
            mEditText = editText;
            text = editText.getText().toString();
            index = editText.getSelectionStart();
            startStr = text.substring(0, index);
            endStr = text.substring(index);

        }

        public String getStartStr() {
            return startStr;
        }

        public void setStartStr(String startStr) {
            this.startStr = startStr;
        }

        public String getEndStr() {
            return endStr;
        }

        public void setEndStr(String endStr) {
            this.endStr = endStr;
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
                index = editText.getSelectionStart();
                String addStr = startStr + s.toString();
                String string = addStr + endStr;
                mEditText.setText(string);
                mEditText.setSelection(addStr.length());
            }
        }
    }
}
