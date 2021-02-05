package com.weique.overhaul.v2.mvp.ui.activity.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.AppLifecycleImpl;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.service.localtion.LocationService;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserGridUtil;
import com.weique.overhaul.v2.di.component.DaggerNewMapComponent;
import com.weique.overhaul.v2.mvp.contract.NewMapContract;
import com.weique.overhaul.v2.mvp.model.entity.AdminGridBean;
import com.weique.overhaul.v2.mvp.model.entity.LocationAndName;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.NewMapPresenter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 新的地图界面
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_NEWMAPACTIVITY)
public class NewMapActivity extends BaseActivity<NewMapPresenter> implements NewMapContract.View {

    @BindView(R.id.map_vessel)
    FrameLayout mapVessel;
    MapView mMapView;
    @BindView(R.id.local_address)
    TextView localAddress;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor mbitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
    private LocationService locationService;
    private GeoCoder mSearch;

    @Inject
    Gson gson;
    /**
     * 上一个界面的路由
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;


    /**
     * 点  网格信息
     */
    String pointsJson;
    /**
     * 根据这个departmentId 获取区域信息
     */
    @Autowired(name = ARouerConstant.DEPARTMENT_ID)
    String departmentId;

    /**
     * 之前有选中的点  传过来
     */
    @Autowired(name = ARouerConstant.LAST_POINT)
    LatLng lastPoint;


    /**
     * 用户所在位置
     */
    private LatLng mLocaltion;
    /**
     * 用户点击的 点定位
     */
    private LatLng clickLocaltion;
    private MyLocationListener myLocationListener;
    private List<List<LatLng>> latLngLists;
    private List<PolygonOptions> ooPolygons;
    private Overlay overlay;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNewMapComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_new_map;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            rightBtnText.setText(getString(R.string.sure));
            mMapView = new MapView(this);
            mapVessel.addView(mMapView);
            if (lastPoint != null) {
                clickLocaltion = lastPoint;
            }
            mPresenter.getLocationPermission();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始请求 map
     */
    @Override
    public void startDrawMap() {
        if (StringUtil.isNotNullString(departmentId)) {
            mPresenter.getDepartmentArea(departmentId);
        } else {
            initMap();
        }
    }

