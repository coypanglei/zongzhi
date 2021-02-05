package com.weique.overhaul.v2.mvp.ui.activity.datastatistics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerDataStatisticsMainComponent;
import com.weique.overhaul.v2.mvp.contract.DataStatisticsMainContract;
import com.weique.overhaul.v2.mvp.presenter.DataStatisticsMainPresenter;
import com.weique.overhaul.v2.mvp.ui.fragment.datastatistics.AreaDetailsFragment;
import com.weique.overhaul.v2.mvp.ui.fragment.datastatistics.SummaryStatisticsFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/01/2020 17:17

 * ================================================
 * @author 43460
 */

@Route(path = RouterHub.APP_STATISTICS_ACTIVITY, name = "数据统计主页面")
public class DataStatisticsMainActivity extends BaseActivity<DataStatisticsMainPresenter> implements DataStatisticsMainContract.View {


    @BindString(R.string.data_report)
    String title;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tl_1)
    SlidingTabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindArray(R.array.data_statistic)
    String[] mTitles;


    private ArrayList<Fragment> mFragments = new ArrayList<>();


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDataStatisticsMainComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_data_statistics_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            setTitle(title);
            mFragments.add(SummaryStatisticsFragment.newInstance());
            mFragments.add(AreaDetailsFragment.newInstance());

            vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

            tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelect(int position) {
                    vp.setCurrentItem(position);
                }

                @Override
                public void onTabReselect(int position) {
                }
            });


            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tabLayout.setCurrentTab(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            vp.setCurrentItem(0);
            tabLayout.setViewPager(vp, mTitles, this, mFragments);
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


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
