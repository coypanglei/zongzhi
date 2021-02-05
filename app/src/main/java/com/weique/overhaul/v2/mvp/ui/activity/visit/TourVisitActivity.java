package com.weique.overhaul.v2.mvp.ui.activity.visit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.SelectDialog;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideImageLoaderPic;
import com.weique.overhaul.v2.app.utils.ImagePathUtill;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerTourVisitComponent;
import com.weique.overhaul.v2.mvp.contract.TourVisitContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.PatrolsDetailItemBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchDetailItemBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.TaskListBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.model.entity.UpDateTourVistBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.TourVisitPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.ImagePickerAdapter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 走访
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_TOURVISITACTIVITY)
public class TourVisitActivity extends BaseActivity<TourVisitPresenter> implements TourVisitContract.View, ImagePickerAdapter.OnRecyclerViewItemClickListener {
    @Inject
    Gson gson;

    /**
     * 走访标识
     */
    public static final String TYPE = "TourVisit";
    /**
     * 走访
     */
    public static final int TYPE_GO_AROUND = 1;
    /**
     * 巡检
     */
    public static final int TYPE_IPQC = 2;
    public static final String LNG = "lng";
    public static final String LAT = "lat";
    public static final String REMIND = "remind";
    public static final String RESOURCE = "resource";
    public static final String INFO = "geren";

    @Autowired(name = TYPE)
    String type;
    @Autowired(name = REMIND)
    String remind;
    @Autowired(name = RESOURCE)
    String resource;
    @Autowired(name = LNG)
    double lng;
    @Autowired(name = LAT)
    double lat;
    @Autowired(name = "list")
    ArrayList<String> list;
    @Autowired(name = "model")
    ResourceSearchDetailItemBean.ListBean model;

    @Autowired(name = "ElementListBean")
    InformationTypeOneSecondBean.ElementListBean listBean;


    /**
     * 待办任务穿过来的
     */
    @Autowired(name = "TASK_MODEL")
    PatrolsDetailItemBean.ElementsBean elementsBean;

    /**
     * 待办任务穿过来的
     */
    @Autowired(name = "status")
    boolean isWork;
    /**
     * 待办任务穿过来的
     */
    @Autowired(name = ARouerConstant.TITLE)
    String title;

