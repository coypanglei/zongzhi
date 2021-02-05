package com.weique.overhaul.v2.mvp.ui.activity.visit;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerInspectionMapComponent;
import com.weique.overhaul.v2.mvp.contract.InspectionMapContract;
import com.weique.overhaul.v2.mvp.model.entity.AdminGridBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.InspectionLatLng;
import com.weique.overhaul.v2.mvp.model.entity.PatrolsDetailItemBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchDetailItemBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.TaskListBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.InspectionMapPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.InspectionMapAdapter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionAddActivity.NEW_TASK;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionAddActivity.TARGET_NEW_POINT;


/**
 * @author 巡检
 */
@Route(path = RouterHub.APP_INSPECTIONMAPACTIVITY)
public class InspectionMapActivity extends BaseActivity<InspectionMapPresenter> implements InspectionMapContract.View {

    @Inject
    Gson gson;


    @Autowired(name = "model")
    ResourceSearchDetailItemBean.ListBean model;

    @Autowired(name = ARouerConstant.TITLE)
    String title;

    /**
     * 待办任务穿过来的
     */
    @Autowired(name = "TASK_MODEL")
    PatrolsDetailItemBean.ElementsBean elementsBean;


    /**
     * 多边形信息
     */
    public static final String POINTS_IN_JSON = "pointsInJSON";
    @Autowired(name = POINTS_IN_JSON)
    String pointsInJSON;

    /**
     * 第几个巡检点
     */
    public static final String WHICH_POINT = "which_point";
    @Autowired(name = WHICH_POINT)
    int which_point;

    /**
     * 是否新建巡检点
     */
    public static final String IS_NEW_POINT = "is_new_point";
    @Autowired(name = IS_NEW_POINT)
    String is_new_point;
    /**
     * 資源名
     */
    public static final String RESOURCE = "resource";
    @Autowired(name = RESOURCE)
    String resource;


    /**
     * 个人中心任务列表
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    @Autowired(name = "MissionConditionsBean")
    TaskListBean.ListBean.MissionConditionsBean missionConditionsBean;


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
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.icon_location)
    LinearLayout iconLocation;
    @BindView(R.id.layout)
    ConstraintLayout layout;
    @BindView(R.id.remind)
    EditText remind;
    @BindView(R.id.select_resource)
    TextView selectResource;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.things_submit)
    Button thingsSubmit;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.reportRecycler)
    RecyclerView reportRecycler;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<PolygonOptions> ooPolygons;
    private InspectionMapAdapter inspectionMapAdapter;
    private String longAndLatJson;
    private BitmapDescriptor mGreenTexture = BitmapDescriptorFactory.fromAsset("Icon_road_blue_arrow.png");


    /**
     * 巡檢過的點集合
     */
    private List<LatLng> latLngs;


    private int mStrokeWidth = 5;

    private List<List<LatLng>> latLngLists;
    private BitmapDescriptor mbitmap = BitmapDescriptorFactory.fromResource(R.drawable.dingwei2_icon);
    private BitmapDescriptor mbitmap_qidian = BitmapDescriptorFactory.fromResource(R.drawable.party_start_icon);
    private BitmapDescriptor mbitmap_my = BitmapDescriptorFactory.fromResource(R.drawable.zhongdian_icon);
    /**
     * 用户点击的点
     */
    private LatLng mCurrentPt;
    /**
     * 用户点击 复位  没有 mCurrentPt 时  用默认点 - 网格集合的第一个点
     */
    private LatLng mDefaultPt;


    public LocationClient mLocationClient;
    private boolean isFirstLoc = true; // 是否首次定位
    private boolean isFirstPoint = true; // 是否首次定位点
    private BaiduMap mBaiduMap;

    /**
     * 用户点击的 点
     */
    private StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean;
    private PolygonOptions ooPolygon;
    private MyLocationListener myLocationListener;
    @Autowired(name = "ElementListBean")
    InformationTypeOneSecondBean.ElementListBean listBean;//选择资源返回的数据
    private LatLng ll;
    private boolean complete = false;
    private String addInspId = "";
    private int i = 0;


