package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.weique.overhaul.v2.di.component.DaggerStaffInfoComponent;
import com.weique.overhaul.v2.mvp.contract.StaffInfoContract;
import com.weique.overhaul.v2.mvp.presenter.StaffInfoPresenter;

import com.weique.overhaul.v2.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 员工信息
 */
public class StaffInfoActivity extends BaseActivity<StaffInfoPresenter> implements StaffInfoContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStaffInfoComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_staff_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(message);
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
}