    /**
     * 个人中心任务列表
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    @Autowired(name = "MissionConditionsBean")
    TaskListBean.ListBean.MissionConditionsBean missionConditionsBean;


    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.map_vessel)
    FrameLayout mapVessel;
    @BindView(R.id.arrow_icon)
    ImageView arrowIcon;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    MapView map;
    @BindView(R.id.icon_location)
    LinearLayout iconLocation;
    @BindView(R.id.layout)
    ConstraintLayout layout;
    @BindView(R.id.select_resource)
    TextView selectResource;
    @BindView(R.id.remind)
    EditText etRemind;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList;
    private int maxImgCount = 4;


    private BitmapDescriptor mbitmap = BitmapDescriptorFactory.fromResource(R.drawable.dingwei2_icon);
    public LocationClient mLocationClient;
    public BDAbstractLocationListener myListener = new MyLocationListener();
    private LatLng latLng;
    private boolean isFirstLoc = true;
    private BaiduMap mBaiduMap;
    private String ElementId;
    /**
     * 放权等级
     */
    private int levelOfDecentralization = StandardAddressStairBean.STREET;

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时必须调用mMapView. onPause ()
        map.onPause();
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        try {
            if (mLocationClient != null) {
                if (myListener != null) {
                    mLocationClient.unRegisterLocationListener(myListener);
                }
                mLocationClient.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
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
            mapVessel.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }

    @Subscriber(tag = RouterHub.APP_TOURVISITACTIVITY)
    private void updateList(EventBusBean eventBusBean) {
        listBean = (InformationTypeOneSecondBean.ElementListBean) eventBusBean.getData();
        selectResource.setText(listBean.getName());


    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTourVisitComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_map1;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            if (!AppUtils.isLocationEnabled(this)) {
                ArmsUtils.makeText("GPS关闭,会使您的定位不准确");
            }
            ARouter.getInstance().inject(this);
            if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= levelOfDecentralization) {
                rightBtnText.setVisibility(View.VISIBLE);
                rightBtnText.setText("提交");
                rightBtnText.setTextColor(getResources().getColor(R.color.colorPrimary));
                rightBtnText.setTextSize(17);
                etRemind.setEnabled(true);
            } else {
                rightBtnText.setVisibility(View.GONE);
                etRemind.setEnabled(false);
            }
            initImagePicker();
            initWidget();
            if ((RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_TASKLISTHOMEFRAGMENT.equals(source)
                    || RouterHub.APP_TASKLISTACTIVITY.equals(source))
                    && missionConditionsBean != null) {
                selectResource.setClickable(false);
                selectResource.setText(listBean.getName());
                arrowIcon.setVisibility(View.GONE);
            } else if (type.equals(INFO)) {
                selectResource.setClickable(false);
                arrowIcon.setVisibility(View.GONE);
                etRemind.setText(remind);
                selectResource.setText(resource);
                latLng = new LatLng(lat, lng);

                for (int i = 0; i < list.size(); i++) {
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = list.get(i);
                    selImageList.add(imageItem);
                }
                adapter.setImages(selImageList);

            } else if ("NEW".equals(type)) {
                selectResource.setClickable(false);
                arrowIcon.setVisibility(View.GONE);
                selectResource.setText(resource);

            } else if ("TASK".equals(type)) {
                selectResource.setClickable(false);
                arrowIcon.setVisibility(View.GONE);
                selectResource.setText(elementsBean.getName());

                if (lat != 0) {
                    etRemind.setText(remind);
                    etRemind.setFocusableInTouchMode(false);//不可编辑
                    etRemind.setKeyListener(null);//不可粘贴，长按不会弹出粘贴框
                    etRemind.setClickable(false);//不可点击，但是这个效果我这边没体现出来，不知道怎没用
                    etRemind.setFocusable(false);//不可编辑
                    latLng = new LatLng(lat, lng);

                    for (int i = 0; i < list.size(); i++) {
                        ImageItem imageItem = new ImageItem();
                        imageItem.path = list.get(i);
                        selImageList.add(imageItem);
                    }
                    adapter.setImages(selImageList);
                }

                if (isWork) {
                    rightBtn.setVisibility(View.GONE);
                }

            } else {
                selectResource.setClickable(true);
                arrowIcon.setVisibility(View.VISIBLE);
            }

            initMap();
            setTitle(title);
            //地图滑动冲突
            map.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        //允许ScrollView截断点击事件，ScrollView可滑动
                        scrollView.requestDisallowInterceptTouchEvent(false);
                    } else {
                        //不允许ScrollView截断点击事件，点击事件由子View处理
                        scrollView.requestDisallowInterceptTouchEvent(true);
                    }
                    return false;
                }
            });
            etRemind.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                    if (s.toString().trim().length() >= 150) {
                        ArmsUtils.makeText("您最多能输入150个字！");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.icon_location)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(latLng);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initMap() {
        //获取地图控件引用
        try {
            map = new MapView(this);
            mapVessel.addView(map);
            mBaiduMap = map.getMap();
            //普通地图
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            mBaiduMap.setMyLocationEnabled(true);

            //默认显示普通地图
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            //开启交通图
            //mBaiduMap.setTrafficEnabled(true);
            //开启热力图
            //mBaiduMap.setBaiduHeatMapEnabled(true);
            // 开启定位图层
            mBaiduMap.setMyLocationEnabled(true);
            mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
            //配置定位SDK参数
            initLocation();
            mLocationClient.registerLocationListener(myListener);    //注册监听函数
            //开启定位
            mLocationClient.start();
            //图片点击事件，回到定位点
            mLocationClient.requestLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //配置定位SDK参数
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
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


    @OnClick({R.id.right_btn, R.id.select_resource})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.right_btn:
                    UpDateTourVistBean upDateTourVistBean = new UpDateTourVistBean();
                    if (type.equals(INFO)) {
                        upDateTourVistBean.setId(model.getId());
                        upDateTourVistBean.setResourceId(model.getcId());
                        upDateTourVistBean.setResourceName(resource);
                        upDateTourVistBean.setElementTypeId(model.getElementTypeId());
                        upDateTourVistBean.setPointInJson(model.getPointInJson());

                        List<String> imgPath = new ArrayList<>();
                        for (ImageItem imageItem : selImageList) {
                            imgPath.add(imageItem.path);
                        }
                        String ImageJson = gson.toJson(imgPath);
                        upDateTourVistBean.setIVImageUrlsInJson(ImageJson);
                        upDateTourVistBean.setDepartmentId(model.getDepartmentId());
                        upDateTourVistBean.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                        upDateTourVistBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());
                        upDateTourVistBean.setMemo(etRemind.getText().toString());

                        assert mPresenter != null;
                        mPresenter.setTourVisitData(false, false, upDateTourVistBean);
                    } else if (type.equals("NEW")) {
                        if (TextUtils.isEmpty(etRemind.getText().toString())) {
                            ArmsUtils.makeText(getResources().getString(R.string.input_interview_d));
                        } else {
                            upDateTourVistBean.setResourceId(model.getcId());
                            upDateTourVistBean.setResourceName(resource);
                            upDateTourVistBean.setElementTypeId(model.getElementTypeId());
                            upDateTourVistBean.setPointInJson(model.getPointInJson());

                            List<String> imgPath = new ArrayList<>();

                            for (ImageItem imageItem : selImageList) {
                                imgPath.add(imageItem.path);
                            }

                            String ImageJson = gson.toJson(imgPath);
                            upDateTourVistBean.setIVImageUrlsInJson(ImageJson);

                            upDateTourVistBean.setDepartmentId(model.getDepartmentId());
                            upDateTourVistBean.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                            upDateTourVistBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());
                            upDateTourVistBean.setMemo(etRemind.getText().toString());

                            assert mPresenter != null;
                            mPresenter.setTourVisitData(false, false, upDateTourVistBean);
                        }

                    } else if ("TASK".equals(type)) {
                        if (TextUtils.isEmpty(etRemind.getText().toString())) {
                            ArmsUtils.makeText(getResources().getString(R.string.input_interview_d));
                        } else {

                            upDateTourVistBean.setResourceId(elementsBean.getId());
                            upDateTourVistBean.setResourceName(elementsBean.getName());
                            upDateTourVistBean.setElementTypeId(elementsBean.getElementTypeId());

                            TourVistLonAndLatBean lonAndLat = new TourVistLonAndLatBean();
                            lonAndLat.setLat(latLng.latitude);
                            lonAndLat.setLng(latLng.longitude);
                            String longAndLatJson = gson.toJson(lonAndLat);
                            upDateTourVistBean.setPointInJson(longAndLatJson);

                            List<String> imgPath = new ArrayList<>();

                            for (ImageItem imageItem : selImageList) {
                                imgPath.add(imageItem.path);
                            }
                            String ImageJson = gson.toJson(imgPath);
                            upDateTourVistBean.setIVImageUrlsInJson(ImageJson);

                            upDateTourVistBean.setDepartmentId(elementsBean.getDepartmentId());
                            upDateTourVistBean.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                            upDateTourVistBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());
                            upDateTourVistBean.setMemo(etRemind.getText().toString());
                            upDateTourVistBean.setTaskId(elementsBean.getTaskId());

                            assert mPresenter != null;
                            mPresenter.setTourVisitData(false, false, upDateTourVistBean);
                        }
                    } else {

                        if (latLng == null) {
                            ArmsUtils.makeText("定位错误");
                        } else if (TextUtils.isEmpty(etRemind.getText().toString())) {
                            ArmsUtils.makeText(getResources().getString(R.string.input_interview_d));
                        } else if (listBean == null) {
                            ArmsUtils.makeText(getResources().getString(R.string.input_select_resource));
                        } else {

                            upDateTourVistBean.setResourceId(listBean.getId());
                            upDateTourVistBean.setResourceName(listBean.getName());
                            upDateTourVistBean.setElementTypeId(listBean.getElementTypeId());

                            TourVistLonAndLatBean lonAndLat = new TourVistLonAndLatBean();
                            lonAndLat.setLat(latLng.latitude);
                            lonAndLat.setLng(latLng.longitude);
                            String longAndLatJson = gson.toJson(lonAndLat);
                            upDateTourVistBean.setPointInJson(longAndLatJson);

                            List<String> imgPath = new ArrayList<>();

                            for (ImageItem imageItem : selImageList) {
                                imgPath.add(imageItem.path);
                            }
                            String ImageJson = gson.toJson(imgPath);
                            upDateTourVistBean.setIVImageUrlsInJson(ImageJson);

                            upDateTourVistBean.setDepartmentId(listBean.getDepartmentId());
                            upDateTourVistBean.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                            upDateTourVistBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());
                            upDateTourVistBean.setMemo(etRemind.getText().toString());

                            assert mPresenter != null;
                            mPresenter.setTourVisitData(false, false, upDateTourVistBean);
                        }

                    }
                    break;
                case R.id.select_resource:
                    if (StringUtil.isNotNullString(source)) {
                        ARouter.getInstance().build(RouterHub.APP_INFORMATIONTYPESECONDACTIVITY)
                                .withString(ARouerConstant.ID, missionConditionsBean.getElementTypeId())
                                .withString(ARouerConstant.TITLE, missionConditionsBean.getName())
                                .navigation();
                    } else {
                        ARouter.getInstance().build(RouterHub.APP_INFORMATIONCOLLECTIONACTIVITY)
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_TOURVISITACTIVITY)
                                .withInt(TourVisitActivity.TYPE, TourVisitActivity.TYPE_GO_AROUND)
                                .navigation();
                    }

                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTourVisitData(String itemBean) {
        EventBus.getDefault().post(new EventBusBean(EventBusConstant.IS_REFRESH), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_PATROLSFRAGMENT);
        finish();
    }

    @Override
    public void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans) {

        try {
            for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                ImageItem imageItem = new ImageItem();
                imageItem.path = uploadFileRsponseBean.getUrl();
                selImageList.add(imageItem);
            }
            adapter.setImages(selImageList);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Activity getActivity() {
        return this;
    }


    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (type.equals(INFO)) {
                latLng = new LatLng(lat, lng);
            } else {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
            }

            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(latLng.latitude)
                    .longitude(latLng.longitude).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            // 当不需要定位图层时关闭定位图层
            //mBaiduMap.setMyLocationEnabled(false);
            if (isFirstLoc) {
                isFirstLoc = false;
                if (type.equals(INFO)) {
                    latLng = new LatLng(lat, lng);
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(latLng).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                } else {
                    LatLng ll = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }


            }
        }
    }


    /**
     * 拍照
     */
    private void initImagePicker() {
        try {
            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoaderPic());   //设置图片加载器
            imagePicker.setShowCamera(true);                      //显示拍照按钮
            imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
            imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
            imagePicker.setSelectLimit(4);              //选中数量限制
            imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
            imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
            imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
            imagePicker.setOutPutX(400);                         //保存文件的宽度。单位像素
            imagePicker.setOutPutY(340);                         //保存文件的高度。单位像素
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        try {
            SelectDialog dialog = new SelectDialog(this, R.style
                    .transparentFrameWindowStyle,
                    listener, names);
            if (!isFinishing()) {
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initWidget() {
        try {
            selImageList = new ArrayList<>();
            adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
            adapter.setOnItemClickListener(this);
            ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(this, 4));
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    ArrayList<ImageItem> images = null;


    @Override
    public void onItemClick(View view, int position) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (position) {
                case IMAGE_ITEM_ADD:
                    if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= levelOfDecentralization) {
                        if (!isWork) {
                            if (TextUtils.isEmpty(selectResource.getText().toString())) {
                                ArmsUtils.makeText(getResources().getString(R.string.input_select_resource));
                            } else {
                                if (type.equals(INFO)) {
                                    ElementId = model.getId();
                                } else if ("NEW".equals(type)) {
                                    ElementId = model.getId();
                                } else if ("TASK".equals(type)) {
                                    ElementId = elementsBean.getInspectionRecordId();
                                } else {
                                    ElementId = listBean.getId();
                                }

                                List<String> names = new ArrayList<>();
                                names.add("拍照");
                                mPresenter.getPermission(maxImgCount, selImageList, names);
                            }
                        }

                    } else {

                    }


                    break;
                default:
                    //打开预览
                    Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                    intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                    intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                    intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                    startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                //添加图片返回
                if (data != null && requestCode == REQUEST_CODE_SELECT) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    List<String> list = new ArrayList<>();
                    String s = ImagePathUtill.compressImage(images.get(0).path);
                    list.add(s);
                    assert mPresenter != null;
                    mPresenter.upLoadFile(ElementId, list);
                }
            } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
                //预览图片返回
                if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                    if (images != null) {
                        selImageList.clear();
                        selImageList.addAll(images);
                        adapter.setImages(selImageList);
                    }
                }
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


    public static LatLng getCenterOfGravityPoint(List<LatLng> mPoints) {
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
