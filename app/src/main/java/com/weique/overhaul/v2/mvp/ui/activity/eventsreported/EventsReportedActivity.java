package com.weique.overhaul.v2.mvp.ui.activity.eventsreported;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.service.localtion.LocSdkClient;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.UserGridUtil;
import com.weique.overhaul.v2.app.utils.ViewAnimationUtil;
import com.weique.overhaul.v2.di.component.DaggerEventsReportedComponent;
import com.weique.overhaul.v2.mvp.contract.EventsReportedContract;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedLookBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedSortBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EventsReportedPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.OrderSortPopup;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 事件上报 已上报列表
 * <p>
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_EVENTSREPORTEDACTIVITY)
public class EventsReportedActivity extends BaseActivity<EventsReportedPresenter> implements EventsReportedContract.View {

    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    EventsReportedAdapter mAdapter;
    @Inject
    HorizontalDividerItemDecoration decoration;

    @BindView(R.id.swipe_refresh)
    VerticalSwipeRefreshLayout refreshLayout;

    @BindView(R.id.status)
    LinearLayout status;
    @BindView(R.id.screen)
    LinearLayout screen;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.add)
    FloatingActionButton add;
    @BindView(R.id.sort_text)
    TextView sortText;
    @BindView(R.id.status_text)
    TextView statusText;

    @BindView(R.id.status_arrow)
    ImageView statusArrow;


    @BindView(R.id.search_view)
    MaterialSearchView materialSearchView;

    private String keyword;

    /**
     * 修改信息的  位置
     */
    private int alertPosition = -1;

    private int mOrderStatus = -2;
    private String mOrderSortType;
    private OrderSortPopup orderSortPopup;
    private CountDownTimer timer;

    /**
     * 是 新增还是  修改
     */
    private int action = -1;

    /**
     * 网格坐标json
     */
    private String gridJson;
    private EventsReportedBean.ListBean listBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEventsReportedComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_events_reported;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            refreshLayout.setOnRefreshListener(() -> {
                getOrderList(true, false, keyword);
            });
            setTitle("事件列表");
            ArmsUtils.configRecyclerView(recycler, linearLayoutManager);
            recycler.addItemDecoration(decoration);
            recycler.setAdapter(mAdapter);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    switch (view.getId()) {
                        case R.id.item_view:
                            Object item = adapter.getItem(position);
                            if (item instanceof EventsReportedBean.ListBean) {
                                listBean = (EventsReportedBean.ListBean) item;
                                if (listBean.getEnumOrderStatus() ==
                                        EventsReportedBean.ListBean.EventsReportedEnumBean.TS) {
                                    EventsReportedActivity.this.alertPosition = position;
                                    action = EventBusConstant.ALERT;
                                } else {
                                    action = EventBusConstant.SELECT;
                                }
                                mPresenter.gridOperatorInformation();
                            }
                            break;
                        default:
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mAdapter.setOnLoadMoreListener(() -> getOrderList(false, true, keyword), recycler);
            mAdapter.setEmptyView(R.layout.null_content_layout, recycler);
            initSearch();
            AccessControlUtil.controlByLevelCommunity(add);
            getOrderList(false, false, keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取订单列表
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param keyword       keyword
     */
    private void getOrderList(boolean pullToRefresh, boolean isLoadMore, String keyword) {
        if (mOrderStatus >= EventsReportedBean.ListBean.EventsReportedEnumBean.TS) {
            mPresenter.getEvents(pullToRefresh, isLoadMore, keyword, EventsReportedBean.SUBMITER, mOrderStatus, mOrderSortType);
        } else {
            mPresenter.getEvents(pullToRefresh, isLoadMore, keyword, EventsReportedBean.SUBMITER, mOrderSortType);
        }
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            mAdapter.loadMoreEnd(true);
        } else {
            mAdapter.loadMoreComplete();
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
                getOrderList(true, false, query);
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
//                            getOrderList(true, false, keyword);
                        }
                    }.start();
                }
                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                Timber.i("222");
            }

            @Override
            public void onSearchViewClosed() {
                materialSearchView.dismissSuggestions();
                //Do some magic
            }
        });
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

    @OnClick({R.id.add, R.id.sort, R.id.status, R.id.right_btn})
    public void onClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.add:
                    action = EventBusConstant.ADD;
                    mPresenter.gridOperatorInformation();
                    break;
                case R.id.sort:
                    ARouter.getInstance()
                            .build(RouterHub.APP_EVENTSREPORTEDSORTACTIVITY)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_EVENTSREPORTEDACTIVITY)
                            .navigation();
                    break;
                case R.id.status:
                    ViewAnimationUtil.rotateAnimation(statusArrow, true);
                    if (orderSortPopup == null) {
                        orderSortPopup = new OrderSortPopup(this);
                        orderSortPopup.showPopupWindow(screen);
                        orderSortPopup.setListItemClickListener(new OrderSortPopup.ListItemClickListener() {
                            @Override
                            public void onItemClick(int orderStatus, String name) {
                                try {
                                    mOrderStatus = orderStatus;
                                    statusText.setText(getString(R.string.status) + "(" + name + ")");
                                    getOrderList(true, false, keyword);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void reset() {
                                statusText.setText(ArmsUtils.getString(EventsReportedActivity.this, R.string.status));
                                mOrderStatus = -2;
                                getOrderList(true, false, keyword);
                            }
                        });
                        orderSortPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                ViewAnimationUtil.rotateAnimation(statusArrow, false);
                            }
                        });
                    } else {
                        orderSortPopup.showPopupWindow(screen);
                        orderSortPopup.setCheckPos();
                    }
                    break;
                case R.id.right_btn:
                    if (!materialSearchView.isSearchOpen()) {
                        materialSearchView.showSearch();
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscriber(tag = RouterHub.APP_EVENTSREPORTEDACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        //事件分类  回调 更新 recycler view
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.UPDATE_UP_EVENT_SORT:
                    if (eventBusBean.getData() instanceof EventsReportedSortBean.ListBean) {
                        EventsReportedSortBean.ListBean bean =
                                (EventsReportedSortBean.ListBean) eventBusBean.getData();
                        if (bean == null) {
                            ArmsUtils.makeText("获取事件分类信息失败");
                            return;
                        }
                        sortText.setText(getString(R.string.sort) + "(" + bean.getName() + ")");
                        mOrderSortType = bean.getId();
                        getOrderList(true, false, keyword);
                    }
                    break;
                case EventBusConstant.ADD:
                case EventBusConstant.ALERT:
                case EventBusConstant.SELECT:
                    getOrderList(true, false, keyword);
                    break;
                case EventBusConstant.DELETE:
                    mAdapter.remove(alertPosition);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * 设置界面列表数据
     */
    @Override
    public void setNewData(List<EventsReportedBean.ListBean> newData, boolean isLoadMore) {
        if (isLoadMore) {
            mAdapter.addData(newData);
        } else {
            mAdapter.setNewData(newData);
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
            LocSdkClient.getInstance(EventsReportedActivity.this).getLocationStart();
            BDLocation location =
                    LocSdkClient.getInstance(EventsReportedActivity.this).getLocationStart()
                            .getLastKnownLocation();
            if (location == null || latLngLists.size() <= 0) {
                ArmsUtils.makeText("获取您的定位失败");
                return;
            }
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            //不允许修改坐标  并 用户不在网格内
            boolean select = true;
            if (action == EventBusConstant.ALERT || action == EventBusConstant.ADD) {
                select = false;
            }
            if (!o && !UserGridUtil.pointInGrid(latLng, latLngLists) && !select) {
                ArmsUtils.makeText("您不在负责区域内，无法上报事件");
            } else {
                EventsReportedLookBean bean = new EventsReportedLookBean();
                bean.setEnumOrderStatus(EventsReportedBean.ListBean.EventsReportedEnumBean.TS);
                if (action == EventBusConstant.ALERT) {
                    ARouter.getInstance()
                            .build(RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
                            .withString(ARouerConstant.ID, listBean.getEventRecordId())
                            .withString(EventsReportedCrudActivity.CUST_ID, listBean.getCustId())
                            .withInt(ARouerConstant.STATUS, EventBusConstant.ALERT)
                            .withString(MapActivity.POINTS_IN_JSON, gridJson)
                            .withBoolean(MapActivity.ADDRESS_CAN_CHANGED, o)
                            .navigation();
                } else if (action == EventBusConstant.SELECT) {
                    ARouter.getInstance()
                            .build(RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY)
                            .withParcelable(EventsReportedLookActivity.LIST_BEAN, listBean)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_EVENTSREPORTEDACTIVITY)
                            .withString(MapActivity.POINTS_IN_JSON, gridJson)
                            .withBoolean(MapActivity.ADDRESS_CAN_CHANGED, o)
                            .navigation();
                } else if (action == EventBusConstant.ADD) {
                    ARouter.getInstance().build(RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
                            .withParcelable(EventsReportedCrudActivity.EVENTSREPORTEDLOOKBEAN, bean)
                            .withInt(ARouerConstant.STATUS, EventBusConstant.ADD)
                            .withString(MapActivity.POINTS_IN_JSON, gridJson)
                            .withBoolean(MapActivity.ADDRESS_CAN_CHANGED, o)
                            .navigation();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
