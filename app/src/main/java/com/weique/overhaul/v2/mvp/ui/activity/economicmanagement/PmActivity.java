package com.weique.overhaul.v2.mvp.ui.activity.economicmanagement;

import android.annotation.SuppressLint;
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
import com.weique.overhaul.v2.di.component.DaggerPmComponent;
import com.weique.overhaul.v2.mvp.contract.PmContract;
import com.weique.overhaul.v2.mvp.presenter.PmPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.fragment.economicmanagement.EconomicManagementBaseInfoFragment;
import com.weique.overhaul.v2.mvp.ui.fragment.economicmanagement.EconomicManagementTableFragment;

import java.util.ArrayList;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 项目管理Activity
 * ================================================
 *
 * @author Administrator
 */
@Route(path = RouterHub.APP_PM_ACTIVITY)
public class PmActivity extends BaseActivity<PmPresenter> implements PmContract.View {

    private final String[] PM_TAB = {"基本信息", "项目进展", "检查情况","项目实况"};
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
        DaggerPmComponent
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
        setTitle("项目管理");
        mFragments = new ArrayList<>();
        mFragments.add(EconomicManagementBaseInfoFragment.newInstance(EnumConstant.EconomicManagementEnum.FIRM_INFO_BASE,id));
        mFragments.add(EconomicManagementTableFragment.newInstance(EnumConstant.EconomicManagementEnum.PM_MANAGE_PROGRESS,id));
        mFragments.add(EconomicManagementTableFragment.newInstance(EnumConstant.EconomicManagementEnum.PM_MANAGE_INSPECT_STATE,id));
        mFragments.add(EconomicManagementTableFragment.newInstance(EnumConstant.EconomicManagementEnum.FIRM_MANAGE_BASE,id));
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, PM_TAB);
        vp.setAdapter(mAdapter);
        tl2.setViewPager(vp, PM_TAB);
        vp.setOffscreenPageLimit(PM_TAB.length);
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