    @Subscriber(tag = RouterHub.APP_TOURVISITACTIVITY)
    private void updateList(EventBusBean eventBusBean) {
        InformationTypeOneSecondBean.ElementListBean bean = (InformationTypeOneSecondBean.ElementListBean) eventBusBean.getData();
        listBean = bean;
        selectResource.setText(listBean.getName());
    }


    private boolean isAddPoint = true;

    @Subscriber(tag = RouterHub.APP_INSPECTIONADDACTIVITY)
    private void update(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getMessage()) {
                case InspectionAddActivity.TARGET_NEW_POINT:
                case InspectionAddActivity.NEW:
                    killMyself();
                    break;
                case InspectionAddActivity.RESET:
                case InspectionAddActivity.TAGETNEW:
                    isFirstLoc = false;
                    assert mPresenter != null;
                    mPresenter.getLatLng(true, false, model.getId());
                    break;
                case InspectionAddActivity.NEW_TASK:
                    swipeRefreshLayout.setEnabled(true);
                    isFirstLoc = false;
                    assert mPresenter != null;
                    mPresenter.getLatLng(true, false, (String) eventBusBean.getData());
                    addInspId = (String) eventBusBean.getData();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInspectionMapComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_inspection_map;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            if (!AppUtils.isLocationEnabled(this)) {
                ArmsUtils.makeText("GPS关闭,会使您的定位不准确");
            }
            ARouter.getInstance().inject(this);
            showProgressDialog();
            latLngs = new ArrayList<>();
            setTitle(title);
            initRecycler();
            rightBtnText.setText("新增");
            rightBtnText.setTextSize(17);
            rightBtnText.setTextColor(getResources().getColor(R.color.colorPrimary));
            if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.GRIDDING
                    || !ArmsUtils.isEmpty(source)) {
                submit.setVisibility(View.VISIBLE);
            } else {
                submit.setVisibility(View.GONE);
            }
            if (InspectionAddActivity.RESET.equals(is_new_point)) {
                assert mPresenter != null;
                mPresenter.getLatLng(false, false, model.getId());
                selectResource.setClickable(false);
                remind.setText(model.getMemo());
                selectResource.setText(resource);
            } else if (InspectionAddActivity.TARGET_NEW_POINT.equals(is_new_point)) {
                selectResource.setClickable(false);
                selectResource.setText(resource);
            } else if (InspectionAddActivity.NEW.equals(is_new_point)) {
                selectResource.setClickable(true);
            } else if (InspectionAddActivity.NEW_TASK.equals(is_new_point)) {

                if (!TextUtils.isEmpty(elementsBean.getInspectionRecordId())) {
                    assert mPresenter != null;
                    mPresenter.getLatLng(false, false, elementsBean.getInspectionRecordId());
                }
            } else if (InspectionAddActivity.MINE_TASK.equals(is_new_point)) {
                selectResource.setClickable(true);
                layout1.setVisibility(View.GONE);

            }
            initMap();
            initCoord();


