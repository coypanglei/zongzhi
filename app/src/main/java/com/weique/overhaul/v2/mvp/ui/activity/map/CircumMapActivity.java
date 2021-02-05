package com.weique.overhaul.v2.mvp.ui.activity.map;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.blankj.utilcode.util.ObjectUtils;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.AppLifecycleImpl;
import com.weique.overhaul.v2.app.baidu.clustering.ClusterItem;
import com.weique.overhaul.v2.app.baidu.clustering.ClusterManager;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.service.localtion.LocationService;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerCircumMapComponent;
import com.weique.overhaul.v2.mvp.contract.CircumMapContract;
import com.weique.overhaul.v2.mvp.model.entity.PointsBean;
import com.weique.overhaul.v2.mvp.model.entity.PointsDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.PointsListBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.presenter.CircumMapPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationDynamicFormSelectActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeCompat;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 *
 * @author Administrator
 * @description: 地图周边
 * ================================================
 */
@Route(path = RouterHub.APP_CIRCUM_MAP_ACTIVITY, name = "地图周边")
public class CircumMapActivity extends BaseActivity<CircumMapPresenter> implements CircumMapContract.View, BaiduMap.OnMapLoadedCallback {

    @BindView(R.id.map_vessel)
    FrameLayout mapVessel;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /**
     * 绘制的 用户的圈的范围
     */

    private ClusterManager<MyItem> mClusterManager;
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationService locationService;
    private MyLocationListener myLocationListener;
    private GeoCoder mSearch;
    private Overlay overlay;
    private MapStatus mMapStatus;
    private View view;

    /**
     * 所有网格创建
     */
    private List<OverlayOptions> ooPolygons = new ArrayList<>();
    /**
     * 画笔大小
     */
    private int mStrokeWidth = 5;

