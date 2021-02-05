package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.BuildConfig;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ACacheConstant;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.app.utils.MetaDataUtil;

public class AgreementDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private ScrollView scrollView;
    private TextView agreementT;
    private TextView agreementContent;
    private TextView cancelBtn;
    private TextView confirmBtn;

    public AgreementDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.agreement_dialog, null);
        setContentView(view);
        initView(view);
    }

    private void initView(View view) {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        scrollView = view.findViewById(R.id.scroll_view);
        agreementT = view.findViewById(R.id.agreement_t);
        agreementContent = view.findViewById(R.id.agreement_content);
        cancelBtn = view.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(this);
        confirmBtn = view.findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(this);

        if (MetaDataUtil.isChinaElectronics()) {
            agreementContent.setText(ArmsUtils.getString(getContext(),R.string.agreement_dialog_content_zhongdian));
        }else{
            agreementContent.setText(ArmsUtils.getString(getContext(),R.string.agreement_dialog_content));
        }
        initAgreement();
    }

    private void initAgreement() {
        try {
            SpannableString spanStrStart = new SpannableString(mContext.getString(R.string.agreement_dialog_content1));
            SpannableString spanStrClick = new SpannableString(mContext.getString(R.string.agreement_dialog_content2));
            spanStrClick.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ARouter.getInstance().build(RouterHub.APP_COMMONWEBVIEWACTIVITY)
                            .withString(ARouerConstant.TITLE, mContext.getString(R.string.user_agreement))
                            .withString(ARouerConstant.WEB_URL, BuildConfig.SERVER_URL + Constant.USER_AGREEMENT)
                            .navigation();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置颜色
                    ds.setColor(ArmsUtils.getColor(mContext, R.color.blue_4D8FF7));
                    //去掉下划线，默认是带下划线的
                    ds.setUnderlineText(false);
                    //设置字体背景
                    //ds.bgColor = Color.parseColor("#FF0000");
                }
            }, 0, spanStrClick.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableString spanStrClick2 = new SpannableString(mContext.getString(R.string.agreement_dialog_content3));
            spanStrClick2.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ARouter.getInstance().build(RouterHub.APP_COMMONWEBVIEWACTIVITY)
                            .withString(ARouerConstant.TITLE, mContext.getString(R.string.privacy_policy))
                            .withString(ARouerConstant.WEB_URL, BuildConfig.SERVER_URL + Constant.PRIVACY_POLICY)
                            .navigation();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置颜色
                    ds.setColor(ArmsUtils.getColor(mContext, R.color.blue_4D8FF7));
                    //去掉下划线，默认是带下划线的
                    ds.setUnderlineText(false);
                    //设置字体背景
    //				ds.bgColor = Color.parseColor("#FF0000");
                }
            }, 0, spanStrClick.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableString spanStrEnd = new SpannableString(mContext.getString(R.string.agreement_dialog_content4));
            agreementT.append(spanStrStart);
            agreementT.append(spanStrClick);
            agreementT.append(spanStrClick2);
            agreementT.append(spanStrEnd);
            agreementT.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                ArmsUtils.exitApp();
                break;
            case R.id.confirm_btn:
                ACache.get(mContext).put(ACacheConstant.AGREE_WITH_THE_PROTOCOL, ACacheConstant.AGREE_WITH_THE_PROTOCOL);
                dismiss();
                break;
            default:
        }
    }
}
