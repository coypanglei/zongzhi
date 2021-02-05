package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;

/**
 * @author GreatKing
 */
public class EditTextDialog extends Dialog {
    private Context mContext;
    private TextView title;
    private TextView impose;
    private EditText content;
    private Button confirm;
    private OnConfirmClickListener OnConfirmClickListener;

    public interface OnConfirmClickListener {
        void onConfirmClick(String contents);
    }

    public EditTextDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        this.mContext = context;
    }

    public void setOnConfirmClickListener(EditTextDialog.OnConfirmClickListener onConfirmClickListener) {
        OnConfirmClickListener = onConfirmClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_order_handle, null);
            setContentView(view);
            //alertDialog是否可以点击外围消失
            setCanceledOnTouchOutside(true);
            setCancelable(true);
            initView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        if (content != null) {
            content.setText("");
        }
        super.show();
    }

    private void initView(View view) {
        try {
            title = view.findViewById(R.id.title);
            title.setVisibility(View.GONE);
            content = view.findViewById(R.id.content);
            content.setFocusable(true);
            content.setFocusableInTouchMode(true);
            content.requestFocus();
            impose = view.findViewById(R.id.impose);
            confirm = view.findViewById(R.id.confirm);
            confirm.setText("回复");
            confirm.setOnClickListener(v -> {
                String s = content.getText().toString();
                if (StringUtil.isNullString(s)) {
                    ArmsUtils.makeText("请填写回复内容");
                    return;
                }
                OnConfirmClickListener.onConfirmClick(s);
                dismiss();
            });
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    impose.setText(s.toString().length() + "/150");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
