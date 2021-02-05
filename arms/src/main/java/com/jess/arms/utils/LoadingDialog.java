package com.jess.arms.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.jess.arms.R;


/**
 * 全局单例dialog
 *
 * @author GK
 */
public class LoadingDialog extends Dialog {
    private SpinKitView spinkit;
    private TextView loadingtext;
    /**
     * 懒汉模式，需要的时候再创建，属于懒加载
     * 但是会造成线程安全问题，多线程会创建多个instance
     * 使用synchronized修饰方法，解决多线程问题
     * 但是 new LoadingDialog() 不是原子操作，所以如果不加volatile会出问题
     */
    private static volatile LoadingDialog loadingDialog;
    private String mMsg;

    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);

        //添加布局
        setContentView(R.layout.dialog_global_loading_layout);
        this.loadingtext = findViewById(R.id.loading_text);
        this.spinkit = findViewById(R.id.spin_kit);
        Sprite drawable = SpriteFactory.create(Style.CIRCLE);
        spinkit.setIndeterminateDrawable(drawable);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        try {
            if (!isShowing()) {
                if (TextUtils.isEmpty(mMsg)) {
                    loadingtext.setVisibility(View.GONE);
                } else {
                    loadingtext.setVisibility(View.VISIBLE);
                    loadingtext.setText(mMsg);
                }
                super.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Builder {

        private LoadingDialog mDialog;
        private Activity mActivity;

        public Builder(Activity activity, boolean canCancel) {
            mActivity = activity;
            mDialog = new LoadingDialog(activity);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(canCancel);
            if (canCancel) {
                mDialog.onBackPressed();
            }
        }

        /**
         * 显示 字符串
         *
         * @param msg msg
         * @return Builder
         */
        public Builder setLoadMessage(String msg) {
            mDialog.mMsg = msg;
            return this;
        }

        /**
         * dialog显示时点击 返回键 关闭activity
         *
         * @return Builder
         */
        public Builder setBackCloseMyself() {
            mDialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (event.getAction() == KeyEvent.ACTION_UP) {
                            mActivity.finish();
                        }
                        return true;
                    }
                    return false;
                }
            });
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public LoadingDialog create() {
            return mDialog;
        }
    }
}
