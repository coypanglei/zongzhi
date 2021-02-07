package com.weique.overhaul.v2.mvp.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ACacheConstant;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.service.localtion.LocSdkClient;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AppUtils;
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
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.HomeMenuAdapter;
import com.weique.overhaul.v2.mvp.ui.binds.PersonWorkBinder;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
public class AppFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    @Inject
    HomeMenuAdapter homeMenuGridAdapter;
    @BindView(R.id.recycler_grid)
    RecyclerView recyclerGrid;

    @Inject
    Gson gson;

    private List<HomeMenuItemBean> mMenuData;
    private String gridJson;


    public static AppFragment newInstance() {
        AppFragment fragment = new AppFragment();
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
        return inflater.inflate(R.layout.fragment_app, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            initAdapter();
            String asString = ACache.get(AppManager.getAppManager().getmApplication()).getAsString(ACacheConstant.MENU_DATA);
            if (StringUtil.isNotNullString(asString)) {
                List list = gson.fromJson(asString, new TypeToken<List<HomeMenuItemBean>>() {
                }.getType());
                setMenuData(list, null, null, null);
            } else {
                mPresenter.getHomeModuleLabel(HomeFragment.HOME_LIST_URL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {
//        mPresenter.getHomeModuleLabel();
    }

    @Override
    public void showLoading() {
//        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
//        swipeRefreshLayout.setRefreshing(false);
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
            mMenuData = allMenuData;
            ACache.get(getActivity()).put(ACacheConstant.MENU_DATA, gson.toJson(allMenuData));
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.SET_SCAN_ICON_STATUS), RouterHub.APP_MAINACTIVITY);
            if (allMenuData != null && allMenuData.size() > 0) {
                homeMenuGridAdapter.setNewData(allMenuData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void startRun(List<String> strings) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSignBtnStatus(SigninStatusBean b) {

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
