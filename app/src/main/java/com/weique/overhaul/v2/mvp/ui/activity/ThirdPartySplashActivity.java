package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerThirdPartySplashComponent;
import com.weique.overhaul.v2.mvp.contract.ThirdPartySplashContract;
import com.weique.overhaul.v2.mvp.presenter.ThirdPartySplashPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * 三方登录 跳转到的闪屏页
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_THIRDPARTYSPLASHPRESENTER)
public class ThirdPartySplashActivity extends BaseActivity<ThirdPartySplashPresenter> implements ThirdPartySplashContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerThirdPartySplashComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_third_party_splash;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
//        Intent intent = getIntent();
        initIntent(intent);
    }

    private void initIntent(Intent intent) {
        if (null != intent && null != intent.getData()) {
            // uri 就相当于 web 页面中的链接
            Uri uri = intent.getData();
            String key1 = uri.getQueryParameter("key1");
            if (StringUtil.isNotNullString(key1)) {
                assert mPresenter != null;
                mPresenter.verifyThirdPartyId(key1);
            } else {
                ArmsUtils.makeText("为获取到您的身份id");
                finish();
            }
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        initIntent(intent);
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
