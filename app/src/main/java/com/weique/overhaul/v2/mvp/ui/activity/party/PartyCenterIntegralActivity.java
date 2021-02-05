package com.weique.overhaul.v2.mvp.ui.activity.party;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerPartyCenterIntegralComponent;
import com.weique.overhaul.v2.mvp.contract.PartyCenterIntegralContract;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.PartyCenterIntegralPresenter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 党建  - 我的 - 积分
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author  GK
 */
@Route(path = RouterHub.APP_PARTYCENTERINTEGRALACTIVITY)
public class PartyCenterIntegralActivity extends BaseActivity<PartyCenterIntegralPresenter> implements PartyCenterIntegralContract.View {

    @BindView(R.id.integral_view)
    TextView integralView;
    @BindView(R.id.integral_today)
    TextView integralToday;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPartyCenterIntegralComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_party_center_integral;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        integralView.setText(String.valueOf(UserInfoUtil.getUserInfo().getSum()));
//        integralToday.setText(String.format(getResources().getString(R.string.today_integral_s), UserInfoUtil.getTodayAddIntegral()));
    }

    /**
     * 接收 刷新界面  事件
     */
    @Subscriber(tag = PartyCenterActivity.APP_PARTYCENTERACTIVITY_FRAGMENTS_ONE_REFRESH)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case PartyCenterActivity.PARTY_UPDATE_INTEGRAL_CODE:
                    integralView.setText(eventBusBean.getMessage());
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        ArmsUtils.makeText(message);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.go_and_see, R.id.go_and_answer})
    public void onClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.go_and_see:
                    finish();
                    EventBus.getDefault().post(new EventBusBean(PartyCenterActivity.NEWS_CENTER), RouterHub.APP_PARTYCENTERACTIVITY);
                    break;
                case R.id.go_and_answer:
                    ARouterUtils.navigation(this, RouterHub.APP_ANSWERACTIVITY);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
