package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.di.component.DaggerCollectDynamicComponent;
import com.weique.overhaul.v2.mvp.contract.CollectDynamicContract;
import com.weique.overhaul.v2.mvp.presenter.CollectDynamicPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 采集信息动态界面
 * <p>
 * ================================================
 * Description:
 * <p>
 * ================================================
 * @author  GK
 */
public class CollectDynamicActivity extends BaseActivity<CollectDynamicPresenter> implements CollectDynamicContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCollectDynamicComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_collect_dynamic;
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