            if (InspectionAddActivity.NEW_TASK.equals(is_new_point)) {
                if (TextUtils.isEmpty(elementsBean.getInspectionRecordId()) && TextUtils.isEmpty(addInspId)) {
                    submit.setVisibility(View.GONE);
                }

            } else if (InspectionAddActivity.NEW.equals(is_new_point)) {
                submit.setVisibility(View.GONE);

            } else if (InspectionAddActivity.TARGET_NEW_POINT.equals(is_new_point)) {
                submit.setVisibility(View.GONE);

            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    private void initRecycler() {
        try {
            if (TARGET_NEW_POINT.equals(is_new_point) || InspectionAddActivity.NEW.equals(is_new_point) || InspectionAddActivity.MINE_TASK.equals(is_new_point)) {
                swipeRefreshLayout.setEnabled(false);
            } else if (InspectionAddActivity.NEW_TASK.equals(is_new_point) && TextUtils.isEmpty(elementsBean.getInspectionRecordId())) {
                swipeRefreshLayout.setEnabled(false);
                swipeRefreshLayout.setOnRefreshListener(() -> {
                    if (InspectionAddActivity.NEW_TASK.equals(is_new_point)) {

                        if (!TextUtils.isEmpty(addInspId)) {
                            assert mPresenter != null;
                            mPresenter.getLatLng(true, false, addInspId);
                        } else {
                            assert mPresenter != null;
                            mPresenter.getLatLng(true, false, elementsBean.getInspectionRecordId());
                        }
                    } else {
                        assert mPresenter != null;
                        mPresenter.getLatLng(true, false, model.getId());
                    }

                });
            } else {
                swipeRefreshLayout.setEnabled(true);
                swipeRefreshLayout.setOnRefreshListener(() -> {
                    if (InspectionAddActivity.NEW_TASK.equals(is_new_point)) {

                        if (!TextUtils.isEmpty(addInspId)) {
                            assert mPresenter != null;
                            mPresenter.getLatLng(true, false, addInspId);
                        } else {
                            assert mPresenter != null;
                            mPresenter.getLatLng(true, false, elementsBean.getInspectionRecordId());
                        }
                    } else {
                        assert mPresenter != null;
                        mPresenter.getLatLng(true, false, model.getId());
                    }

                });
            }
            inspectionMapAdapter = new InspectionMapAdapter();
            inspectionMapAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(reportRecycler, new LinearLayoutManager(this));
            reportRecycler.setAdapter(inspectionMapAdapter);
            inspectionMapAdapter.setEmptyView(R.layout.null_content_layout, reportRecycler);
            inspectionMapAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (view.getId() == R.id.edit_btn) {
                        if (InspectionAddActivity.NEW_TASK.equals(is_new_point)) {
                            ARouter.getInstance().build(RouterHub.APP_INSPECTIONADDACTIVITY)
                                    .withString(InspectionMapActivity.POINTS_IN_JSON, longAndLatJson)//当前经纬度
                                    .withSerializable("listModel", ((InspectionLatLng.ListBean) adapter.getData().get(position)))
                                    .withString(InspectionMapActivity.IS_NEW_POINT, NEW_TASK)
                                    .withString("imageUrl", ((InspectionLatLng.ListBean) adapter.getData().get(position)).getIRImageUrlsInJson())
                                    .withBoolean("isComplete", complete)
                                    .withInt("NEW_TASK_ITEM", 0)
                                    .withString(RESOURCE, elementsBean.getName())
                                    .withSerializable("TASK_MODEL", elementsBean)
                                    .withString(ARouerConstant.TITLE, "编辑巡检记录")
                                    .navigation();

                        } else {
                            ARouter.getInstance().build(RouterHub.APP_INSPECTIONADDACTIVITY)
                                    .withString(InspectionMapActivity.POINTS_IN_JSON, longAndLatJson)//当前经纬度
                                    .withString("RESET_LAT", ((InspectionLatLng.ListBean) adapter.getData().get(position)).getPointInJson())//当前经纬度
                                    .withSerializable("listModel", ((InspectionLatLng.ListBean) adapter.getData().get(position)))
                                    .withSerializable("xunjianModel", model)
                                    .withString(InspectionMapActivity.IS_NEW_POINT, is_new_point)
                                    .withString("imageUrl", ((InspectionLatLng.ListBean) adapter.getData().get(position)).getIRImageUrlsInJson())
                                    .withBoolean("isComplete", complete)
                                    .withString(ARouerConstant.TITLE, "编辑巡检记录")
                                    .withString(RESOURCE, resource)
                                    .navigation();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            inspectionMapAdapter.setOnLoadMoreListener(() -> {

                if (is_new_point.equals(InspectionAddActivity.NEW_TASK)) {
                    assert mPresenter != null;
                    mPresenter.getLatLng(false, true, elementsBean.getInspectionRecordId());
                } else {
                    assert mPresenter != null;
                    mPresenter.getLatLng(false, true, model.getId());
                }
            }, reportRecycler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化参照物  网格  和 标记点
     */
    private void initCoord() {
        try {
            mBaiduMap.clear();
            ooPolygons = new ArrayList<>();
            latLngLists = new ArrayList<>();
            //修改时  会传过来一个点
            if (StringUtil.isNotNullString(pointsInJSON)) {
                List<StandardAddressStairBean.PointsInJsonBean.PolygoysBean> polygoysBeans = null;
                try {
                    StandardAddressStairBean.PointsInJsonBean pointsInJsonBean = gson.fromJson(pointsInJSON, StandardAddressStairBean.PointsInJsonBean.class);
                    polygoysBeans = pointsInJsonBean.getPolygoys();
                    if (polygoysBeans == null) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        AdminGridBean adminGridInfoBean = gson.fromJson(pointsInJSON, AdminGridBean.class);
                        polygoysBeans = new ArrayList<>();
                        for (AdminGridBean.AdministrativesBean bean : adminGridInfoBean.getAdministratives()) {
                            StandardAddressStairBean.PointsInJsonBean.PolygoysBean polygoysBean = new StandardAddressStairBean.PointsInJsonBean.PolygoysBean();
                            String administrative = bean.getAdministrative();
                            String[] split = administrative.split(";");

                            List<StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean> pathBeans = new ArrayList<>();
                            for (String ss : split) {
                                StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                                        new StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean();
                                String[] sss = ss.split(",");
                                pathBean.setLng(Double.parseDouble(sss[0]));
                                pathBean.setLat(Double.parseDouble(sss[1]));
                                pathBeans.add(pathBean);
                            }
                            AdminGridBean.AdministrativesBean.AdministrativeStyleBean administrativeStyle = bean.getAdministrativeStyle();
                            StandardAddressStairBean.PointsInJsonBean.PolygoysBean.StyleOptionsBean styleOptionsBean =
                                    new StandardAddressStairBean.PointsInJsonBean.PolygoysBean.StyleOptionsBean();
                            styleOptionsBean.setFillColor(administrativeStyle.getFillColor());
                            styleOptionsBean.setFillOpacity(administrativeStyle.getFillOpacity());
                            styleOptionsBean.setStrokeColor(administrativeStyle.getStrokeColor());
                            styleOptionsBean.setStrokeOpacity(administrativeStyle.getStrokeOpacity());
                            styleOptionsBean.setStrokeStyle(administrativeStyle.getStrokeStyle());
                            styleOptionsBean.setStrokeWeight(administrativeStyle.getStrokeWeight());
                            polygoysBean.setStyleOptions(styleOptionsBean);
                            polygoysBean.setPath(pathBeans);
                            polygoysBeans.add(polygoysBean);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        ArmsUtils.makeText("网格渲染失败");
                    }
                }
                LatLng latLng;
                if (polygoysBeans != null && polygoysBeans.size() > 0) {
                    for (StandardAddressStairBean.PointsInJsonBean.PolygoysBean polygoys : polygoysBeans) {
                        List<LatLng> latLngList = new ArrayList<>();
                        if (polygoys != null && polygoys.getPath() != null && polygoys.getPath().size() > 0) {
                            for (StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean : polygoys.getPath()) {
                                latLng = new LatLng(pathBean.getLat(), pathBean.getLng());
                                if (mDefaultPt == null) {
                                    mDefaultPt = latLng;
                                }
                                latLngList.add(latLng);
                            }
                            if (latLngList.size() > 2) {
                                ooPolygons.add(new PolygonOptions()
                                        .points(latLngList)
                                        .stroke(new Stroke(mStrokeWidth, Color.parseColor(polygoys.getStyleOptions().getStrokeColor().replace("#", "#40"))))
                                        .fillColor(Color.parseColor(polygoys.getStyleOptions().getFillColor().replace("#", "#40"))));
                                latLngLists.add(latLngList);
                            }
                        }
                    }
                }
            }
            if (ooPolygons != null && ooPolygons.size() > 0) {
                for (PolygonOptions ooPolygon : ooPolygons) {
                    if (ooPolygon != null) {
                        mBaiduMap.addOverlay(ooPolygon);
                    }
                }
            } else {
                ArmsUtils.makeText(getString(R.string.init_gridding_fail));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initMap() {
        if (myLocationListener == null) {
            myLocationListener = new MyLocationListener();
        }
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(getApplicationContext());
        }
        if (mBaiduMap == null) {
            //获取地图控件引用
            mBaiduMap = map.getMap();
            //普通地图
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            //开启交通图
            //mBaiduMap.setTrafficEnabled(true);
            //开启热力图
            //mBaiduMap.setBaiduHeatMapEnabled(true);
            // 开启定位图层
            mBaiduMap.setMyLocationEnabled(true);
            //配置定位SDK参数
            initLocation();
        }
        mLocationClient.registerLocationListener(myLocationListener);    //注册监听函数
        if (mLocationClient.isStarted()) {
            //开启定位
            mLocationClient.restart();
        } else {
            //开启定位
            mLocationClient.start();
        }
        //图片点击事件，回到定位点
        mLocationClient.requestLocation();

    }

    /**
     * 配置定位SDK参数
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType(Constant.CoorType_BD09LL);//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // 打开gps
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }


    @Override
    public void setLatLng(InspectionLatLng inspectionLatLng, boolean isLoadMore) {

        try {
            initMap();
            complete = inspectionLatLng.isComplete();
            if (inspectionLatLng.isComplete()) {
                submit.setVisibility(View.GONE);
                rightBtn.setVisibility(View.GONE);
            }
            if (isLoadMore) {
                inspectionMapAdapter.addData(inspectionLatLng.getInspectionWithEvents());
            } else {
                inspectionMapAdapter.setNewData(inspectionLatLng.getInspectionWithEvents());
            }
            latLngs = new ArrayList<>();
            for (InspectionLatLng.ListBean inspectionLatLng1 : inspectionLatLng.getInspectionWithEvents()) {
                StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean = gson.fromJson(inspectionLatLng1.getPointInJson(), StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
                latLngs.add(new LatLng(pathBean.getLat(), pathBean.getLng()));
            }
            initCoord();
            initPoint();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }


    private void initPoint() {
        try {
            for (int i = 0; i < latLngs.size(); i++) {
                if (i == 0) {
                    MarkerOptions ooA1 = new MarkerOptions().position(latLngs.get(i)).icon(mbitmap_qidian);
                    mBaiduMap.addOverlay(ooA1);
                } else {
                    MarkerOptions ooA1 = new MarkerOptions().position(latLngs.get(i)).icon(mbitmap);
                    mBaiduMap.addOverlay(ooA1);
                }
            }

            if (latLngs.size() > 1) {
                OverlayOptions ooPolylineA = new PolylineOptions()
                        .width(9)// 设置折线线宽， 默认为 5， 单位：像素
                        .customTexture(mGreenTexture)
                        .points(latLngs)// 设置折线坐标点列表
                        .dottedLine(true);
                mBaiduMap.addOverlay(ooPolylineA);
            }

            if (!isFirstLoc && !isAddPoint) {
                MarkerOptions ooa = new MarkerOptions().position(ll).icon(mbitmap_my).zIndex(9).draggable(false);
                mBaiduMap.addOverlay(ooa);
                List<LatLng> latLngsOne = new ArrayList<>();
                latLngsOne.add(latLngs.get(latLngs.size() - 1));
                latLngsOne.add(ll);
                if (latLngsOne.size() > 1) {
                    // 覆盖物参数配置
                    OverlayOptions ooPolylineA = new PolylineOptions()
                            .width(5)// 设置折线线宽， 默认为 5， 单位：像素
                            .points(latLngsOne)// 设置折线坐标点列表
                            .dottedLine(true);
                    mBaiduMap.addOverlay(ooPolylineA);
                }
                hideProgressDialog();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override


        public void onReceiveLocation(BDLocation location) {
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mCurrentPt = new LatLng(location.getLatitude(), location.getLongitude());

            if (isFirstPoint) {
                isFirstPoint = false;
                MapStatus.Builder builder1 = new MapStatus.Builder();
                builder1.target(mCurrentPt).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
            }
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据


            if (location.getRadius() > 20) {
                if (i == 3) {
                    ll = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    initCoord();
                    initPoint();
                    MarkerOptions ooA = new MarkerOptions().position(ll).icon(mbitmap_my).zIndex(9).draggable(false);
                    mBaiduMap.addOverlay(ooA);
                    TourVistLonAndLatBean lonAndLat = new TourVistLonAndLatBean();
                    lonAndLat.setLat(ll.latitude);
                    lonAndLat.setLng(ll.longitude);
                    longAndLatJson = gson.toJson(lonAndLat);
                    //巡检点数大于0
                    if (latLngs.size() > 0) {
                        List<LatLng> latLngsOne = new ArrayList<>();
                        latLngsOne.add(latLngs.get(latLngs.size() - 1));
                        latLngsOne.add(ll);
                        if (latLngsOne.size() > 1) {
                            // 覆盖物参数配置
                            OverlayOptions ooPolylineA = new PolylineOptions()
                                    .width(5)// 设置折线线宽， 默认为 5， 单位：像素
                                    .points(latLngsOne)// 设置折线坐标点列表
                                    .color(getResources().getColor(R.color.red6367))
                                    .dottedLine(true);
                            mBaiduMap.addOverlay(ooPolylineA);
                        }
                    }
                    mLocationClient.unRegisterLocationListener(myLocationListener);
                    myLocationListener = null;
                    hideProgressDialog();
                } else {
                    i++;
                }
            } else {

                ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                initCoord();
                initPoint();
                MarkerOptions ooA = new MarkerOptions().position(ll).icon(mbitmap_my).zIndex(9).draggable(true);
                mBaiduMap.addOverlay(ooA);
                TourVistLonAndLatBean lonAndLat = new TourVistLonAndLatBean();
                lonAndLat.setLat(ll.latitude);
                lonAndLat.setLng(ll.longitude);
                longAndLatJson = gson.toJson(lonAndLat);
                //巡检点数大于0
                if (latLngs.size() > 0) {
                    List<LatLng> latLngsOne = new ArrayList<>();
                    latLngsOne.add(latLngs.get(latLngs.size() - 1));
                    latLngsOne.add(ll);
                    if (latLngsOne.size() > 1) {
                        // 覆盖物参数配置
                        OverlayOptions ooPolylineA = new PolylineOptions()
                                .width(5)// 设置折线线宽， 默认为 5， 单位：像素
                                .points(latLngsOne)// 设置折线坐标点列表
                                .color(getResources().getColor(R.color.red6367))
                                .dottedLine(true);
                        mBaiduMap.addOverlay(ooPolylineA);
                    }
                }

                mLocationClient.unRegisterLocationListener(myLocationListener);
                myLocationListener = null;
                hideProgressDialog();
            }
        }
    }


    @Override
    public void launchActivityByRouter(@NonNull String path) {
        ARouterUtils.navigation(this, path);
    }


    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }


    /**
     * 隐藏或结束加载更多
     * true 结束  false 隐藏
     */
    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            inspectionMapAdapter.loadMoreEnd(true);
        } else {
            inspectionMapAdapter.loadMoreComplete();
        }
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

    @Override
    protected void onResume() {
        map.onResume();
        initMap();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //在activity执行onPause时必须调用mMapView. onPause ()
        map.onPause();
        super.onPause();
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        if (mLocationClient != null) {
            if (myLocationListener != null) {
                mLocationClient.unRegisterLocationListener(myLocationListener);
            }
            mLocationClient.stop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 取消注册的位置提醒监听
//        mLocationClient.removeNotifyEvent(mNotifyLister);
        // 停止定位
        if (mbitmap != null) {
            // 释放资源
            mbitmap.recycle();
            mbitmap = null;
        }

        // 释放地图资源
        if (mBaiduMap != null) {
            mBaiduMap.setMyLocationEnabled(false);
            mBaiduMap.clear();
            mBaiduMap = null;
        }
        //在activity执行onDestroy时必须调用mMapView.onDestroy()
        if (map != null) {
            map.onDestroy();
            map = null;
        }
        myLocationListener = null;
        mLocationClient = null;
        super.onDestroy();
    }

    @OnClick(R.id.icon_location)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            LatLng ll = null;
            if (mCurrentPt != null) {
                ll = mCurrentPt;
            } else {
                ll = mDefaultPt;
            }

            MapStatus.Builder builder = new MapStatus.Builder();
//        builder.target(ll).zoom(18.0f);
            builder.target(ll);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.submit, R.id.select_resource, R.id.things_submit, R.id.right_btn})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.right_btn:
                    if (ArmsUtils.isEmpty(longAndLatJson)) {
                        ArmsUtils.makeText("请稍等！我在酝酿精准定位！");
                    } else {
                        if (InspectionAddActivity.TARGET_NEW_POINT.equals(is_new_point)) {
                            ARouter.getInstance().build(RouterHub.APP_INSPECTIONADDACTIVITY)
                                    .withString(InspectionMapActivity.POINTS_IN_JSON, longAndLatJson)
                                    .withString(InspectionMapActivity.IS_NEW_POINT, is_new_point)
                                    .withString(ARouerConstant.TITLE, "新增巡检记录")
                                    .withSerializable("xunjianModel", model)
                                    .withString(RESOURCE, resource)
                                    .navigation();
                        } else if (InspectionAddActivity.NEW.equals(is_new_point)) {
                            ARouter.getInstance().build(RouterHub.APP_INSPECTIONADDACTIVITY)
                                    .withString(InspectionMapActivity.POINTS_IN_JSON, longAndLatJson)
                                    .withString(ARouerConstant.TITLE, "新增巡检记录")
                                    .withString(InspectionMapActivity.IS_NEW_POINT, is_new_point)
                                    .navigation();
                        } else if (InspectionAddActivity.NEW_TASK.equals(is_new_point)) {
                            ARouter.getInstance().build(RouterHub.APP_INSPECTIONADDACTIVITY)
                                    .withString(InspectionMapActivity.POINTS_IN_JSON, longAndLatJson)
                                    .withString(InspectionMapActivity.IS_NEW_POINT, is_new_point)
                                    .withString(ARouerConstant.TITLE, "新增巡检记录")
                                    .withSerializable("TASK_MODEL", elementsBean)
                                    .withString(RESOURCE, elementsBean.getName())
                                    .withString("addInspId", addInspId)
                                    .withInt("NEW_TASK_ITEM", 1)
                                    .navigation();
                        } else if (InspectionAddActivity.MINE_TASK.equals(is_new_point)) {
                            ARouter.getInstance().build(RouterHub.APP_INSPECTIONADDACTIVITY)
                                    .withString(InspectionMapActivity.POINTS_IN_JSON, longAndLatJson)
                                    .withString(InspectionMapActivity.IS_NEW_POINT, is_new_point)
                                    .withString(ARouerConstant.TITLE, "新增巡检记录")
                                    .withParcelable("MissionConditionsBean", missionConditionsBean)
                                    .withParcelable("MINE_TASK_MODEL", listBean)
                                    .withString(RESOURCE, listBean.getName())
                                    .navigation();


                        } else {
                            ARouter.getInstance().build(RouterHub.APP_INSPECTIONADDACTIVITY)
                                    .withString(InspectionMapActivity.POINTS_IN_JSON, longAndLatJson)
                                    .withString(InspectionMapActivity.IS_NEW_POINT, "TAGETNEW")
                                    .withString(ARouerConstant.TITLE, "新增巡检记录")
                                    .withSerializable("xunjianModel", model)
                                    .withString(RESOURCE, resource)
                                    .navigation();
                        }
                    }


                    break;
                case R.id.select_resource:
                    ARouter.getInstance().build(RouterHub.APP_INFORMATIONCOLLECTIONACTIVITY)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_INSPECTIONMAPACTIVITY)
                            .withInt(TourVisitActivity.TYPE, TourVisitActivity.TYPE_IPQC)
                            .navigation();


                    break;
                case R.id.things_submit:
                    break;

                case R.id.submit:

                    if (InspectionAddActivity.NEW_TASK.equals(is_new_point)) {
                        if (TextUtils.isEmpty(elementsBean.getInspectionRecordId()) && TextUtils.isEmpty(addInspId)) {
                            ArmsUtils.makeText("您当前没有巡检记录！");
                        } else {
                            if (!TextUtils.isEmpty(addInspId)) {
                                assert mPresenter != null;
                                mPresenter.setIsInspectionComplete(elementsBean.getTaskId(), addInspId);
                            } else {
                                assert mPresenter != null;
                                mPresenter.setIsInspectionComplete(elementsBean.getTaskId(), elementsBean.getInspectionRecordId());
                            }

                        }

                    } else if (InspectionAddActivity.NEW.equals(is_new_point)) {
                        ArmsUtils.makeText("您当前没有巡检记录！");
                    } else if (InspectionAddActivity.TARGET_NEW_POINT.equals(is_new_point)) {
                        ArmsUtils.makeText("您当前没有巡检记录！");
                    } else {
                        assert mPresenter != null;
                        mPresenter.setIsInspectionComplete("", model.getId());
                    }


                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否完成巡检
     *
     * @param itemBean
     */
    @Override
    public void setIsInspectionComplete(String itemBean) {
        EventBus.getDefault().post(new EventBusBean(EventBusConstant.IS_REFRESH), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_PATROLSFRAGMENT);
        killMyself();
    }
}
