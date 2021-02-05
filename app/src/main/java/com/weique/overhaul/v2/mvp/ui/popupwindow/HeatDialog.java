package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.weique.overhaul.v2.R;

/**
 * @author GreatKing
 */
public class HeatDialog extends Dialog {
    private Context mContext;
    private EditText content;
    private TextView confirmBtn;
    private TextView cancelBtn;
    private OnConfirmClickListener OnConfirmClickListener;

    /**
     * 0无协同部门  1. 协同部门未道现场  2.  到现场
     */
    private int synergyStatus = 0;

    public interface OnConfirmClickListener {
        void onConfirmClick(HeatDialog heatDialog, String contents);
    }

    public HeatDialog(@NonNull Context context, OnConfirmClickListener listener) {
        super(context, R.style.dialog_style);
        this.mContext = context;
        this.OnConfirmClickListener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
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

    private void initView(View view) {
        try {
            content = view.findViewById(R.id.content);
            content.setFocusable(true);
            content.setFocusableInTouchMode(true);
            content.requestFocus();
            confirmBtn = view.findViewById(R.id.confirm_btn);
            cancelBtn = view.findViewById(R.id.cancel_btn);
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    content.setText(s.toString().length() + " ℃");
                }
            });
            //
            confirmBtn.setOnClickListener(v -> {
                OnConfirmClickListener.onConfirmClick(this, content.getText().toString());
            });
            cancelBtn.setOnClickListener(v -> {
                dismiss();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
