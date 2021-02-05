package com.weique.overhaul.v2.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.google.android.material.appbar.AppBarLayout;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ACacheConstant;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerHomeComponent;
import com.weique.overhaul.v2.dynamic.BaseBinderAdapterBean;
import com.weique.overhaul.v2.mvp.contract.HomeContract;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;
import com.weique.overhaul.v2.mvp.model.entity.HomeMenuItemBean;
import com.weique.overhaul.v2.mvp.model.entity.PersonWorkBean;
import com.weique.overhaul.v2.mvp.model.entity.SigninStatusBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.HomePresenter;
import com.weique.overhaul.v2.mvp.ui.activity.IntegratedWithActivity;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.activity.message.MessageListActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.HomeMenuGridAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.binds.PersonWorkBinder;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    @Inject
    HomeMenuGridAdapter homeMenuGridAdapter;
    @BindView(R.id.user_photo_text)
    ImageView userPhoto;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tag_one)
    TextView tagOne;
    @BindView(R.id.c_view)
    CardView cView;
    @BindView(R.id.marquee_view)
    SimpleMarqueeView marqueeView;
    @BindView(R.id.marquee_layout)
    LinearLayout marqueeLayout;
    @BindView(R.id.recycler_grid)
    RecyclerView recyclerGrid;
    @BindView(R.id.tl_2)
    SlidingTabLayout tl2;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.swipe_refresh)
    VerticalSwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;


    private ArrayList<HomeMenuItemBean> mMenuData;
    private MyPagerAdapter mAdapter;


    private ArrayList<Fragment> mFragments;
    //    private final String[] strings1 = {"待办事件", "待办巡查"};
    private final String[] strings1 = {"工作清单", "待办巡查"};
    private TaskListHomeFragment taskListHomeFragment;
    private PatrolsFragment patrolsFragment;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    try {
                        if (verticalOffset < 0) {
                            swipeRefreshLayout.setEnabled(false);
                        } else {
                            swipeRefreshLayout.setEnabled(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            swipeRefreshLayout.setOnRefreshListener(() -> {
                //功能模块
                mPresenter.getHomeModuleLabel();
                //首页  MAINACTIVITY
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.REQUEST_AGAIN), RouterHub.APP_MAINACTIVITY);
                //更新  PatrolsFragment
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.IS_REFRESH), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_PATROLSFRAGMENT);
                //更新  TaskAgentsFragment
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.SELECT), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT);
                //更新  TaskListHomeFragment
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE), RouterHub.APP_TASKLISTACTIVITY);
                //重新获取数据
                mPresenter.getNotice();
            });
            initUserInfo();
            initAdapter();
            mPresenter.getHomeModuleLabel();
            mFragments = new ArrayList<>();
            taskListHomeFragment = TaskListHomeFragment.newInstance();
            mFragments.add(taskListHomeFragment);
            patrolsFragment = PatrolsFragment.newInstance();
            mFragments.add(patrolsFragment);
            mAdapter = new MyPagerAdapter(getChildFragmentManager(), mFragments, strings1);
            vp.setAdapter(mAdapter);
            tl2.setViewPager(vp, strings1);
            vp.setOffscreenPageLimit(strings1.length);
            mPresenter.getSignStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initUserInfo() {
        GlideUtil.loadImage(mContext, UserInfoUtil.getUserInfo().getHeadUrl(), userPhoto);
        String roleName = "";
        if (StringUtil.isNotNullString(UserInfoUtil.getUserInfo().getRoleName())) {
            roleName = "(" + StringUtil.setText(UserInfoUtil.getUserInfo().getRoleName()) + ")";
        }
        name.setText(UserInfoUtil.getUserInfo().getName() + roleName);
        tagOne.setText(UserInfoUtil.getUserInfo().getFullPath());
    }

    private void initAdapter() {
        try {
            recyclerGrid.setLayoutManager(new GridLayoutManager(getActivity(),
                    5, RecyclerView.VERTICAL, false));

            recyclerGrid.setAdapter(homeMenuGridAdapter);
            recyclerGrid.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                    .colorResId(R.color.white)
                    .sizeResId(R.dimen.dp_15)
                    .build());
            homeMenuGridAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    HomeMenuItemBean homeMenuItemBean = (HomeMenuItemBean) adapter.getItem(position);
                    assert homeMenuItemBean != null;
                    switch (homeMenuItemBean.getTarget()) {
                        case RouterHub.APP_INTEGRATEDWITHACTIVITY:
                            if (mMenuData != null && mMenuData.size() > 0) {
                                ARouter.getInstance().build(homeMenuItemBean.getTarget())
                                        .withParcelableArrayList(IntegratedWithActivity.LIST, mMenuData)
                                        .navigation();
                            }
                            break;
                        case RouterHub.APP_CAPTUREACTIVITY:
                            mPresenter.getPermissionCamera();
                            break;
                        case RouterHub.APP_SIGNINACTIVITY:
                            mPresenter.getPermission();
                            break;
                        case RouterHub.APP_CHATSELECTACTIVITY:
                            if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.GRIDDING) {
                                ARouter.getInstance()
                                        .build(RouterHub.APP_CHATSELECTACTIVITY)
                                        .navigation();
                            } else {
                                ARouter.getInstance().build(RouterHub.APP_ADDRESSBOOKACTIVITY)
                                        .withString(ARouerConstant.SOURCE, RouterHub.APP_CHATSELECTACTIVITY)
                                        .withString(ARouerConstant.TITLE, getString(R.string.video_hs))
                                        .navigation();
                            }
                            break;
                        case RouterHub.APP_ADDRESSBOOKACTIVITY:
                            if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.GRIDDING) {
                                ARouter.getInstance().build(RouterHub.APP_ADDRESSLOOKLISTACTIVITY)
                                        .navigation();
                            } else {
                                ARouter.getInstance().build(RouterHub.APP_ADDRESSBOOKACTIVITY)
                                        .withString(ARouerConstant.TITLE, getString(R.string.book_address))
                                        .navigation();
                            }
                            break;
                        //个人工作日志
                        case RouterHub.APP_PERSONAL_ACTIVITY:
                            CommonCollectBean commonCollectBean = new CommonCollectBean();
                            commonCollectBean.setTitle(homeMenuItemBean.getName().trim());
                            commonCollectBean.setPath(MainService.PERSONAL_WORK_STATISTICS);
                            List<BaseBinderAdapterBean> list = new ArrayList<>();
                            list.add(new BaseBinderAdapterBean(PersonWorkBean.class, new PersonWorkBinder(), null));
                            commonCollectBean.setBindBeanList(list);
                            commonCollectBean.setClassName(PersonWorkBean.class);
                            ARouter.getInstance().build(RouterHub.APP_PERSONAL_ACTIVITY)
                                    .withParcelable(Constant.COMMON_COLLECTION, commonCollectBean).
                                    navigation();
                            break;
                        default:
                            launchActivityByRouter(homeMenuItemBean.getTarget().trim());
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        try {
            for (Fragment fragment : mFragments) {
                if (fragment != null) {
                    fragment.onDestroyView();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }

    /**
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {
        mPresenter.getHomeModuleLabel();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(message);
    }


    @Override
    public void launchActivityByRouter(@NonNull String path) {
        ARouterUtils.navigation(getContext(), path.trim());
    }

    @Override
    public void setMenuData(ArrayList<HomeMenuItemBean> allMenuData, List<HomeMenuItemBean> menuData, List<Boolean> booleans) {
        try {
            mMenuData = allMenuData;
            ACache.get(getActivity()).put(ACacheConstant.MENU_DATA, mMenuData.toString());
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.SET_SCAN_ICON_STATUS), RouterHub.APP_MAINACTIVITY);
            if (menuData != null && menuData.size() > 0) {
                homeMenuGridAdapter.setNewData(menuData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void startRun(List<String> strings) {
        try {
            if (strings.size() <= 0) {
                strings.add("暂无公告");
            }
            SimpleMF<String> marqueeFactory = new SimpleMF<>(getActivity());
            marqueeFactory.setData(strings);
            marqueeView.setMarqueeFactory(marqueeFactory);
            marqueeView.startFlipping();
            marqueeView.setOnItemClickListener((mView, mData, mPosition) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    ARouter.getInstance().build(RouterHub.APP_MESSAGELISTACTIVITY)
                            .withInt(MessageListActivity.CHECK_POS, MessageListActivity.TWO)
                            .navigation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSignBtnStatus(SigninStatusBean b) {
        //接口返回 false:当天已签到  true:当天未签到  所以用非
        if (b.isCheckInOrder()) {

            new CommonDialog.Builder(mContext).setTitle("")
                    .setContent("您今日尚未签到，点击确定进行签到")
                    .setPositiveButton("确定", (v, commonDialog) -> {
                        //跳转至签到页面
                        mPresenter.getPermission();

                    })
                    .setNegativeButton("取消", null).create().show();


        }
    }

    @OnClick({R.id.c_view, R.id.user_photo_text})
    public void onClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.c_view:
                    ARouter.getInstance().build(RouterHub.APP_MY_INFO_ACTIVITY).navigation();
                    break;

                case R.id.user_photo_text:
                    ARouter.getInstance()
                            .build(RouterHub.APP_PICTURELOOKACTIVITY)
                            .withString(ARouerConstant.TITLE, "头像")
                            .withString(PictureLookActivity.URL_, UserInfoUtil.getUserInfo().getHeadUrl())
                            .navigation();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscriber(tag = RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.UPDATE_HEAD_PHOTO:
                    GlideUtil.loadImage(mContext, UserInfoUtil.getUserInfo().getHeadUrl(), userPhoto);
                    break;
                case EventBusConstant.UPDATE_HEAD_NAME:
                    String roleName = "";
                    if (StringUtil.isNotNullString(UserInfoUtil.getUserInfo().getRoleName())) {
                        roleName = "(" + StringUtil.setText(UserInfoUtil.getUserInfo().getRoleName()) + ")";
                    }
                    name.setText(UserInfoUtil.getUserInfo().getName() + roleName);
                    break;
                case EventBusConstant.GET_NOTICE:
                    mPresenter.getNotice();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
