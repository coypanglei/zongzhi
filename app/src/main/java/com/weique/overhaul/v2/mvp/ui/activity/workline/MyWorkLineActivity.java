package com.weique.overhaul.v2.mvp.ui.activity.workline;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerMyWorkLineComponent;
import com.weique.overhaul.v2.mvp.contract.MyWorkLineContract;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.model.entity.WorkLineLatBean;
import com.weique.overhaul.v2.mvp.presenter.MyWorkLinePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/06/2020 16:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.APP_MYWORKLINEACTIVITY)
public class MyWorkLineActivity extends BaseActivity<MyWorkLinePresenter> implements MyWorkLineContract.View {

    @Inject
    Gson gson;

    ArrayList<WorkLineLatBean> list;

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
    @BindView(R.id.map_vessel)
    FrameLayout mapVessel;

    MapView mMapView;


    private BaiduMap mBaiduMap;
    private Marker mMoveMarker;
    private Handler mHandler;
    private Polyline mPolyline;

    private BitmapDescriptor mbitmap = BitmapDescriptorFactory.fromResource(R.drawable.start_icon);//起点
    private BitmapDescriptor mbitmap_zhongdian = BitmapDescriptorFactory.fromResource(R.drawable.zhongdian_icon);//起点

    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 30;
    private static final double DISTANCE = 0.000005;
    private BitmapDescriptor mGreenTexture = BitmapDescriptorFactory.fromAsset("Icon_road_blue_arrow.png");

