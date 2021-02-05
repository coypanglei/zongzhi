package com.weique.overhaul.v2.mvp.ui.activity.party;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerPartyCenterDissComponent;
import com.weique.overhaul.v2.mvp.contract.PartyCenterDissContract;
import com.weique.overhaul.v2.mvp.presenter.PartyCenterDissPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 党建 - 个人 - 吐槽(意见反馈)
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author  GK
 */
@Route(path = RouterHub.APP_PARTYCENTERDISSACTIVITY)
public class PartyCenterDissActivity extends BaseActivity<PartyCenterDissPresenter> implements PartyCenterDissContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPartyCenterDissComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_party_center_diss;
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