    /**
     * 绘制多边形区域
     */
    private void drawPolygons() {
        try {
            if (StringUtil.isNotNullString(pointsJson)) {
                latLngLists = new ArrayList<>();
                ooPolygons = new ArrayList<>();
                List<StandardAddressStairBean.PointsInJsonBean.PolygoysBean> polygoysBeans = null;
                try {
                    StandardAddressStairBean.PointsInJsonBean areaMapBean = gson.fromJson(pointsJson, StandardAddressStairBean.PointsInJsonBean.class);
                    polygoysBeans = areaMapBean.getPolygoys();
                    if (polygoysBeans == null) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        AdminGridBean adminGridInfoBean = gson.fromJson(pointsJson, AdminGridBean.class);
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
                if (polygoysBeans != null && polygoysBeans.size() > 0) {
                    for (StandardAddressStairBean.PointsInJsonBean.PolygoysBean polygoys : polygoysBeans) {
                        List<LatLng> latLngList = new ArrayList<>();
                        if (polygoys != null && polygoys.getPath() != null && polygoys.getPath().size() > 0) {
                            for (StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean : polygoys.getPath()) {
                                LatLng latLng = new LatLng(pathBean.getLat(), pathBean.getLng());
                                latLngList.add(latLng);
                            }
                            if (latLngList.size() > 2) {
                                ooPolygons.add(new PolygonOptions()
                                        .points(latLngList)
                                        .stroke(new Stroke(5, Color.parseColor(polygoys.getStyleOptions().getStrokeColor().replace("#", "#40"))))
                                        .fillColor(Color.parseColor(polygoys.getStyleOptions().getFillColor().replace("#", "#40"))));
                                latLngLists.add(latLngList);
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
                    //设置标记点 点击点
                    if (UserGridUtil.pointInGrid(clickLocaltion, latLngLists)) {
                        MarkerOptions ooA = new MarkerOptions().position(clickLocaltion).icon(mbitmap).zIndex(9).draggable(false);
                        mBaiduMap.addOverlay(ooA);
                    } else {
                        ArmsUtils.makeText("您不在负责区域内");
                    }
                } else {
                    ArmsUtils.makeText(getString(R.string.init_gridding_fail));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        try {
            mMapView.showZoomControls(true);
            mBaiduMap = mMapView.getMap();
            //显示卫星图层
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            mBaiduMap.setMyLocationEnabled(true);
            initListener();
            locationService = AppLifecycleImpl.getLocationService();
            myLocationListener = new MyLocationListener();
            locationService.registerListener(myLocationListener);
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
            locationService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            mbitmap.recycle();
            mbitmap = null;
            if (locationService != null) {
                if (myLocationListener != null) {
                    locationService.unregisterListener(myLocationListener);
                }
                locationService.stop();
                locationService = null;
            }
            if (mBaiduMap != null) {
                mBaiduMap.setMyLocationEnabled(false);
                mBaiduMap.clear();
                mBaiduMap = null;
            }
            if (mMapView != null) {
                mMapView.onDestroy();
                mMapView = null;
            }
            mapVessel.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
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

    /**
     * 地图点击
     *
     * @param latLng latLng
     */
    private void onMapClick(LatLng latLng) {
        try {
            if (latLngLists == null || latLngLists.size() <= 0 || UserGridUtil.pointInGrid(latLng, latLngLists)) {
                showLoading();
                if (overlay != null && !overlay.isRemoved()) {
                    overlay.remove();
                }
                MarkerOptions ooA = new MarkerOptions().position(latLng).icon(mbitmap).zIndex(9).draggable(false);
                overlay = mBaiduMap.addOverlay(ooA);
                //坐标反编码为地址
                ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption()
                        .location(latLng)
                        .newVersion(1)
                        .radius(1000)
                        .pageNum(0);
                // 发起反地理编码请求，该方法必须在监听之后执行，否则会在某些场景出现拿不到回调结果的情况
                mSearch.reverseGeoCode(reverseGeoCodeOption);
            } else {
                ArmsUtils.makeText("请选择区域内点位");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        //点击地图监听
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                NewMapActivity.this.onMapClick(latLng);
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                NewMapActivity.this.onMapClick(mapPoi.getPosition());
            }
        });
        mSearch = GeoCoder.newInstance();
        //反编码监听
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                try {
                    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                          Toast.makeText(MapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
                        return;
                    }
                    localAddress.setText(result.getAddress());
                    clickLocaltion = result.getLocation();
                    hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            try {
                //绘制用户定位
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(location.getDirection()).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                //设置定位点到 成员变量
                mLocaltion = new LatLng(location.getLatitude(), location.getLongitude());
                //默认设置 定位点 为点击 点
                if (clickLocaltion == null) {
                    clickLocaltion = mLocaltion;
                }
                //设置显示zoom 和显示中心点 mLocaltion
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(mLocaltion);
                builder.zoom(18.0f);
                //地图状态更新
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build());
                mBaiduMap.animateMapStatus(mapStatusUpdate);
                //定位位置显示
                localAddress.setText(location.getAddrStr());
                //停止定位服务
                locationService.stop();
                //绘制多边形区域
                drawPolygons();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param view view
     */
    @OnClick({R.id.right_btn, R.id.bt})
    public void onClickByView(View view) {
        try {
            switch (view.getId()) {
                case R.id.right_btn:
                    TourVistLonAndLatBean lonAndLat = null;
                    if (clickLocaltion != null) {
                        lonAndLat = new TourVistLonAndLatBean(clickLocaltion.latitude, clickLocaltion.longitude);
                    } else {
                        lonAndLat = new TourVistLonAndLatBean(mLocaltion.latitude, mLocaltion.longitude);
                    }
                    LocationAndName locationAndName = new LocationAndName(lonAndLat, localAddress.getText().toString());
                    EventBus.getDefault().post(new EventBusBean<LocationAndName>
                            (EventBusConstant.UPDATE_UP_LOCATION, locationAndName), source);
                    finish();
                    break;
                case R.id.bt:
                    MapStatusUpdate mapStatusUpdate;
                    if (clickLocaltion != null) {
                        mapStatusUpdate = MapStatusUpdateFactory.newLatLng(clickLocaltion);
                    } else {
                        mapStatusUpdate = MapStatusUpdateFactory.newLatLng(mLocaltion);
                    }
                    mBaiduMap.animateMapStatus(mapStatusUpdate);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showOpenGpsDialog() {
        try {
            new CommonDialog.Builder(this).setTitle("提示")
                    .setContent("GPS未开启，会导致您的定位不准确")
                    .setNegativeBtnShow(false)
                    .setCancelable(false)
                    .setPositiveButton("去设置", (v, commonDialog) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, Constant.GPS_STATE);
                    }).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showArea(String pointsInJSON) {
        pointsJson = pointsInJSON;
        initMap();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case Constant.GPS_STATE:
                        mPresenter.getLocationPermission();
                        break;
                    default:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