    private BitmapDescriptor mBitmapCar = BitmapDescriptorFactory.fromResource(R.drawable.yidongdian);
    private List<LatLng> latLngs;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyWorkLineComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_work_line;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        ARouter.getInstance().inject(this);
        list = new ArrayList<>();
        assert mPresenter != null;
        mPresenter.getLatLng();
        setTitle("今日工作轨迹");
        initMap();

    }

    private void initMap() {
        mMapView = new MapView(this);
        mapVessel.addView(mMapView);
        mBaiduMap = mMapView.getMap();
        mHandler = new Handler(Looper.getMainLooper());
//        moveLooper();
        mMapView.showZoomControls(true);
    }


    private void drawPolyLine() {


        try {
            latLngs = new ArrayList<>();


            for (WorkLineLatBean workLineLatBean : list) {
                TourVistLonAndLatBean pointsInJsonBean = gson.fromJson(workLineLatBean.getPointsInJson(), TourVistLonAndLatBean.class);
                latLngs.add(new LatLng(pointsInJsonBean.getLat(), pointsInJsonBean.getLng()));
            }

            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(new LatLng(latLngs.get(0).latitude, latLngs.get(0).longitude));
            builder.zoom(19.0f);
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));


            OverlayOptions ooPolylineA = new PolylineOptions()
                    .width(10)// 设置折线线宽， 默认为 5， 单位：像素
                    .customTexture(mGreenTexture)
                    .points(latLngs)// 设置折线坐标点列表
                    .dottedLine(true);

            mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolylineA);


            MarkerOptions ooA1 = new MarkerOptions().position(latLngs.get(0)).icon(mbitmap);
            mBaiduMap.addOverlay(ooA1);

            MarkerOptions icon = new MarkerOptions().position(latLngs.get(latLngs.size() - 1)).icon(mbitmap_zhongdian);
            mBaiduMap.addOverlay(icon);

            // 添加小车marker
            OverlayOptions markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(mBitmapCar).
                    position(latLngs.get(0));
            //position(latLngs.get(0)).rotate((float) getAngle(0));
            mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);

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

    /**
     * 根据点获取图标转的角度
     */
    private double getAngle(int startIndex) {
        if ((startIndex + 1) >= mPolyline.getPoints().size()) {
            throw new RuntimeException("index out of bonds");
        }
        LatLng startPoint = mPolyline.getPoints().get(startIndex);
        LatLng endPoint = mPolyline.getPoints().get(startIndex + 1);
        return getAngle(startPoint, endPoint);
    }

    /**
     * 根据两点算取图标转的角度
     */
    private double getAngle(LatLng fromPoint, LatLng toPoint) {
        double slope = getSlope(fromPoint, toPoint);
        if (slope == Double.MAX_VALUE) {
            if (toPoint.latitude > fromPoint.latitude) {
                return 0;
            } else {
                return 180;
            }
        } else if (slope == 0.0) {
            if (toPoint.longitude > fromPoint.longitude) {
                return -90;
            } else {
                return 90;
            }
        }
        float deltAngle = 0;
        if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
            deltAngle = 180;
        }
        double radio = Math.atan(slope);
        double angle = 180 * (radio / Math.PI) + deltAngle - 90;
        return angle;
    }

    /**
     * 根据点和斜率算取截距
     */
    private double getInterception(double slope, LatLng point) {
        double interception = point.latitude - slope * point.longitude;
        return interception;
    }

    /**
     * 算斜率
     */
    private double getSlope(LatLng fromPoint, LatLng toPoint) {
        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }
        double slope = ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));
        return slope;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        try {
            mBitmapCar.recycle();
            mGreenTexture.recycle();
            mBaiduMap.clear();

            //在activity执行onDestroy时必须调用mMapView.onDestroy()
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

    /**
     * 计算x方向每次移动的距离
     */
    private double getXMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE || slope == 0.0) {
            return DISTANCE;
        }
        return Math.abs((DISTANCE * 1 / slope) / Math.sqrt(1 + 1 / (slope * slope)));
    }

    /**
     * 计算y方向每次移动的距离
     */
    private double getYMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE || slope == 0.0) {
            return DISTANCE;
        }
        return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
    }

    /**
     * 循环进行移动逻辑
     */
    public void moveLooper() {
        new Thread() {
            @Override
            public void run() {
                try {
                    if (latLngs == null || latLngs.size() <= 0) {
                        return;
                    }
                    while (true) {
                        for (int i = 0; i < latLngs.size() - 1; i++) {
                            final LatLng startPoint = latLngs.get(i);
                            final LatLng endPoint = latLngs.get(i + 1);
                            mMoveMarker.setPosition(startPoint);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // refresh marker's rotate
                                    if (mMapView == null) {
                                        return;
                                    }
                                    //                                mMoveMarker.setRotate((float) getAngle(startPoint, endPoint));
                                }
                            });
                            double slope = getSlope(startPoint, endPoint);
                            // 是不是正向的标示
                            boolean isYReverse = (startPoint.latitude > endPoint.latitude);
                            boolean isXReverse = (startPoint.longitude > endPoint.longitude);
                            double intercept = getInterception(slope, startPoint);
                            double xMoveDistance = isXReverse ? getXMoveDistance(slope) : -1 * getXMoveDistance(slope);
                            double yMoveDistance = isYReverse ? getYMoveDistance(slope) : -1 * getYMoveDistance(slope);

                            for (double j = startPoint.latitude, k = startPoint.longitude;
                                 !((j > endPoint.latitude) ^ isYReverse) && !((k > endPoint.longitude) ^ isXReverse); ) {
                                LatLng latLng = null;

                                if (slope == Double.MAX_VALUE) {
                                    latLng = new LatLng(j, k);
                                    j = j - yMoveDistance;
                                } else if (slope == 0.0) {
                                    latLng = new LatLng(j, k - xMoveDistance);
                                    k = k - xMoveDistance;
                                } else {
                                    latLng = new LatLng(j, (j - intercept) / slope);
                                    j = j - yMoveDistance;
                                }

                                final LatLng finalLatLng = latLng;
                                if (finalLatLng.latitude == 0 && finalLatLng.longitude == 0) {
                                    continue;
                                }
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mMapView == null) {
                                            return;
                                        }
                                        mMoveMarker.setPosition(finalLatLng);
                                        // 设置 Marker 覆盖物的位置坐标,并同步更新与Marker关联的InfoWindow的位置坐标.
                                        //                                    mMoveMarker.setPositionWithInfoWindow(finalLatLng);

                                        //                                    MarkerOptions ooA1 = new MarkerOptions().position(latLngs.get(0)).icon(mbitmap);
                                        //                                    mBaiduMap.addOverlay(ooA1);
                                    }
                                });
                                try {
                                    Thread.sleep(TIME_INTERVAL);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }

    @Override
    public void setLatLng(List<WorkLineLatBean> workLineLatBean) {
        list = new ArrayList<>();
        list.addAll(workLineLatBean);

        if (list.size() > 1) {
            drawPolyLine();
            moveLooper();
        } else {
            ArmsUtils.makeText("暂无工作轨迹！");
        }


    }
}
