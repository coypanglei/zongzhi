package com.weique.overhaul.v2.mvp.ui.activity.map;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
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
import com.weique.overhaul.v2.app.utils.UserGridUtil;
import com.weique.overhaul.v2.di.component.DaggerMapComponent;
import com.weique.overhaul.v2.mvp.contract.MapContract;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.MapPresenter;

import org.simple.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 地图页
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_MAPACTIVITY)
public class MapActivity extends BaseActivity<MapPresenter> implements MapContract.View {
    @Inject
    Gson gson;

    @BindView(R.id.map_vessel)
    FrameLayout mapVessel;
    MapView map;

    @BindView(R.id.state)
    TextView mStateBar;


    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.address_detail)
    TextView addressDetail;

    private BaiduMap mBaiduMap;

    /**
     * 上个界面的标识  用于eventbus 回传数据  定向
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    /**
     * 是否允许修改坐标
     */
    public static final String ADDRESS_CAN_CHANGED = "addressCanChanged";
    @Autowired(name = ADDRESS_CAN_CHANGED)
    boolean addressCanChanged;
    /**
     * 上次的经纬度定位
     */
    public static final String LONANDLAT = "LonAndLat";
    @Autowired(name = LONANDLAT)
    String lonAndLat;

    /**
     * 多边形信息
     */
    public static final String POINTS_IN_JSON = "pointsInJSON";
    @Autowired(name = POINTS_IN_JSON)
    String pointsInJSON;


    private int mStrokeWidth = 5;

    private List<List<LatLng>> latLngLists;
    private BitmapDescriptor mbitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
    /**
     * 用户点击的点
     */
    private LatLng mCurrentPt;
    /**
     * 用户点击 复位  没有 mCurrentPt 时  用默认点 - 网格集合的第一个点
     */
    private LatLng mDefaultPt;
    /**
     * 用户的定位点
     */
    private LatLng mLocalPt;


    public LocationClient mLocationClient;
    @BindView(R.id.bt)
    LinearLayout bt;
    private boolean isFirstLoc = true; // 是否首次定位
    /**
     * 用户点击的 点
     */
    private StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean;
    private List<PolygonOptions> ooPolygons;
    private MyLocationListener myLocationListener;
    /**
     * 区分 查看 坐标点地图   还是 在选择 坐标点地图
     */
    private boolean isChecked = false;
    private GeoCoder mSearch = null;
    private String addressName = "未发现地图定位地址";


    @Override
    protected void onResume() {
        map.onResume();
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

        // 释放检索对象
        try {
            if (mLocationClient != null) {
                mLocationClient.stop();
                mLocationClient = null;
            }
            if (mBaiduMap != null) {
                mBaiduMap.setMyLocationEnabled(false);
                mBaiduMap.clear();
                mBaiduMap = null;
            }

            myLocationListener = null;
            if (map != null) {
                map.onDestroy();
                map = null;
            }

            mapVessel.removeAllViews();
            if (mSearch != null) {
                mSearch.destroy();
                mSearch = null;
            }
            // 停止定位
            if (mbitmap != null) {
                // 释放资源
                mbitmap.recycle();
                mbitmap = null;
            }

            // 释放地图资源

            //在activity执行onDestroy时必须调用mMapView.onDestroy()
            if (map != null) {
                map.onDestroy();
                map = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.onDestroy();
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMapComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_map;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            if (!AppUtils.isLocationEnabled(this)) {
                ArmsUtils.makeText("GPS关闭,会使您的定位不准确");
            }
            showLoading();
            ARouter.getInstance().inject(this);
            setTitle("地图");
            mSearch = GeoCoder.newInstance();
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
                        addressName = result.getAddress();
                        addressDetail.setText("具体地址:" + addressName);
                        hideLoading();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            rightBtnText.setText(ArmsUtils.getString(this, R.string.sure));
            isChecked = source.equals(RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY)
                    || source.equals(RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
                    || source.equals(RouterHub.APP_STANDARDADDRESSLOOKACTIVITY);
            latLngLists = new ArrayList<>();
            ooPolygons = new ArrayList<>();
            UserGridUtil.gridJsonToListLatLng(pointsInJSON, latLngLists, ooPolygons);
            //设置多边形的第一个点为基点
            if (latLngLists.size() > 0 && latLngLists.get(0).size() > 0) {
                mDefaultPt = latLngLists.get(0).get(0);
            }
            initMap();

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
            //绘制多边形
            if (ooPolygons != null && ooPolygons.size() > 0) {
                for (PolygonOptions ooPolygon : ooPolygons) {
                    if (ooPolygon != null) {
                        mBaiduMap.addOverlay(ooPolygon);
                    }
                }
            } else {
                ArmsUtils.makeText(getString(R.string.init_gridding_fail));
            }
            if (StringUtil.isNotNullString(lonAndLat)) {
                StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean oldBathBean = null;
                try {
                    Timber.e(lonAndLat);
                    oldBathBean = gson.fromJson(lonAndLat, StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (oldBathBean != null) {
                    mCurrentPt = new LatLng(oldBathBean.getLat(), oldBathBean.getLng());
                    if (UserGridUtil.pointInGrid(mCurrentPt, latLngLists)) {
                        MarkerOptions ooA = new MarkerOptions().position(mCurrentPt).icon(mbitmap);
                        mBaiduMap.addOverlay(ooA);
                    } else {
                        ArmsUtils.makeText("您不在负责区域内");
                    }
                    if (mCurrentPt != null) {
                        initAddress(mCurrentPt);
                    }
                }
                hideLoading();
            } else {
                //用户定位 再网格内
                if (UserGridUtil.pointInGrid(mLocalPt, latLngLists)) {
                    mCurrentPt = mLocalPt;
                    MarkerOptions ooA = new MarkerOptions().position(mCurrentPt).icon(mbitmap);
                    mBaiduMap.addOverlay(ooA);
                    //在网格内设置默认 定点  和 默认 地理反编码
                    BigDecimal longitude = new BigDecimal(mCurrentPt.longitude);
                    BigDecimal latitude = new BigDecimal(mCurrentPt.latitude);
                    pathBean = new StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean
                            (longitude.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue(),
                                    latitude.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue());
                    initAddress(mCurrentPt);
                } else {
                    if (RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY.equals(source)
                            && !addressCanChanged) {
                        ArmsUtils.makeText("不允许网格外上报事件");
                    } else {
                        ArmsUtils.makeText("您不在负责区域内");
                    }
                    hideLoading();
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }


    private void initMap() {

        try {
            myLocationListener = new MyLocationListener();
            mLocationClient = new LocationClient(getApplicationContext());
            mLocationClient.registerLocationListener(myLocationListener);    //注册监听函数
            //获取地图控件引用
            map = new MapView(this);
            mapVessel.addView(map);
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
            //开启定位
            mLocationClient.start();
            //图片点击事件，回到定位点
            mLocationClient.requestLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 配置定位SDK参数
     */
    private void initLocation() {
        try {
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            option.setCoorType(Constant.CoorType_BD09LL);//可选，默认gcj02，设置返回的定位结果坐标系
            int span = 0;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对地图事件的消息响应
     */
    private void initListener() {
        try {
            mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
                /**
                 * 单击地图
                 */
                @Override
                public void onMapClick(LatLng point) {
                    MapActivity.this.onMapClick(point);
                }

                /**
                 * 单击地图中的POI点
                 */
                @Override
                public void onMapPoiClick(MapPoi poi) {
                    MapActivity.this.onMapClick(poi.getPosition());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 点击地图点位
     *
     * @param point point
     */
    private void onMapClick(LatLng point) {
        try {
            if (isChecked) {
                return;
            }
            if (RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY.equals(source)
                    && !addressCanChanged) {
                ArmsUtils.makeText("不允许修改上报定位坐标");
                return;
            }
            if (UserGridUtil.pointInGrid(point, latLngLists)) {
                mCurrentPt = point;
                updateMapState();
                initAddress(mCurrentPt);
            } else {
                ArmsUtils.makeText("请点击网格内区域");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据经纬度获取附近 gis 标准地址
     */
    private void initAddress(LatLng latLng) {
        try {
            ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption()
                    .location(latLng) // 设置反地理编码位置坐标
                    .newVersion(1) // 设置是否返回新数据 默认值0不返回，1返回
                    .radius(1000) //  POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                    .pageNum(0);
            // 发起反地理编码请求，该方法必须在监听之后执行，否则会在某些场景出现拿不到回调结果的情况
            mSearch.reverseGeoCode(reverseGeoCodeOption);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 更新地图状态显示面板
     */
    private void updateMapState() {
        try {
            mBaiduMap.clear();
            if (mStateBar == null) {
                return;
            }
            if (mCurrentPt == null) {
                return;
            } else {
                MarkerOptions ooA = new MarkerOptions().position(mCurrentPt).icon(mbitmap).zIndex(9).draggable(true);
                mBaiduMap.addOverlay(ooA);
            }
            BigDecimal longitude = new BigDecimal(mCurrentPt.longitude);
            BigDecimal latitude = new BigDecimal(mCurrentPt.latitude);
            pathBean = new StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean
                    (longitude.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue(),
                            latitude.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue());
            if (ooPolygons != null && ooPolygons.size() > 0) {
                for (PolygonOptions ooPolygon : ooPolygons) {
                    if (ooPolygon != null) {
                        mBaiduMap.addOverlay(ooPolygon);
                    }
                }
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
            mCurrentPt = new LatLng(location.getLatitude(), location.getLongitude());
            mLocalPt = mCurrentPt;
            // 构造定位数据
            try {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(location.getDirection()).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                // 设置定位数据
                mBaiduMap.setMyLocationData(locData);
                // 当不需要定位图层时关闭定位图层
                if (isFirstLoc) {
                    isFirstLoc = false;
                    //当前本人定位
//                    LatLng ll = new LatLng(location.getLatitude(),
//                            location.getLongitude());
//                    if (pathBean == null || pathBean.getLat() == 0 || pathBean.getLng() == 0) {
//                        BigDecimal longitude = new BigDecimal(ll.longitude);
//                        BigDecimal latitude = new BigDecimal(ll.latitude);
//                        pathBean = new StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean
//                                (longitude.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue(),
//                                        latitude.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue());
//                    }

                    if (latLngLists != null && latLngLists.size() > 0) {
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(getCenterOfGravityPoint(latLngLists.get(0)));
                        builder.zoom(18.0f);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    }
                    mLocationClient.unRegisterLocationListener(myLocationListener);
                    if (RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY.equals(source)
                            || addressCanChanged) {

                    }
                }
                initListener();
                initCoord();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @OnClick({R.id.bt, R.id.right_btn, R.id.normal_layout, R.id.statellite_layout})
    public void onViewClicked(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.bt:
                   /* LatLng ll = null;
                    if (mCurrentPt != null) {
                        ll = mCurrentPt;
                    } else {
                        ll = mDefaultPt;
                    }*/
                    MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(mDefaultPt);
                    mBaiduMap.animateMapStatus(mapStatusUpdate);
                    break;
                case R.id.right_btn:
                    if (pathBean != null && !isChecked) {
                        EventBus.getDefault().post(
                                new EventBusBean<>(EventBusConstant.UPDATE_UP_LOCATION, "", pathBean), source);
                    }
                    if (StringUtil.isNotNullString(addressName)) {
                        EventBus.getDefault().post(
                                new EventBusBean<>(EventBusConstant.SELF_LOCALIZATION, "", addressName), source);
                    }
                    finish();
                    break;
                case R.id.normal_layout:
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    break;
                // 卫星图
                case R.id.statellite_layout:
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    public void launchActivityByRouter(@NonNull String path) {
        ARouterUtils.navigation(this, path);
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


    /**
     * 根据多边形获取中心点
     *
     * @param mPoints
     * @return
     */
    private LatLng getCenterOfGravityPoint(List<LatLng> mPoints) {
        double area = 0.0;//多边形面积
        double Gx = 0.0, Gy = 0.0;// 重心的x、y
        try {
            for (int i = 1; i <= mPoints.size(); i++) {
                double iLat = mPoints.get(i % mPoints.size()).latitude;
                double iLng = mPoints.get(i % mPoints.size()).longitude;
                double nextLat = mPoints.get(i - 1).latitude;
                double nextLng = mPoints.get(i - 1).longitude;
                double temp = (iLat * nextLng - iLng * nextLat) / 2.0;
                area += temp;
                Gx += temp * (iLat + nextLat) / 3.0;
                Gy += temp * (iLng + nextLng) / 3.0;
            }
            Gx = Gx / area;
            Gy = Gy / area;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LatLng(Gx, Gy);
    }


}
