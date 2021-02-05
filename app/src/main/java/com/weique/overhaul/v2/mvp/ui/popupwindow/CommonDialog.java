package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;

public class CommonDialog extends Dialog {
    private Context mContext;
    private TextView title;
    private TextView content;
    private TextView cancelBtn;
    private TextView confirmBtn;
    private View secondLine;
    private String titleText;
    private String contentText;
    private String confirmBtnText;
    private String cancelBtnText;
    private boolean cancelable = true;


    public CommonDialog(Context context) {
        super(context, R.style.dialog_style);
        this.mContext = context;
        initView();
    }

    public interface OnPositiveClickListener {
        void onPositiveClick(View view, CommonDialog commonDialog);
    }

    public interface OnNegativeClickListener {
        void onNegativeClick(View view, CommonDialog commonDialog);
    }

    private boolean isProgressBarShow = true;
    private boolean isNegativeBtnShow = true;

    protected void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_dialog, null);
        setContentView(view);
        //alertDialog是否可以点击外围消失
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        title = view.findViewById(R.id.title);
        content = view.findViewById(R.id.content);
        cancelBtn = view.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(v -> {
            cancel();
            dismiss();
        });
        secondLine = view.findViewById(R.id.second_line);
        confirmBtn = view.findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(v -> {
            cancel();
            dismiss();
        });
    }

    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        show(this);
        super.show();
    }

    private void show(CommonDialog mDialog) {
        try {
            mDialog.content.setText(contentText);
            if (StringUtil.isNotNullString(mDialog.cancelBtnText)) {
                mDialog.cancelBtn.setText(mDialog.cancelBtnText);
            }
            if (!mDialog.isNegativeBtnShow) {
                mDialog.cancelBtn.setVisibility(View.GONE);
                mDialog.secondLine.setVisibility(View.GONE);
            } else {
                if (StringUtil.isNotNullString(cancelBtnText)) {
                    mDialog.cancelBtn.setText(cancelBtnText);
                }
            }
            if (!mDialog.isProgressBarShow) {
                mDialog.confirmBtn.setVisibility(View.GONE);
                mDialog.secondLine.setVisibility(View.GONE);
            } else {
                if (StringUtil.isNotNullString(confirmBtnText)) {
                    mDialog.confirmBtn.setText(confirmBtnText);
                }
            }
            if (StringUtil.isNullString(titleText)) {
                mDialog.title.setVisibility(View.GONE);
            } else {
                mDialog.title.setVisibility(View.VISIBLE);
                mDialog.title.setText(titleText);
            }
            mDialog.setCancelable(mDialog.cancelable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Builder {

        private CommonDialog commonDialog;

        public Builder(Context context) {
            commonDialog = new CommonDialog(context);
        }

        public Builder setTitle(String title) {
            commonDialog.titleText = title;
            return this;
        }

        public Builder setContent(String content) {
            commonDialog.contentText = content;
            return this;
        }

        /**
         * 设置确认按钮的回调
         *
         * @param onPositiveClickListener
         */
        public Builder setPositiveButton(OnPositiveClickListener onPositiveClickListener) {
            try {
                if (onPositiveClickListener != null) {
                    commonDialog.confirmBtn.setOnClickListener(v -> {
                        onPositiveClickListener.onPositiveClick(v, commonDialog);
                        commonDialog.dismiss();
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * 设置确认按钮的回调
         *
         * @param btnText,onClickListener
         */
        public Builder setPositiveButton(String btnText, OnPositiveClickListener onPositiveClickListener) {
            try {
                if (onPositiveClickListener != null) {
                    commonDialog.confirmBtnText = btnText;
                    commonDialog.confirmBtn.setOnClickListener(v -> {
                        onPositiveClickListener.onPositiveClick(v, commonDialog);
                        commonDialog.dismiss();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * 设置取消按钮的回掉
         *
         * @param onClickListener
         */
        public Builder setNegativeButton(OnNegativeClickListener onClickListener) {
            try {
                if (onClickListener != null) {
                    commonDialog.cancelBtn.setOnClickListener(v -> {
                        onClickListener.onNegativeClick(v, commonDialog);
                        commonDialog.dismiss();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }


        /**
         * 设置取消按钮的回调
         *
         * @param btnText,onClickListener
         */
        public Builder setNegativeButton(String btnText, OnNegativeClickListener onClickListener) {
            try {
                if (onClickListener != null) {
                    commonDialog.cancelBtnText = btnText;
                    commonDialog.cancelBtn.setOnClickListener(v -> {
                        onClickListener.onNegativeClick(v, commonDialog);
                        commonDialog.dismiss();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }


        /**
         * 设置是否显示取消按钮，默认显示
         *
         * @param isNegativeBtnShow
         */
        public Builder setNegativeBtnShow(boolean isNegativeBtnShow) {
            commonDialog.isNegativeBtnShow = isNegativeBtnShow;
            return this;
        }

        /**
         * 设置该对话框能否被Cancel掉，默认可以
         *
         * @param cancelable
         */
        public Builder setCancelable(boolean cancelable) {
            commonDialog.cancelable = cancelable;
            return this;
        }

        /**
         * 设置对话框被cancel对应的回调接口，cancel()方法被调用时才会回调该接口
         *
         * @param onCancelListener
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            commonDialog.setOnCancelListener(onCancelListener);
            return this;
        }

        /**
         * 设置对话框消失对应的回调接口，一切对话框消失都会回调该接口
         *
         * @param onDismissListener
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            commonDialog.setOnDismissListener(onDismissListener);
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public CommonDialog create() {
            return commonDialog;
        }
    }
}
