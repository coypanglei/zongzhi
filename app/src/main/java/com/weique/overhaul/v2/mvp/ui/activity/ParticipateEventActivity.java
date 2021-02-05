package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerParticipateEventComponent;
import com.weique.overhaul.v2.mvp.contract.ParticipateEventContract;
import com.weique.overhaul.v2.mvp.presenter.ParticipateEventPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.fragment.MyParticipateEventFragment;

import java.util.ArrayList;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 个人中心--参与事件
 *
 * @author GK
 */
@Route(path = RouterHub.APP_PARTICIPATEEVENTACTIVITY)
public class ParticipateEventActivity extends BaseActivity<ParticipateEventPresenter> implements ParticipateEventContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableLayout)
    SlidingTabLayout tableLayout;
    @BindView(R.id.vp)
    ViewPager vp;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"已办理", "已结束"};
    private MyPagerAdapter mAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerParticipateEventComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_participate_event;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("参与事件");
        addTabToTabLayout();
    }

    private void addTabToTabLayout() {
        try {
            mFragments.add(MyParticipateEventFragment.newInstance(MyParticipateEventFragment.UNDERWAY));
            mFragments.add(MyParticipateEventFragment.newInstance(MyParticipateEventFragment.COMPLETED));
            mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
            vp.setAdapter(mAdapter);
            tableLayout.setViewPager(vp, mTitles, this, mFragments);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
