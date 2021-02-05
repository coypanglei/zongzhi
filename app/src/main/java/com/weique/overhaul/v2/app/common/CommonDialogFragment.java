package com.weique.overhaul.v2.app.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.ViewHolder;
import com.weique.overhaul.v2.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author GK
 * @description:
 * @date :2020/7/31 16:01
 */
public class CommonDialogFragment extends BaseNiceDialog {
    /**
     * 提醒
     */
    public static final int HINT = 0;
    /**
     * 警告
     */
    public static final int WARN = 1;
    /**
     * 危险
     */
    public static final int DANGER = 2;
    /**
     * 类型
     */
    private int type = HINT;
    private static final String TYPE_KEY = "type";
    private View.OnClickListener cancelBtnClick = v -> getDialog().dismiss();
    private String cancelBtnText = "取消";
    private View.OnClickListener confirmBtnClick = v -> getDialog().dismiss();
    private String confirmBtnText = "确定";
    private String title = "提示";
    private String content = "确定退出";
    private boolean cancelBtnHide = false;
    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
    /**
     * 路由跳转进入 行为判断
     */
    @IntDef({HINT, WARN, DANGER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DialogTypeEnum {
    }

    public static CommonDialogFragment newInstance(@DialogTypeEnum int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY, type);
        CommonDialogFragment dialog = new CommonDialogFragment();
        dialog.setArguments(bundle);
        return dialog;
    }

    public static CommonDialogFragment newInstance() {
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY, HINT);
        CommonDialogFragment dialog = new CommonDialogFragment();
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setOnComfirmClick(View.OnClickListener click) {
        this.confirmBtnClick = click;
    }

    public void setOnComfirmClick(String confirmText, View.OnClickListener click) {
        confirmBtnText = confirmText;
        this.confirmBtnClick = click;
    }

    public void setOnCancleClick(View.OnClickListener click) {
        this.cancelBtnClick = click;
    }

    public void setCancleHide() {
        cancelBtnHide = true;
    }

    public void setTitle(String titleText) {
        title = titleText;
    }

    public void setContent(String contentText) {
        content = contentText;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(TYPE_KEY);
        }
        if (getDialog() != null) {
            try {
                // 解决Dialog内存存泄漏
                getDialog().setOnDismissListener(null);
                getDialog().setOnCancelListener(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int intLayoutId() {
        return R.layout.common_dialog;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        try {
            holder.setText(R.id.title, title);
            holder.setText(R.id.content, content);
            holder.setText(R.id.confirm_btn, confirmBtnText);
            holder.setText(R.id.cancel_btn, cancelBtnText);
            holder.setOnClickListener(R.id.confirm_btn, confirmBtnClick);
            holder.setOnClickListener(R.id.cancel_btn, cancelBtnClick);
            TextView cancelBtn = holder.getView(R.id.cancel_btn);
            View secondLine = holder.getView(R.id.second_line);
            if (cancelBtnHide) {
                cancelBtn.setVisibility(View.GONE);
                secondLine.setVisibility(View.GONE);
            } else {
                cancelBtn.setVisibility(View.VISIBLE);
                secondLine.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
