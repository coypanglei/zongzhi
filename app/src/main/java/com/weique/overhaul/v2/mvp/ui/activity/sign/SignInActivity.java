package com.weique.overhaul.v2.mvp.ui.activity.sign;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

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
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.RadarView;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerSignInComponent;
import com.weique.overhaul.v2.mvp.contract.SignInContract;
import com.weique.overhaul.v2.mvp.model.entity.AdminGridBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.MySignListBean;
import com.weique.overhaul.v2.mvp.model.entity.SigninStatusBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.presenter.SignInPresenter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * @author GreatKing
 */
@Route(path = RouterHub.APP_SIGNINACTIVITY)
public class SignInActivity extends BaseActivity<SignInPresenter> implements SignInContract.View {
    public static final String TAG = SignInActivity.class.getSimpleName();
    @Inject
    Gson gson;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.sign_in_info)
    TextView signInInfo;
    @BindView(R.id.sign_out_info)
    TextView signOutInfo;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.map_vessel)
    FrameLayout mapVessel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    MapView map;
    @BindView(R.id.address_name)
    TextView addressName;
    @BindView(R.id.radar)
    RadarView radar;
    private int i;

    private boolean isFirstPoint = true; // 是否首次定位点

    private List<PolygonOptions> ooPolygons;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor mbitmap = BitmapDescriptorFactory.fromResource(R.drawable.zhongdian_icon);
    private LocationClient mLocationClient;
    private MyLocationListener myListener;

    /**
     * 是否首次定位
     */
    private UiSettings mUiSettings;
    private LatLng ll;

    private boolean isFirstLoc = true; // 是否首次定位
    /**
     * 边线宽度
     */
    private int mStrokeWidth = 5;
    private List latLngList;
    private LatLng mCurrentPt;
    boolean isPolygonContains = false;
    private List<List<LatLng>> latLngLists;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSignInComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sign_in;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            if (!AppUtils.isLocationEnabled(this)) {
                ArmsUtils.makeText("GPS关闭,会使您的定位不准确");
            }
            setTitle("打卡");
            rightBtnText.setText("打卡记录");
            rightBtnText.setTextSize(16);
            rightBtnText.setTextColor(getResources().getColor(R.color.colorPrimary));
            refreshMap();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        if (mLocationClient != null) {
            if (myListener != null) {
                mLocationClient.unRegisterLocationListener(myListener);
                myListener = null;
            }
            mLocationClient.stop();
        }
        super.onStop();
    }

    private void initMap() {
        //获取地图控件引用
        try {
            if (map == null) {
                map = new MapView(this);
                mapVessel.addView(map);
            }
//            if (mBaiduMap == null) {
            mBaiduMap = map.getMap();
            //普通地图
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

            mUiSettings = mBaiduMap.getUiSettings();
            mUiSettings.setAllGesturesEnabled(true);
            //默认显示普通地图
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            //开启交通图
            //mBaiduMap.setTrafficEnabled(true);
            //开启热力图
            //mBaiduMap.setBaiduHeatMapEnabled(true);
            //比例尺
            map.showZoomControls(false);
            // 开启定位图层
            mBaiduMap.setMyLocationEnabled(true);
            //配置定位SDK参数
//            }
            //声明LocationClient类
            if (mLocationClient == null) {
                mLocationClient = new LocationClient(SignInActivity.this);
                initLocation();
            }
            if (myListener == null) {
                myListener = new MyLocationListener();
            }
            //注册监听函数
            mLocationClient.registerLocationListener(myListener);
            if (mLocationClient.isStarted()) {
                //开启定位
                mLocationClient.restart();
            } else {
                //开启定位
                mLocationClient.start();
            }
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
            int span = 1000;
//        option.setPriority(LocationClientOption.NetWorkFirst);
            option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            option.setOpenGps(true);//可选，默认false,设置是否使用gps
            option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
            option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
            // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
            option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            option.setIgnoreKillProcess(true);
            //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
            option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
            mLocationClient.setLocOption(option);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.right_btn, R.id.refresh})
    public void onViewClicked(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.right_btn:
                    ARouter.getInstance().build(RouterHub.APP_MYSIGNRECORDACTIVITY)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_SIGNINACTIVITY)
                            .navigation();
                    break;
                case R.id.refresh:
                    refreshMap();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshMap() {
        addressName.setHint("正在获取地址...");
        isFirstLoc = true;
        radar.start();
        initMap();
    }

    @Override
    public void setSignData(String data) {
        mPresenter.getSignStatus();
    }

    @Override
    public void setSignBtnStatus(SigninStatusBean status) {
        try {
            if (StringUtil.isNullString(addressName.getText().toString())) {
                ArmsUtils.makeText("未获取到您的定位地址，正在刷新定位");
//                refreshMap();
//                return;
            }
            List<MySignListBean.ListBean> list = status.getList();
            boolean isSignIn = false;
            boolean isSignOut = false;
            String signInTime = "";
            if (list != null && list.size() > 0) {
                for (MySignListBean.ListBean listBean : list) {
                    //签到
                    if (listBean.getEnumCheckInStatus() == 0) {
                        signInInfo.setText("已签到\n" + listBean.getCreateTime());
                        signInTime = listBean.getCreateTime();
                        isSignIn = true;
                        break;
                    }
                }
                MySignListBean.ListBean listBean = list.get(0);
                if (listBean.getEnumCheckInStatus() == 1) {
                    signOutInfo.setText("已签退\n" + listBean.getCreateTime());
                    isSignOut = true;
                } else {
                    signOutInfo.setText("未签退");
                }
            } else {
                signInInfo.setText("未签到");
                signOutInfo.setText("未签退");
            }
            //接口返回 false:当天已签到  true:当天未签到  所以用非
            radar.setSign(isSignIn);
            radar.setBackgroundResource(R.drawable.daka_btn_bule);
            boolean finalIsSignIn = isSignIn;
            String finalSignInTime = signInTime;
            radar.setOnItemSelectListener(indexString -> {
                if (AppUtils.isFastClick()) {
                    return;
                }
                //判断签退是否大于签到三小时
                try {
                    if (finalIsSignIn) {
                        if (StringUtil.isNotNullString(finalSignInTime)) {
                            long sMillis = TimeUtils.string2Millis(finalSignInTime);
                            long cMillis = TimeUtils.getNowMills();
                            if ((cMillis - sMillis) < 3 * 60 * 60 * 1000) {
                                ArmsUtils.makeText("工作时长不足3小时，请及时更新打卡");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mCurrentPt != null && latLngLists != null && latLngLists.size() > 0) {
                    for (List<LatLng> latLngs : latLngLists) {
                        if (SpatialRelationUtil.isPolygonContainsPoint(latLngs, mCurrentPt)) {
                            isPolygonContains = true;
                            break;
                        }
                    }
                }
                String hint = "";
                if (isPolygonContains) {
                    if (finalIsSignIn) {
                        hint = "确定签退";
                    } else {
                        hint = "确定签到";
                    }
                } else {
                    if (status.isInGrid()) {
                        if (finalIsSignIn) {
                            ArmsUtils.makeText("不允许网格外签退");
                        } else {
                            ArmsUtils.makeText("不允许网格外签到");
                        }
                        return;
                    } else {
                        if (finalIsSignIn) {
                            hint = "确定网格外签退";
                        } else {
                            hint = "确定网格外签到";
                        }
                    }
                }

                CommonDialog commonDialog = new CommonDialog.Builder(SignInActivity.this)
                        .setContent(hint)
                        .setNegativeBtnShow(!status.isInGrid())
                        .setPositiveButton(getString(R.string.sure), (view, dialog) -> {
                            if (!status.isInGrid() || isPolygonContains) {
                                TourVistLonAndLatBean lonAndLat = new TourVistLonAndLatBean();
                                lonAndLat.setLat(ll.latitude);
                                lonAndLat.setLng(ll.longitude);
                                String longAndLatJson = gson.toJson(lonAndLat);
                                assert mPresenter != null;
                                int signActive = 0;
                                if (!finalIsSignIn) {
                                    signActive = 0;
                                } else {
                                    signActive = 1;
                                }
                                mPresenter.setSign(longAndLatJson, addressName.getText().toString(), isPolygonContains, signActive);
                            }
                        }).create();
                commonDialog.show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化参照物  网格  和 标记点
     */
    @Override
    public void setReseau(GridInformationBean gridInformationBean) {
        try {
            mBaiduMap.clear();
            String pointsInJSON = gridInformationBean.getPointsInJSON();
            latLngLists = new ArrayList<>();
            ooPolygons = new ArrayList<>();
            List<StandardAddressStairBean.PointsInJsonBean.PolygoysBean> polygoysBeans = null;
            try {
                StandardAddressStairBean.PointsInJsonBean pointsInJsonBean =
                        gson.fromJson(pointsInJSON, StandardAddressStairBean.PointsInJsonBean.class);
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
                        StandardAddressStairBean.PointsInJsonBean.PolygoysBean polygoysBean =
                                new StandardAddressStairBean.PointsInJsonBean.PolygoysBean();
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
                                    .stroke(new Stroke(mStrokeWidth, Color.parseColor(polygoys.getStyleOptions().getStrokeColor().replace("#", "#40"))))
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
                MarkerOptions ooA = new MarkerOptions().position(mCurrentPt).icon(mbitmap).zIndex(9).draggable(false);
                mBaiduMap.addOverlay(ooA);
            } else {
                ArmsUtils.makeText(getString(R.string.init_gridding_fail));
            }
            mPresenter.getSignStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
     */
    public class MyLocationListener extends BDAbstractLocationListener {

        @SuppressLint("CheckResult")
        @Override
        public void onReceiveLocation(BDLocation location) {
            mCurrentPt = new LatLng(location.getLatitude(), location.getLongitude());

            if (isFirstPoint) {
                isFirstPoint = false;
                MapStatus.Builder builder1 = new MapStatus.Builder();
                builder1.target(mCurrentPt).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
            }
            if (location.getRadius() > 20) {
                //定位三次
                if (i == 3) {
                    setLocationDate(location);
                } else {
                    i++;
                }
            } else {
                i = 0;
                setLocationDate(location);
            }
        }
    }

    /**
     * @param location 定位  操作
     */
    private void setLocationDate(BDLocation location) {
        if (isFirstLoc) {
            isFirstLoc = false;
            mBaiduMap.clear();
            mLocationClient.unRegisterLocationListener(myListener);
            myListener = null;
            ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            if (StringUtil.isNotNullString(location.getAddrStr())) {
                addressName.setText(location.getAddrStr());
            }
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            mPresenter.GetDepartment();
            Observable.create((ObservableOnSubscribe<Long>) emitter -> {
                try {
                    Flowable.intervalRange(0, 2, 0, 1, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(aLong -> {
                                if (aLong == 0) {
                                    emitter.onNext(0L);
                                }
                            }).doOnComplete(() -> {

                    }).subscribe();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).subscribe(o -> {
                if (radar != null) {
                    radar.stop();
                }
            });
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
    protected void onDestroy() {
        // 取消注册的位置提醒监听
//        mLocationClient.removeNotifyEvent(mNotifyLister);
        // 停止定位
        try {
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
            if (radar != null) {
                radar.close();
                radar = null;
            }
            mLocationClient = null;
            myListener = null;
            mapVessel.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