    @Inject
    Gson gson;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCircumMapComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_circum_map;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rightBtnText.setText("筛选");
        //map 添加
        mMapView = new MapView(this);
        mapVessel.addView(mMapView);
        mBaiduMap = mMapView.getMap();
        initMap();
        initListener();
    }

    // 定义点聚合管理类ClusterManager
    List<MyItem> items = new ArrayList<MyItem>();

    private boolean mFinish;


    @Override
    public void setPoitons(List<PointsListBean.ElementPointsBean> points) {


        try {
            items.clear();
            for (PointsListBean.ElementPointsBean pointsBean : points) {
                items.add(new MyItem(new LatLng(pointsBean.getPoint().getLat(), pointsBean.getPoint().getLng()), "1", pointsBean));
            }
            initCluster();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setDepartmentsPoints(List<PointsBean> points) {


        //地图状态更新
        try {
            for (PointsBean pointsBean : points) {
                initCoord(pointsBean.getPointsInJSON());
            }
            mBaiduMap.addOverlays(ooPolygons);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getActivity() {
        return this;
    }

    /**
     * 筛选的集合
     */
    @Override
    public void setTypeId(List<PointsListBean.ElementPointsBean> points) {
        try {
            mBaiduMap.clear();
            //构造CircleOptions对象
            //半径 1公里
            CircleOptions mCircleOptions = new CircleOptions().center(mLocaltion)
                    .radius(500)
                    .fillColor(0x710000FF) //填充颜色
                    .stroke(new Stroke(5, 0x7C00FF00)); //边框宽和边框颜色
            mBaiduMap.addOverlay(mCircleOptions);

            LatLngBounds bound = mBaiduMap.getMapStatus().bound;
            Map<String, Object> map = new HashMap<>();

            //地图状态更新
            try {
                mBaiduMap.addOverlays(ooPolygons);
            } catch (Exception e) {
                e.printStackTrace();
            }
            items.clear();
            for (PointsListBean.ElementPointsBean pointsBean : points) {
                items.add(new MyItem(new LatLng(pointsBean.getPoint().getLat(), pointsBean.getPoint().getLng()), "1", pointsBean));
            }
            initCluster();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void setClickPoint(String dataStructureInJson, MyItem myItem) {
        ARouter.getInstance().build(RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
                .withString(InformationDynamicFormSelectActivity.DYNAMIC_FORM_JSON, dataStructureInJson)
                .withString(ARouerConstant.ID, myItem.getmElementPointsBean().getCustId())
                .withString(InformationDynamicFormSelectActivity.TYPE_ID, myItem.getmElementPointsBean().getElementTypeId())
                .navigation();
    }

    @Override
    public void setClickPoint(String dataStructureInJson, PointsDetailBean pointsDetailBean) {
        ARouter.getInstance().build(RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
                .withString(InformationDynamicFormSelectActivity.DYNAMIC_FORM_JSON, dataStructureInJson)
                .withString(ARouerConstant.ID, pointsDetailBean.getCustId())
                .withString(InformationDynamicFormSelectActivity.TYPE_ID, pointsDetailBean.getElementTypeId())
                .navigation();
    }


    /**
     * 初始化参照物  网格  和 标记点
     */
    private void initCoord(String pointsInJSON) {
        try {
            //修改时  会传过来一个点
            if (StringUtil.isNotNullString(pointsInJSON)) {
                LatLng latLng;
                List<StandardAddressStairBean.PointsInJsonBean.PolygoysBean> polygoysBeans = null;
                try {
                    StandardAddressStairBean.PointsInJsonBean pointsInJsonBean = gson.fromJson(pointsInJSON, StandardAddressStairBean.PointsInJsonBean.class);
                    polygoysBeans = pointsInJsonBean.getPolygoys();
                    if (ObjectUtils.isNotEmpty(polygoysBeans) && polygoysBeans.size() > 0) {
                        for (StandardAddressStairBean.PointsInJsonBean.PolygoysBean polygoys : polygoysBeans) {
                            List<LatLng> latLngList = new ArrayList<>();
                            if (polygoys != null && polygoys.getPath() != null && polygoys.getPath().size() > 0) {
                                for (StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean : polygoys.getPath()) {
                                    latLng = new LatLng(pathBean.getLat(), pathBean.getLng());

                                    latLngList.add(latLng);
                                }
                                ooPolygons.add(new PolygonOptions()
                                        .points(latLngList)
                                        .stroke(new Stroke(mStrokeWidth, Color.parseColor(polygoys.getStyleOptions().getStrokeColor().replace("#", "#40"))))
                                        .fillColor(Color.parseColor(polygoys.getStyleOptions().getFillColor().replace("#", "#40"))));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.right_btn_text, R.id.animation_view})
    public void onViewClicked(View view) {
        if (!mFinish) {
            return;
        }
        if (AppUtils.isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.right_btn_text:
                /**
                 *  屏幕宽度适配
                 */
                int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
                mPresenter.showPopup(R.id.toolbar, screenWidth);
                break;
            case R.id.animation_view:
                //updateMapPoints
                //开始定位
                locationService.start();
                break;
            default:
                break;
        }
    }


    //ClusterItem接口的实现类
    public class MyItem implements ClusterItem {
        LatLng mPosition;

        String mFlag;

        public PointsListBean.ElementPointsBean getmElementPointsBean() {
            return mElementPointsBean;
        }

        public void setmElementPointsBean(PointsListBean.ElementPointsBean mElementPointsBean) {
            this.mElementPointsBean = mElementPointsBean;
        }

        PointsListBean.ElementPointsBean mElementPointsBean;

        public MyItem(LatLng position) {
            mPosition = position;
        }

        public MyItem(LatLng position, String flag, PointsListBean.ElementPointsBean elementPointsBean) {
            mPosition = position;
            mFlag = flag;
            mElementPointsBean = elementPointsBean;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public BitmapDescriptor getBitmapDescriptor() {
            BitmapDescriptor bitmapDescriptor = null;
            try {
                Timber.e(mFlag);
                switch (mFlag) {
                    case "1":
                        bitmapDescriptor = BitmapDescriptorFactory
                                .fromResource(R.drawable.icon_location_one);
                        break;
                    case "2":
                        bitmapDescriptor = BitmapDescriptorFactory
                                .fromResource(R.drawable.icon_location_two);
                        break;
                    case "3":
                        bitmapDescriptor = BitmapDescriptorFactory
                                .fromResource(R.drawable.icon_location_three);
                        break;
                    case "4":
                        TextView textView = view.findViewById(R.id.tv_test);
                        textView.setText("ASDASDDDDAD");
                        bitmapDescriptor = BitmapDescriptorFactory
                                .fromView(view);
                        break;
                    default:
                        bitmapDescriptor = BitmapDescriptorFactory
                                .fromResource(R.drawable.icon_location_one);
                        break;
                }

                if (bitmapDescriptor != null) {
                    return bitmapDescriptor;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        public String getFlag() {
            return mFlag;
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
            locationService = AppLifecycleImpl.getLocationService();
            myLocationListener = new MyLocationListener();
            locationService.registerListener(myLocationListener);
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
            locationService.start();
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
                CircumMapActivity.this.onMapClick(latLng);
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                CircumMapActivity.this.onMapClick(mapPoi.getPosition());
            }
        });

        //反编码监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                try {
                    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                        return;
                    }
                    hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onMapLoaded() {
        // TODO Auto-generated method stub
        mMapStatus = new MapStatus.Builder().zoom(18).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
    }

    /**
     * 地图点击
     *
     * @param latLng latLng
     */
    private void onMapClick(LatLng latLng) {
        try {
            Timber.i("地图点击 +  " + latLng.toString());
            showLoading();
            if (overlay != null && !overlay.isRemoved()) {
                overlay.remove();
            }

//            MarkerOptions ooA = new MarkerOptions().position(latLng).icon(mbitmap).zIndex(9).draggable(false);
//            overlay = mBaiduMap.addOverlay(ooA);
            //坐标反编码为地址
            ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption()
                    .location(latLng)
                    .newVersion(1)
                    .radius(1000)
                    .pageNum(0);
            // 发起反地理编码请求，该方法必须在监听之后执行，否则会在某些场景出现拿不到回调结果的情况
            mSearch.reverseGeoCode(reverseGeoCodeOption);
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

    private LatLng mLocaltion;

    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {
            try {
                Timber.e("开始定位" + location.toString());
                //绘制用户定位
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(location.getDirection()).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                //设置定位点到 成员变量
                mLocaltion = new LatLng(location.getLatitude(), location.getLongitude());
                //设置显示zoom 和显示中心点 mLocaltion
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(mLocaltion);
                builder.zoom(16.0f);
                //地图状态更新
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build());
                mBaiduMap.animateMapStatus(mapStatusUpdate);

                //停止定位服务
                locationService.stop();
                //绘制周边区域 图层 500 一公里
                drawAreaRange(500, mLocaltion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 定义点聚合管理类ClusterManager
     */
    private void initCluster() {
        mClusterManager = new ClusterManager<MyItem>(this, mBaiduMap);
        mClusterManager.setMapStatusListenterl(new ClusterManager.MapStatusListenter() {
            @Override
            public void createZoomData(float zoom, LatLngBounds bound, boolean finish) {
//                Timber.e("缩放起了变化，现在缩放等级为" + zoom);

                try {
//                    Timber.e(bound.toString());
                    mFinish = finish;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }

            }
        });
        // 添加Marker点
        /*
          设置地图监听，当地图状态发生改变时，进行点聚合运算
         */
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        // 设置maker点击时的响应
        mBaiduMap.setOnMarkerClickListener(mClusterManager);
        mClusterManager.addItems(items);
        mClusterManager.updateMapPoints(mBaiduMap.getMapStatus());
        /**
         *  聚合点
         */
        mClusterManager.setOnClusterClickListener(cluster -> {
            /**
             *  屏幕宽度适配
             */
            int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
            List<String> ids = new ArrayList<>();
            for (MyItem myItem : cluster.getItems()) {
                ids.add(myItem.getmElementPointsBean().getCustId());
            }
            mPresenter.showPointsPopup(screenWidth, ids);
            return false;
        });
        /**
         *  单点点击
         */
        mClusterManager.setOnClusterItemClickListener(item -> {

            mPresenter.clickPoint(item.getmElementPointsBean().getElementTypeId(), item);
            return false;
        });
    }

    /**
     * 绘制周边区域 图层
     */
    private void drawAreaRange(int radius, LatLng latLng) {
        mBaiduMap.clear();
        //构造CircleOptions对象
        //半径 1公里
        CircleOptions mCircleOptions = new CircleOptions().center(latLng)
                .radius(radius)
                .fillColor(0x710000FF) //填充颜色
                .stroke(new Stroke(5, 0x7C00FF00)); //边框宽和边框颜色
        mBaiduMap.addOverlay(mCircleOptions);

        LatLngBounds bound = mBaiduMap.getMapStatus().bound;
        Map<String, Object> map = new HashMap<>();
        if (ooPolygons.size() <= 0) {
            mPresenter.getDepartmentsPoints(map);
        } else {
            //地图状态更新
            try {
                mBaiduMap.addOverlays(ooPolygons);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.clear();
        //范围
        map.put("distance", radius);
        //当前定位点 坐标
        map.put("lng", latLng.longitude);
        map.put("lat", latLng.latitude);
        mPresenter.getPoints(map);
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
            if (locationService != null) {
                if (myLocationListener != null) {
                    locationService.unregisterListener(myLocationListener);
                }
                locationService.stop();
                locationService = null;
            }
            if (mBaiduMap != null) {
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
}
