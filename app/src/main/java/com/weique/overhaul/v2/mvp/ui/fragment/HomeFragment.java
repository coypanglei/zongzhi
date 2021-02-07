package com.weique.overhaul.v2.mvp.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.model.LatLng;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.google.gson.Gson;
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
import com.weique.overhaul.v2.app.service.localtion.LocSdkClient;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserGridUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerHomeComponent;
import com.weique.overhaul.v2.dynamic.BaseBinderAdapterBean;
import com.weique.overhaul.v2.mvp.contract.HomeContract;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedLookBean;
import com.weique.overhaul.v2.mvp.model.entity.HomeMenuItemBean;
import com.weique.overhaul.v2.mvp.model.entity.PersonWorkBean;
import com.weique.overhaul.v2.mvp.model.entity.SigninStatusBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.HomePresenter;
import com.weique.overhaul.v2.mvp.ui.activity.IntegratedWithActivity;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventsReportedCrudActivity;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.activity.message.MessageListActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.HomeMenuAdapter;
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
    HomeMenuAdapter homeMenuAdapter;
    @BindView(R.id.user_photo_text)
    ImageView userPhoto;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tag_one)
    TextView tagOne;
    @BindView(R.id.c_view)
    CardView cView;
    @BindView(R.id.marquee_view)
    SimpleMarqueeView<String> marqueeView;
    @BindView(R.id.marquee_layout)
    LinearLayout marqueeLayout;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe_refresh)
    VerticalSwipeRefreshLayout swipeRefreshLayout;
    private List<HomeMenuItemBean> mMenuData;

    @Inject
    Gson gson;
    public static final String APP_LIST_URL = "app/login/GetNavHomeForMobile";
    public static final String HOME_LIST_URL = "app/login/GetNavForMobile";
    private String gridJson;

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
            swipeRefreshLayout.setOnRefreshListener(() -> {
                refreshData();
            });
            initAdapter();
            initUserInfo();
            getHomeModuleLabel();
            mPresenter.getSignStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initAdapter() {
        try {
            homeMenuAdapter = new HomeMenuAdapter(R.layout.home_list_item, null);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

            recycler.setAdapter(homeMenuAdapter);
            recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                    .colorResId(R.color.white)
                    .sizeResId(R.dimen.dp_5)
                    .build());
            homeMenuAdapter.setEmptyView(R.layout.null_content_layout, recycler);
            homeMenuAdapter.setOnItemClickListener((adapter, view, position) -> {
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
                                        .withParcelableArrayList(IntegratedWithActivity.LIST, (ArrayList<? extends Parcelable>) mMenuData)
                                        .navigation();
                            }
                            break;
                        case RouterHub.APP_CAPTUREACTIVITY:
                            mPresenter.getPermissionCamera();
                            break;
                        case RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY:
                            mPresenter.gridOperatorInformation();
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
                                        .withString(ARouerConstant.SOURCE, RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT)
                                        .withString(ARouerConstant.TITLE, getString(R.string.video_hs))
                                        .navigation();
                            }
                            break;
                        case RouterHub.APP_ADDRESSBOOKACTIVITY:
                            if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.GRIDDING) {
                                ARouter.getInstance().build(RouterHub.APP_ADDRESSLOOKLISTACTIVITY)
                                        .withString(ARouerConstant.SOURCE, RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT)
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


    /**
     * 刷新数据
     */
    private void refreshData() {
        //功能模块
        getHomeModuleLabel();
        //首页  MAINACTIVITY
        EventBus.getDefault().post(new EventBusBean(EventBusConstant.REQUEST_AGAIN), RouterHub.APP_MAINACTIVITY);
        //更新  PatrolsFragment
        EventBus.getDefault().post(new EventBusBean(EventBusConstant.IS_REFRESH), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_PATROLSFRAGMENT);
        //更新  TaskAgentsFragment
        EventBus.getDefault().post(new EventBusBean(EventBusConstant.SELECT), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT);
        //更新  TaskListHomeFragment
        EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE), RouterHub.APP_TASKLISTACTIVITY);
        //更新  MyFragment
        EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE), RouterHub.APP_MAINACTIVITY_MYFRAGMENT);
        //重新获取数据
        mPresenter.getNotice();
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


    /**
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {
        getHomeModuleLabel();
    }

    private void getHomeModuleLabel() {
        mPresenter.getHomeModuleLabel(APP_LIST_URL);
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
    public void setMenuData(List<HomeMenuItemBean> allMenuData,
                            List<HomeMenuItemBean> menuData,
                            List<Boolean> booleans, String url) {
        try {
            if (APP_LIST_URL.equals(url)) {
                mMenuData = allMenuData;
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.SET_SCAN_ICON_STATUS), RouterHub.APP_MAINACTIVITY);
                if (menuData != null && menuData.size() > 0) {
                    homeMenuAdapter.setNewData(menuData);
                }
                mPresenter.getHomeModuleLabel(HOME_LIST_URL);
            } else {
                ACache.get(getActivity()).put(ACacheConstant.MENU_DATA, gson.toJson(allMenuData));
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
                if (AppUtils.isFastClick()) {
                    return;
                }
                ARouter.getInstance().build(RouterHub.APP_MESSAGELISTACTIVITY)
                        .withInt(MessageListActivity.CHECK_POS, MessageListActivity.TWO)
                        .navigation();
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
                case EventBusConstant.COMMON_REFRESH:
                    refreshData();
                case EventBusConstant.COMMON_UPDATE:
                    initUserInfo();
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
            LocSdkClient.getInstance(getContext()).getLocationStart();
            BDLocation location =
                    LocSdkClient.getInstance(getContext()).getLocationStart()
                            .getLastKnownLocation();
            if (location == null || latLngLists.size() <= 0) {
                ArmsUtils.makeText("获取您的定位失败");
                return;
            }
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            //不允许修改坐标  并 用户不在网格内
            boolean select = false;
            if (!o && !UserGridUtil.pointInGrid(latLng, latLngLists) && !select) {
                ArmsUtils.makeText("您不在负责区域内，无法上报事件");
            } else {
                EventsReportedLookBean bean = new EventsReportedLookBean();
                bean.setEnumOrderStatus(EventsReportedBean.ListBean.EventsReportedEnumNewBean.TS);
                ARouter.getInstance().build(RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
                        .withParcelable(EventsReportedCrudActivity.EVENTSREPORTEDLOOKBEAN, bean)
                        .withInt(ARouerConstant.STATUS, EventBusConstant.ADD)
                        .withString(ARouerConstant.SOURCE, RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT)
                        .withString(MapActivity.POINTS_IN_JSON, gridJson)
                        .withBoolean(MapActivity.ADDRESS_CAN_CHANGED, o)
                        .navigation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
