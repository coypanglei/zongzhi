package com.example.speechrecognition.utils;

import android.view.KeyEvent;
import android.widget.EditText;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;


public class ControlViewUtils {

    /**
     * 是否开启
     */
    private static boolean isOpen;


    public static final String OPEN_SPEECH = "open_speech";

    /**
     * 语音使用的语言 普通话 方言 天津 （没充钱）
     */
    public static final String SPEECH_LANGUAGE = "speech_language";


    /**
     * 语音使用的语言 普通话 方言 天津 （没充钱）
     */
    public static final String SPEECH_MODE = "speech_mode";

    public interface RecorePopupClickInterface {
        void onRecordCreate();
    }


    /**
     * 设置语音识别点击编辑显示
     */
    public static void setClick(final EditText editText, final RecorePopupClickInterface recorePopupClickInterface) {

        try {
            isOpen = SPUtils.getInstance().getBoolean(OPEN_SPEECH, true);
            if (isOpen) {
                editText.setOnClickListener(view -> {
                    if (ObjectUtils.isNotEmpty(recorePopupClickInterface)) {
                        recorePopupClickInterface.onRecordCreate();
                    }
                });
                editText.setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        // 此处为得到焦点时的处理内容
                        if (ObjectUtils.isNotEmpty(recorePopupClickInterface)) {
                            recorePopupClickInterface.onRecordCreate();
                        }
                    } else {
                        // 此处为失去焦点时的处理内容
                    }
                });
                editText.setOnKeyListener((view, keyCode, event) -> {
                    try {
                        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {

                            //                            recorePopupClickInterface.setContent(editText.getText().toString());
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

    /**
     * 插入文本
     */
    public static void setEditText(EditText edit, String recordText) {
        int index = edit.getSelectionStart();
        String startStr;
        String endStr;
        String content = edit.getText().toString();
        startStr = content.substring(0, index);
        endStr = content.substring(index);
        String addStr = startStr + recordText;
        String string = addStr + endStr;
        edit.setText(string);
        edit.setSelection(addStr.length());
    }


}
