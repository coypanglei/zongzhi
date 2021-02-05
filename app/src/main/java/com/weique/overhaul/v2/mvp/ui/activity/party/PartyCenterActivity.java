package com.weique.overhaul.v2.mvp.ui.activity.party;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerPartyCenterComponent;
import com.weique.overhaul.v2.mvp.contract.PartyCenterContract;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.PartyCenterPresenter;
import com.weique.overhaul.v2.mvp.ui.fragment.party.PartyCenterMineTableFragment;
import com.weique.overhaul.v2.mvp.ui.fragment.party.PartyCenterNewsFragment;
import com.weique.overhaul.v2.mvp.ui.fragment.party.PartyCenterTopTableFragment;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 党建中心
 *
 * @author GK
 */
@Route(path = RouterHub.APP_PARTYCENTERACTIVITY)
public class PartyCenterActivity extends BaseActivity<PartyCenterPresenter> implements PartyCenterContract.View {

    /**
     * 对应 后台接口不可随意修改
     * <p>
     * 新闻中心
     */
    public static final int NEWS_CENTER = 0;
    /**
     * 主题教育
     */
    public static final int SUBJECT_EDUCATION = 1;
    /**
     * 通知公告
     */
    public static final int NOTICE = 2;
    /**
     * 党建活动
     */
    public static final int PARTY_ACTIVITY = 3;
    /**
     * 显示 刷新
     */
    public static final int PARTY_SHOW_REFRESH = 11;
    /**
     * 隐藏下拉刷新
     */
    public static final int PARTY_HIDE_REFRESH = 12;
    /**
     * 更新积分
     */
    public static final int PARTY_UPDATE_INTEGRAL_CODE = 13;
    /**
     * 更新积分
     */
    public static final String PARTY_UPDATE_INTEGRAL = "update_integal";
    /**
     * 党建中心 - 所有fragment 第一级fragment  用与 event bus  控制刷新
     */
    public static final String APP_PARTYCENTERACTIVITY_FRAGMENTS_ONE_REFRESH = "FIRST_GRADE_FRAGMENT_UPDATE";
    /**
     * 党建中心 - 所有fragment 第二级fragment   用与 event bus  控制刷新
     */
    public static final String APP_PARTYCENTERACTIVITY_FRAGMENTS_TWO_REFRESH = "SECOND_GRADE_FRAGMENT_UPDATE";


    @BindView(R.id.input_layout)
    LinearLayout inputLayout;
    @BindView(R.id.my_collect)
    TextView myCollect;
    @BindView(R.id.swipe_refresh)
    VerticalSwipeRefreshLayout swipeRefresh;
    @BindView(R.id.integral)
    LinearLayout integral;
    @BindView(R.id.content_view)
    FrameLayout contentView;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.integral_text)
    TextView integralText;

    private List<BaseFragment> fragments;
    private int index = 0;
    private int currentIndex = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPartyCenterComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_party_center;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        integralText.setText(String.valueOf(UserInfoUtil.getUserInfo().getSum()));
        swipeRefresh.setOnRefreshListener(() -> {
            //刷新 党建中 fragment界面
            EventBus.getDefault().post(new EventBusBean(PARTY_SHOW_REFRESH), APP_PARTYCENTERACTIVITY_FRAGMENTS_ONE_REFRESH);
        });
        initPager();
    }

    @SuppressLint("WrongConstant")
    private void initPager() {
        fragments = new ArrayList<>();
        fragments.add(PartyCenterNewsFragment.newInstance());
        fragments.add(PartyCenterTopTableFragment.newInstance(SUBJECT_EDUCATION));
        fragments.add(PartyCenterTopTableFragment.newInstance(NOTICE));
        fragments.add(PartyCenterTopTableFragment.newInstance(PARTY_ACTIVITY));
        fragments.add(PartyCenterMineTableFragment.newInstance());
        showFragment();
        bottomNavigation.setItemIconTintList(null);
        bottomNavigation.setLabelVisibilityMode(1);
        bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.news_center:
                    index = 0;
                    showFragment();
                    break;
                case R.id.education:
                    index = 1;
                    showFragment();
                    break;
                case R.id.notice:
                    index = 2;
                    showFragment();
                    break;
                case R.id.party_activity:
                    index = 3;
                    showFragment();
                    break;
                case R.id.mine:
                    index = 4;
                    showFragment();
                    return true;
                default:
                    break;
            }
            return true;
        });
    }


    /**
     * 切换viewpage fragment
     *
     * @param eventBusBean 0 1 2 3  处理界面  切换
     */
    @Subscriber(tag = RouterHub.APP_PARTYCENTERACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            index = eventBusBean.getCode();
            switch (index) {
                case NEWS_CENTER:
                    bottomNavigation.setSelectedItemId(R.id.news_center);
                    BaseFragment baseFragment = fragments.get(0);
                    if (baseFragment instanceof PartyCenterNewsFragment) {
                        PartyCenterNewsFragment fragment = (PartyCenterNewsFragment) baseFragment;
                        fragment.setVpPositionToAll();
                    }
                    break;
                case SUBJECT_EDUCATION:
                    bottomNavigation.setSelectedItemId(R.id.education);
                    break;
                case NOTICE:
                    bottomNavigation.setSelectedItemId(R.id.notice);
                    break;
                case PARTY_ACTIVITY:
                    bottomNavigation.setSelectedItemId(R.id.party_activity);
                    break;
                case PARTY_SHOW_REFRESH:
                    showLoading();
                    break;
                case PARTY_HIDE_REFRESH:
                    hideLoading();
                    break;
                case PARTY_UPDATE_INTEGRAL_CODE:
                    //更新积分
                    integralText.setText(eventBusBean.getMessage());
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新用户积分
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = PARTY_UPDATE_INTEGRAL)
    private void onEventCallBack1(EventBusBean eventBusBean) {
        try {
            index = eventBusBean.getCode();
            switch (index) {
                case PARTY_UPDATE_INTEGRAL_CODE:
                    //更新积分
                    integralText.setText(eventBusBean.getMessage());
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
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

    private void showFragment() {
        try {
            if (index == 4) {
                inputLayout.setVisibility(View.GONE);
                integral.setVisibility(View.GONE);
                myCollect.setVisibility(View.VISIBLE);
            } else {
                inputLayout.setVisibility(View.VISIBLE);
                integral.setVisibility(View.VISIBLE);
                myCollect.setVisibility(View.GONE);
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.hide(fragments.get(currentIndex));
            if (!fragments.get(index).isAdded()) {
                ft.add(R.id.content_view, fragments.get(index));
            }
            fragments.get(index).setUserVisibleHint(true);
            ft.show(fragments.get(index)).commitAllowingStateLoss();
            currentIndex = index;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.input_layout, R.id.integral, R.id.my_collect})
    public void onClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.input_layout:
                    ARouterUtils.navigation(RouterHub.APP_SEARCHACTIVITY);
                    break;
                case R.id.my_collect:
                    ARouterUtils.navigation(RouterHub.APP_PARTYCENTERCOLLECTSACTIVITY);
                    break;
                case R.id.integral:
                    ARouterUtils.navigation(RouterHub.APP_PARTYCENTERINTEGRALACTIVITY);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
