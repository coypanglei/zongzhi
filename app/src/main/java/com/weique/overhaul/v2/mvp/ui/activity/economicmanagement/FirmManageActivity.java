package com.weique.overhaul.v2.mvp.ui.activity.economicmanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EnumConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerFirmManageComponent;
import com.weique.overhaul.v2.mvp.contract.FirmManageContract;
import com.weique.overhaul.v2.mvp.presenter.FirmManagePresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.fragment.economicmanagement.EconomicManagementBaseInfoFragment;
import com.weique.overhaul.v2.mvp.ui.fragment.economicmanagement.EconomicManagementTableFragment;

import java.util.ArrayList;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 企业管理界面
 * ================================================
 *
 * @author Administrator
 */
@Route(path = RouterHub.APP_FIRM_MANAGE_ACTIVITY)
public class FirmManageActivity extends BaseActivity<FirmManagePresenter> implements FirmManageContract.View {

    private final String[] FIRM_TAB = {"基本信息", "需求", "审批", "执法", "税收"};
    @BindView(R.id.tl_2)
    SlidingTabLayout tl2;
    @BindView(R.id.vp)
    ViewPager vp;

    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments;
    /**
     * 上个界面跳转的id
     */
    @Autowired(name = ARouerConstant.ID)
    String id;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFirmManageComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_firm_manage;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle(getString(R.string.enterprise_management));
        mFragments = new ArrayList<>();
        mFragments.add(EconomicManagementBaseInfoFragment.newInstance(EnumConstant.EconomicManagementEnum.PROJECT_INFO_BASE, id));
        mFragments.add(EconomicManagementTableFragment.newInstance(EnumConstant.EconomicManagementEnum.FIRM_MANAGE_DEMAND, id));
        mFragments.add(EconomicManagementTableFragment.newInstance(EnumConstant.EconomicManagementEnum.FIRM_MANAGE_APPROVE, id));
        mFragments.add(EconomicManagementTableFragment.newInstance(EnumConstant.EconomicManagementEnum.FIRM_MANAGE_LAW, id));
        mFragments.add(EconomicManagementTableFragment.newInstance(EnumConstant.EconomicManagementEnum.FIRM_MANAGE_ECONOMICS, id));
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, FIRM_TAB);
        vp.setAdapter(mAdapter);
        tl2.setViewPager(vp, FIRM_TAB);
        vp.setOffscreenPageLimit(FIRM_TAB.length);
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
}
