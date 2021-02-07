package com.weique.overhaul.v2.mvp.ui.activity.enforcelaw;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.model.LatLng;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.service.localtion.LocSdkClient;
import com.weique.overhaul.v2.app.utils.UserGridUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerEnforceLawEventComponent;
import com.weique.overhaul.v2.mvp.contract.EnforceLawEventContract;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedLookBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EnforceLawEventPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventsReportedCrudActivity;
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.fragment.enforcelaw.EnforceLawLawListFragment;

import org.jetbrains.annotations.NotNull;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 事件案件 列表 activity
 * ================================================
 *
 * @author GreatKing
 */

@Route(path = RouterHub.APP_COMPREHENSIVE_LAW_ENFORCEMENT, name = "事件案件 列表")
public class EnforceLawEventActivity extends BaseActivity<EnforceLawEventPresenter> implements EnforceLawEventContract.View {


    @BindString(R.string.comprehensive_law_enforcement)
    String title;
    @BindArray(R.array.data_comprehensive_law_enforcement)
    String[] mTitles;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @BindView(R.id.tl_1)
    SlidingTabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.search_view)
    MaterialSearchView materialSearchView;

    private CountDownTimer timer;

    private String keyword;


    /**
     * 待定 添加按钮
     */
    List<String> titles = new ArrayList<>();

    /**
     * 是 新增还是  修改
     */
    private int action = -1;

    /**
     * 网格坐标json
     */
    private String gridJson;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEnforceLawEventComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_enforce_law_event;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            materialSearchView.setHint("请输入具体名称");
            setTitle(title);
//            mFragments.add(EnforceLawEventListFragment.newInstance());
            mFragments.add(EnforceLawLawListFragment.newInstance());

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
            initSearch();

//            if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.COMMUNITY) {
//                titles.add(mTitles[0]);
//            }
            if (UserInfoUtil.getUserInfo().isComprehensiveLawEnforcementOfficer()) {
                titles.add(mTitles[0]);
            }
            if (titles.size() == 0) {
                ivAdd.setVisibility(View.GONE);
            }
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.clearFocus();
            materialSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 初始化  搜索相关问题
     */
    private void initSearch() {
        materialSearchView.setVoiceSearch(false);
        materialSearchView.setEllipsize(true);
        materialSearchView.setHint(getString(R.string.input_search_content));
        materialSearchView.setSubmitOnClick(false);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keyword = query;
                EventBus.getDefault().post(new EventBusBean
                        (EventBusConstant.SEARCH_KEY, keyword), RouterHub.APP_EVENTSREPORTEDACTIVITY);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals(keyword)) {
                    if (timer != null) {
                        timer.cancel();
                    }
                    timer = new CountDownTimer(1000, 1000) {
                        @SuppressLint("StringFormatMatches")
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            keyword = newText;
                            EventBus.getDefault().post(new EventBusBean(EventBusConstant.SEARCH_KEY, keyword), RouterHub.APP_EVENTSREPORTEDACTIVITY);
                        }
                    }.start();
                }
                return false;
            }
        });


    }


    @OnClick({R.id.right_btn, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_btn:
                if (!materialSearchView.isSearchOpen()) {
                    materialSearchView.showSearch();
                }
                break;
                /*
                  选择类型

                 */
            case R.id.iv_add:
                ARouter.getInstance()
                        .build(RouterHub.APP_ENFORCELAWLAWADDACTIVITY)
                        .navigation();
               /* String[] toBeStored = titles.toArray(new String[titles.size()]);
                DialogFragmentHelper.showListDialog(getSupportFragmentManager(),
                        "综合执法类型", toBeStored, result -> {
                            if (result .equals("事件")) {
                                mPresenter.gridOperatorInformation();
                            } else if (result .equals("案件")) {
                                ARouter.getInstance()
                                        .build(RouterHub.APP_ENFORCELAWLAWADDACTIVITY)
                                        .navigation();
                            }
                        }, true);*/
                break;
            default:
                break;
        }
    }

    @Override
    public void getGridInfoSuccess(String pointsInJSON) {
        gridJson = pointsInJSON;
        mPresenter.getIsInGrid();
    }

    @Override
    public void setAddressCanChanged(Boolean o) {
        try {
            List<List<LatLng>> latLngLists = new ArrayList<>();
            List<PolygonOptions> ooPolygons = new ArrayList<>();
            UserGridUtil.gridJsonToListLatLng(gridJson, latLngLists, ooPolygons);
            // 若用百度定位sdk,需要在此初始化定位SDK
            LocSdkClient.getInstance(EnforceLawEventActivity.this).getLocationStart();
            BDLocation location =
                    LocSdkClient.getInstance(this).getLocationStart()
                            .getLastKnownLocation();
            if (location == null || latLngLists.size() <= 0) {
                ArmsUtils.makeText("获取您的定位失败");
                return;
            }
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            //不允许修改坐标  并 用户不在网格内
            if (!o && !UserGridUtil.pointInGrid(latLng, latLngLists)) {
                ArmsUtils.makeText("您不在负责区域内，无法上报事件");
            } else {
                EventsReportedLookBean bean = new EventsReportedLookBean();
                bean.setEnumOrderStatus(EventsReportedBean.ListBean.EventsReportedEnumNewBean.TS);
                ARouter.getInstance().build(RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
                        .withParcelable(EventsReportedCrudActivity.EVENTSREPORTEDLOOKBEAN, bean)
                        .withInt(ARouerConstant.STATUS, EventBusConstant.ADD)
                        .withString(MapActivity.POINTS_IN_JSON, gridJson)
                        .withBoolean(MapActivity.ADDRESS_CAN_CHANGED, o)
                        .navigation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 适配器
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {
        MyPagerAdapter(FragmentManager fm) {
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
