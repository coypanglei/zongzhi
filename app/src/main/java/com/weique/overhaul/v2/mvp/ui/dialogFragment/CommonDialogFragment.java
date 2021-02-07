package com.weique.overhaul.v2.mvp.ui.dialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Administrator
 */
public class CommonDialogFragment extends DialogFragment {


    /**
     * 监听弹出窗是否被取消
     */
    private OnDialogCancelListener mCancelListener;

    public OnDialogCancelListener getmCancelListener() {
        return mCancelListener;
    }

    public void setmCancelListener(OnDialogCancelListener mCancelListener) {
        this.mCancelListener = mCancelListener;
    }

    /**
     * 回调获得需要显示的 dialog
     */
    private OnCallDialog mOnCallDialog;

    public interface OnDialogCancelListener {
        void onCancel();

        void onDismiss();
    }

    public interface OnCallDialog {
        Dialog getDialog(FragmentActivity context);
    }

    public static CommonDialogFragment newInstance(OnCallDialog callDialog, boolean cancelable) {
        return newInstance(callDialog, cancelable, null);
    }

    public static CommonDialogFragment newInstance(OnCallDialog callDialog, boolean cancelable, OnDialogCancelListener cancelListener) {
        CommonDialogFragment instance = new CommonDialogFragment();
        instance.setCancelable(cancelable);
        instance.mCancelListener = cancelListener;
        if (instance.mOnCallDialog == null) {
            instance.mOnCallDialog = callDialog;
        }
        return instance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (null == mOnCallDialog) {
            super.onCreate(savedInstanceState);
        }
        return mOnCallDialog.getDialog(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            Dialog dialog = getDialog();
            if (dialog != null) {

                // 在 5.0 以下的版本会出现白色背景边框，若在 5.0 以上设置则会造成文字部分的背景也变成透明
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    // 目前只有这两个 dialog 会出现边框
                    if (dialog instanceof ProgressDialog || dialog instanceof DatePickerDialog) {
                        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                }

                Window window = getDialog().getWindow();
                assert window != null;
                WindowManager.LayoutParams windowParams = window.getAttributes();
                windowParams.dimAmount = 0.0f;
                window.setAttributes(windowParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancel(@NotNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (mCancelListener != null) {
            mCancelListener.onCancel();
        }
    }


    @Override
    public void onDestroy() {
        if (mCancelListener != null) {
            mCancelListener.onDismiss();
        }
        super.onDestroy();
    }


}
