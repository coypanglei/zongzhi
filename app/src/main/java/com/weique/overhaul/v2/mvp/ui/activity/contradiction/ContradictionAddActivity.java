package com.weique.overhaul.v2.mvp.ui.activity.contradiction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.PictureSelectorUtils;
import com.weique.overhaul.v2.app.utils.RegexUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerContradictionAddComponent;
import com.weique.overhaul.v2.mvp.contract.ContradictionAddContract;
import com.weique.overhaul.v2.mvp.model.entity.AdminGridBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionAddBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionAddPeopleBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionAddSearchItemBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionRecordBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.presenter.ContradictionAddPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationAddPhotoAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.SimplePhotoAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity.getCenterOfGravityPoint;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_CONTRADICTIONADDACTIVITY)
public class ContradictionAddActivity extends BaseActivity<ContradictionAddPresenter> implements ContradictionAddContract.View {
    @Inject
    Gson gson;
    /**
     * 多边形信息
     */
    public static final String POINTS_IN_JSON = "pointsInJSON";
    @Autowired(name = POINTS_IN_JSON)
    String pointsInJSON;

    /**
     * 矛盾调解记录Id
     */
    @Autowired(name = "ID")
    String id;
    /**
     * 矛盾调解记录状态
     */
    @Autowired(name = "STATUS")
    int recordStatus;

    @BindView(R.id.delete_layout)
    LinearLayout deleteLayout;
    @BindView(R.id.map_vessel)
    FrameLayout mapVessel;
    MapView map;
    @BindView(R.id.icon_location)
    LinearLayout iconLocation;
    @BindView(R.id.layout)
    ConstraintLayout layout;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.ed_minzu)
    EditText edMinzu;
    @BindView(R.id.ed_job)
    EditText edJob;
    @BindView(R.id.ed_unit)
    EditText edUnit;
    @BindView(R.id.ed_name1)
    EditText edName1;
    @BindView(R.id.tv_sex1)
    TextView tvSex1;
    @BindView(R.id.tv_birth1)
    TextView tvBirth1;
    @BindView(R.id.ed_minzu1)
    EditText edMinzu1;
    @BindView(R.id.ed_job1)
    EditText edJob1;
    @BindView(R.id.ed_unit1)
    EditText edUnit1;
    @BindView(R.id.ed_address)
    EditText edAddress;
    @BindView(R.id.tv_do_type)
    TextView tvDoType;
    @BindView(R.id.ed_memo)
    EditText edMemo;
    @BindView(R.id.staging)
    Button staging;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.invalid)
    Button invalid;
    @BindView(R.id.refuse_reason)
    TextView refuseReason;
    @BindView(R.id.layout_refuse_reason)
    LinearLayout layoutRefuseReason;
    @BindView(R.id.self_handle)
    ImageView selfHandle;
    @BindView(R.id.ed_cid)
    EditText edCid;
    @BindView(R.id.ed_cid1)
    EditText edCid1;
    @BindView(R.id.ed_tel)
    EditText edTel;
    @BindView(R.id.ed_tel1)
    EditText edTel1;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private int isSelfHandle = 0;

    public LocationClient mLocationClient;
    private boolean isFirstLoc = true; // 是否首次定位
    private boolean isFirstPoint = true; // 是否首次定位点
    private BaiduMap mBaiduMap;
    private BitmapDescriptor mbitmap = BitmapDescriptorFactory.fromResource(R.drawable.dingwei2_icon);


    private LatLng mDefaultPt;


    private List<List<LatLng>> latLngLists;
    private int mStrokeWidth = 5;
    private List<PolygonOptions> ooPolygons;
    List<ContradictionRecordBean.CAPartyPersonBean> zancunPeople;
    /**
     * 性别
     */
    OptionsPickerView pvOptions;
    private ArrayList<String> optionSexList;
    private int whichOption = -1;
    private String strSex;
    private String strSex1;

    /**
     * 出生日期
     */
    private int whichTimeOption = -1;
    private String strBirth;
    private String strBirth1;


    /**
     * 化解方式
     */
    OptionsPickerView pvOptionsResolve;
    private ArrayList<String> optionResolveList;
    private int strpvResolve = -1;
    private String selectAddressId;//标准地址id
    private String gisLocations;//gisLocations

    private String resetId = "";//修改条目的Id
    private TimePickerView pvTime;


    /**
     * 照片模块
     */
    List<InformationItemPictureBean> list;
    private InformationAddPhotoAdapter informationAddPhotoAdapter;
    private SimplePhotoAdapter simplePhotoAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerContradictionAddComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_contradiction_add;
    }

    @Subscriber(tag = RouterHub.APP_CONTRADICTIONADDACTIVITY)
    private void update(ContradictionAddSearchItemBean.DepartmentBean s) {
        try {
            tvSelectAddress.setText(s.getFullPath());
            edAddress.setText(s.getFullPath());
            selectAddressId = s.getId();
            initCoord();
            if (StringUtil.isNotNullString(s.getGisLocations())) {
                gisLocations = s.getGisLocations();
                StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                        gson.fromJson(s.getGisLocations(), StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
                LatLng latLng = new LatLng(pathBean.getLat(), pathBean.getLng());
                MarkerOptions ooA1 = new MarkerOptions().position(latLng).icon(mbitmap);
                mBaiduMap.addOverlay(ooA1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            if (!AppUtils.isLocationEnabled(this)) {
                ArmsUtils.makeText("GPS关闭,会使您的定位不准确");
            }
            ImmersionBar.with(this).statusBarColor(R.color.white).init();
            ARouter.getInstance().inject(this);

            setTitle("矛盾调解");
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
            //initLocation();
            initCoord();
            // deleteKeyBoard();
            optionSexList = new ArrayList<>();
            optionResolveList = new ArrayList<>();

            optionSexList.add("不确定");
            optionSexList.add("男");
            optionSexList.add("女");

            optionResolveList.add("不愿化解");
            optionResolveList.add("自愿化解");
            optionResolveList.add("人民调解");
            optionResolveList.add("行政调解");
            optionResolveList.add("律师调解");
            optionResolveList.add("行政复议");
            optionResolveList.add("行政裁决");
            optionResolveList.add("仲裁");
            optionResolveList.add("公证");
            optionResolveList.add("民主协商");


            initOptionsPicker();
            initResolveOptionsPicker();
            initTimePicker();
            //地图滑动冲突
            map.getChildAt(0).setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //允许ScrollView截断点击事件，ScrollView可滑动
                    scrollView.requestDisallowInterceptTouchEvent(false);
                } else {
                    //不允许ScrollView截断点击事件，点击事件由子View处理
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            });

            initAdd();

            initPhotoadapter();
            list.add(0, new InformationItemPictureBean(R.drawable.picture));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdd() {
        try {
            if (!TextUtils.isEmpty(id)) {
                assert mPresenter != null;
                mPresenter.getContradictionRecord(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initOptionsPicker() {
        try {
            pvOptions = PickerViewUtil.selectPickerSelecter(this, (options1, options2, options3, v) -> {
                try {
                    if (whichOption == 0) {
                        tvSex.setText(optionSexList.get(options1));
                        strSex = options1 + "";
                    } else if (whichOption == 1) {
                        tvSex1.setText(optionSexList.get(options1));
                        strSex1 = options1 + "";
                    }

                    tvSex.setTextColor(getResources().getColor(R.color.black_333));
                    tvSex1.setTextColor(getResources().getColor(R.color.black_333));
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }, (options1, options2, options3) -> {

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initResolveOptionsPicker() {
        try {
            try {
                pvOptionsResolve = PickerViewUtil.selectPickerSelecter(this, (options1, options2, options3, v) -> {
                    try {
                        tvDoType.setText(optionResolveList.get(options1));
                        tvDoType.setTextColor(getResources().getColor(R.color.black_333));
                        strpvResolve = options1;
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }, (options1, options2, options3) -> {

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initTimePicker() {

        /*
         * 隐患录入日期
         */
        try {
            Calendar selectedDate = Calendar.getInstance();
            pvTime = PickerViewUtil.selectPickerTimeBirth(this, Constant.YMD, (date, v) -> {
                try {
                    SimpleDateFormat format = new SimpleDateFormat(Constant.YMD, Locale.CHINA);
                    if (whichTimeOption == 0) {
                        tvBirth.setText(format.format(date));
                        strBirth = format.format(date);

                    } else if (whichTimeOption == 1) {
                        tvBirth1.setText(format.format(date));
                        strBirth1 = format.format(date);
                    }
                    tvBirth.setTextColor(getResources().getColor(R.color.black_333));
                    tvBirth1.setTextColor(getResources().getColor(R.color.black_333));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 上传照片
     */
    private void initPhotoadapter() {
        list = new ArrayList<>();
        informationAddPhotoAdapter = new InformationAddPhotoAdapter(list);
        ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(informationAddPhotoAdapter);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .colorResId(R.color.transparent)
                .sizeResId(R.dimen.dp_10)
                .build());
        informationAddPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            try {
                if (AppUtils.isFastClick()) {
                    return;
                }
                InformationItemPictureBean bean = (InformationItemPictureBean) adapter.getItem(position);
                if (view.getId() == R.id.image_) {
                    if (bean.getType() == InformationItemPictureBean.IS_DRAWABLE) {
                        if (adapter.getData().size() >= 8) {
                            ArmsUtils.makeText("最多上传8张图片");
                            return;
                        }
                        //9  是加上了默认的加号 图标
                        int max = 9 - adapter.getData().size();
                        mPresenter.getPermission(max);
                    } else if (bean.getType() == InformationItemPictureBean.IS_VIS_DELETE) {
                        try {
                            String item = ((InformationItemPictureBean) adapter.getItem(position)).getImageUrl();
                            ARouter.getInstance()
                                    .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                    .withString(PictureLookActivity.URL_, item)
                                    .navigation();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (view.getId() == R.id.remove_image) {
                    adapter.remove(position);
                    adapter.notifyItemChanged(position);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
                LatLng latLng;
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
                if (latLngLists != null && latLngLists.size() > 0) {
                    animateMapStatus();
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
        try {
            if (mLocationClient == null) {
                mLocationClient = new LocationClient(getApplicationContext());
                //开启定位
                mLocationClient.start();
                //图片点击事件，回到定位点
                mLocationClient.requestLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.tv_sex, R.id.tv_birth, R.id.tv_sex1, R.id.tv_birth1, R.id.type_layout, R.id.tv_select_address, R.id.icon_location
            , R.id.staging, R.id.submit, R.id.delete_layout, R.id.invalid, R.id.self_handle})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.self_handle:
                    if (isSelfHandle == 0) {
                        isSelfHandle = 1;
                        selfHandle.setImageResource(R.drawable.s_open);
                    } else {
                        isSelfHandle = 0;
                        selfHandle.setImageResource(R.drawable.s_close);
                    }
                    break;
                case R.id.tv_sex:
                    KeybordUtil.hideKeyboard(ContradictionAddActivity.this);
                    whichOption = 0;
                    pvOptions.setPicker(optionSexList);
                    pvOptions.show();
                    break;
                case R.id.tv_birth:
                    KeybordUtil.hideKeyboard(ContradictionAddActivity.this);
                    whichTimeOption = 0;
                    pvTime.show();
                    break;
                case R.id.tv_sex1:
                    KeybordUtil.hideKeyboard(ContradictionAddActivity.this);
                    whichOption = 1;
                    pvOptions.setPicker(optionSexList);
                    pvOptions.show();
                    break;
                case R.id.tv_birth1:
                    KeybordUtil.hideKeyboard(ContradictionAddActivity.this);
                    whichTimeOption = 1;
                    pvTime.show();
                    break;
                case R.id.type_layout:
                    KeybordUtil.hideKeyboard(ContradictionAddActivity.this);
                    pvOptionsResolve.setPicker(optionResolveList);
                    pvOptionsResolve.show();
                    break;

                case R.id.tv_select_address:
                    ARouter.getInstance().build(RouterHub.APP_CONTRADICTIONADDSEARCHACTIVITY)
                            .navigation();

                    break;
                case R.id.icon_location:
                    if (latLngLists != null && latLngLists.size() > 0) {
                        animateMapStatus();
                    }
                    break;
                case R.id.staging:
                    new CommonDialog.Builder(ContradictionAddActivity.this)
                            .setContent("确定暂存吗？")
                            .setPositiveButton(getString(R.string.sure), (v, dialog) -> {
                                if (!TextUtils.isEmpty(resetId)) {//修改
                                    ContradictionAddBean stagingBean = new ContradictionAddBean();
                                    stagingBean.setId(resetId);
                                    stagingBean.setUserId(UserInfoUtil.getUserInfo().getUid());
                                    stagingBean.setStandardAddressId(selectAddressId);
                                    stagingBean.setGisLocation(gisLocations);
                                    stagingBean.setEnumResolveType(strpvResolve);
                                    stagingBean.setEnumCAEventOrderSaveStatus(0);
                                    stagingBean.setAddress(edAddress.getText().toString().trim());
                                    stagingBean.setMemo(edMemo.getText().toString().trim());
                                    stagingBean.setEnumCAEventOrderType(isSelfHandle);
                                    stagingBean.setCAPartyPerson(gson.toJson(zancunPeople));
                                    stagingBean.setPicPath(informationAddPhotoAdapter.getPicPathsJson());
                                    assert mPresenter != null;
                                    mPresenter.setResetContradiction(stagingBean);
                                } else {
                                    if (TextUtils.isEmpty(selectAddressId)) {
                                        ArmsUtils.makeText("请选择标准地址");
                                    } else if (TextUtils.isEmpty(edName.getText().toString().trim()) || TextUtils.isEmpty(edName1.getText().toString().trim())) {
                                        ArmsUtils.makeText("请填写当事人姓名");
                                    } else if (TextUtils.isEmpty(strSex) || TextUtils.isEmpty(strSex1)) {
                                        ArmsUtils.makeText("请选择性别");
                                    } else if (TextUtils.isEmpty(strBirth) || TextUtils.isEmpty(strBirth1)) {
                                        ArmsUtils.makeText("请选择出生日期");
                                    } else if (TextUtils.isEmpty(edMinzu.getText().toString().trim()) || TextUtils.isEmpty(edMinzu1.getText().toString().trim())) {
                                        ArmsUtils.makeText("请填写民族");
                                    } else if (TextUtils.isEmpty(edAddress.getText().toString().trim())) {
                                        ArmsUtils.makeText("请填写事发地址");
                                    } else if (strpvResolve == -1) {
                                        ArmsUtils.makeText("请选择化解方式");
                                    } else if (TextUtils.isEmpty(edMemo.getText().toString().trim())) {
                                        ArmsUtils.makeText("请填写描述");
                                    } else {
                                        //身份证号
                                        String cid = edCid.getText().toString().trim();
                                        if (!TextUtils.isEmpty(cid) && !RegexUtils.isIDCard18Exact(cid)) {
                                            ArmsUtils.makeText("身份证号码不正确");
                                            return;
                                        }

                                        String cid1 = edCid1.getText().toString().trim();
                                        if (!TextUtils.isEmpty(cid1) && !RegexUtils.isIDCard18Exact(cid1)) {
                                            ArmsUtils.makeText("身份证号码不正确");
                                            return;
                                        }
                                        List<ContradictionAddPeopleBean> peopleBeans = new ArrayList<>();
                                        ContradictionAddPeopleBean contradictionAddPeopleBean = new ContradictionAddPeopleBean();
                                        contradictionAddPeopleBean.setName(edName.getText().toString().trim());
                                        contradictionAddPeopleBean.setEnumGender(strSex);
                                        contradictionAddPeopleBean.setBirthDate(strBirth);
                                        contradictionAddPeopleBean.setNation(edMinzu.getText().toString().trim());
                                        contradictionAddPeopleBean.setJob(edJob.getText().toString().trim());
                                        contradictionAddPeopleBean.setJobUnit(edUnit.getText().toString().trim());
                                        contradictionAddPeopleBean.setCID(cid);
                                        contradictionAddPeopleBean.setCIndex(0);
                                        contradictionAddPeopleBean.setTel(edTel.getText().toString());

                                        ContradictionAddPeopleBean contradictionAddPeopleBean1 = new ContradictionAddPeopleBean();
                                        contradictionAddPeopleBean1.setName(edName1.getText().toString().trim());
                                        contradictionAddPeopleBean1.setEnumGender(strSex1);
                                        contradictionAddPeopleBean1.setBirthDate(strBirth1);
                                        contradictionAddPeopleBean1.setNation(edMinzu1.getText().toString().trim());
                                        contradictionAddPeopleBean1.setJob(edJob1.getText().toString().trim());
                                        contradictionAddPeopleBean1.setJobUnit(edUnit1.getText().toString().trim());
                                        contradictionAddPeopleBean1.setCID(cid1);
                                        contradictionAddPeopleBean1.setCIndex(1);
                                        contradictionAddPeopleBean1.setTel(edTel1.getText().toString());


                                        peopleBeans.add(contradictionAddPeopleBean);
                                        peopleBeans.add(contradictionAddPeopleBean1);
                                        String toJson = gson.toJson(peopleBeans);
                                        ContradictionAddBean contradictionAddBean = new ContradictionAddBean();
                                        contradictionAddBean.setUserId(UserInfoUtil.getUserInfo().getUid());
                                        contradictionAddBean.setStandardAddressId(selectAddressId);
                                        contradictionAddBean.setEnumResolveType(strpvResolve);
                                        contradictionAddBean.setEnumCAEventOrderSaveStatus(0);
                                        contradictionAddBean.setGisLocation(gisLocations);
                                        contradictionAddBean.setCAPartyPerson(toJson);
                                        contradictionAddBean.setAddress(edAddress.getText().toString().trim());
                                        contradictionAddBean.setMemo(edMemo.getText().toString().trim());
                                        contradictionAddBean.setEnumCAEventOrderType(isSelfHandle);
                                        assert mPresenter != null;
                                        mPresenter.setContradiction(contradictionAddBean);
                                    }
                                }
                            }).setNegativeButton(getString(R.string.ove), null).create().show();
                    break;
                case R.id.submit:
                    new CommonDialog.Builder(ContradictionAddActivity.this)
                            .setContent("确定提交吗？")
                            .setPositiveButton(getString(R.string.sure), (v, commonDialog) -> {
                                if (TextUtils.isEmpty(tvSelectAddress.getText().toString())) {
                                    ArmsUtils.makeText("请选择标准地址");
                                } else if (TextUtils.isEmpty(edName.getText().toString().trim()) || TextUtils.isEmpty(edName1.getText().toString().trim())) {
                                    ArmsUtils.makeText("请填写当事人姓名");
                                } else if (TextUtils.isEmpty(strSex) || TextUtils.isEmpty(strSex1)) {
                                    ArmsUtils.makeText("请选择性别");
                                } else if (TextUtils.isEmpty(strBirth) || TextUtils.isEmpty(strBirth1)) {
                                    ArmsUtils.makeText("请选择出生日期");
                                } else if (TextUtils.isEmpty(edMinzu.getText().toString().trim()) || TextUtils.isEmpty(edMinzu1.getText().toString().trim())) {
                                    ArmsUtils.makeText("请填写民族");
                                } else if (TextUtils.isEmpty(edCid.getText().toString().trim()) || TextUtils.isEmpty(edCid1.getText().toString().trim())) {
                                    ArmsUtils.makeText(getString(R.string.id_number_hint));
                                } else if (TextUtils.isEmpty(edTel.getText().toString().trim()) || TextUtils.isEmpty(edTel1.getText().toString().trim())) {
                                    ArmsUtils.makeText("请填写联系方式");
                                } else if (TextUtils.isEmpty(edAddress.getText().toString().trim())) {
                                    ArmsUtils.makeText("请填写事发地址");
                                } else if (strpvResolve == -1) {
                                    ArmsUtils.makeText("请选择化解方式");
                                } else if (TextUtils.isEmpty(edMemo.getText().toString().trim())) {
                                    ArmsUtils.makeText("请填写描述");
                                } else {
                                    //身份证号
                                    String cid = edCid.getText().toString().trim();
                                    if (!TextUtils.isEmpty(cid) && !RegexUtils.isIDCard18Exact(cid)) {
                                        ArmsUtils.makeText("身份证号码不正确");
                                        return;
                                    }

                                    String cid1 = edCid1.getText().toString().trim();
                                    if (!TextUtils.isEmpty(cid1) && !RegexUtils.isIDCard18Exact(cid1)) {
                                        ArmsUtils.makeText("身份证号码不正确");
                                        return;
                                    }

                                    String tel = edTel.getText().toString().trim();
                                    if (!TextUtils.isEmpty(tel) && !RegexUtils.isTel(tel)) {
                                        ArmsUtils.makeText("手机号不正确");
                                        return;
                                    }
                                    String tel1 = edTel1.getText().toString().trim();
                                    if (!TextUtils.isEmpty(tel1) && !RegexUtils.isTel(tel1)) {
                                        ArmsUtils.makeText("手机号不正确");
                                        return;
                                    }


                                    List<ContradictionAddPeopleBean> peopleBeans = new ArrayList<>();
                                    ContradictionAddPeopleBean contradictionAddPeopleBean = new ContradictionAddPeopleBean();
                                    contradictionAddPeopleBean.setName(edName.getText().toString().trim());
                                    contradictionAddPeopleBean.setEnumGender(strSex);
                                    contradictionAddPeopleBean.setBirthDate(strBirth);
                                    contradictionAddPeopleBean.setNation(edMinzu.getText().toString().trim());
                                    contradictionAddPeopleBean.setJob(edJob.getText().toString().trim());
                                    contradictionAddPeopleBean.setJobUnit(edUnit.getText().toString().trim());
                                    contradictionAddPeopleBean.setCID(cid);
                                    contradictionAddPeopleBean.setCIndex(0);
                                    contradictionAddPeopleBean.setTel(edTel.getText().toString());


                                    ContradictionAddPeopleBean contradictionAddPeopleBean1 = new ContradictionAddPeopleBean();
                                    contradictionAddPeopleBean1.setName(edName1.getText().toString().trim());
                                    contradictionAddPeopleBean1.setEnumGender(strSex1);
                                    contradictionAddPeopleBean1.setBirthDate(strBirth1);
                                    contradictionAddPeopleBean1.setNation(edMinzu1.getText().toString().trim());
                                    contradictionAddPeopleBean1.setJob(edJob1.getText().toString().trim());
                                    contradictionAddPeopleBean1.setJobUnit(edUnit1.getText().toString().trim());
                                    contradictionAddPeopleBean1.setCID(cid1);
                                    contradictionAddPeopleBean1.setCIndex(1);
                                    contradictionAddPeopleBean1.setTel(edTel1.getText().toString());


                                    peopleBeans.add(contradictionAddPeopleBean);
                                    peopleBeans.add(contradictionAddPeopleBean1);
                                    String toJson = gson.toJson(peopleBeans);


                                    ContradictionAddBean contradictionAddBean = new ContradictionAddBean();
                                    contradictionAddBean.setUserId(UserInfoUtil.getUserInfo().getUid());
                                    contradictionAddBean.setStandardAddressId(selectAddressId);
                                    contradictionAddBean.setEnumResolveType(strpvResolve);
                                    contradictionAddBean.setEnumCAEventOrderSaveStatus(1);
                                    contradictionAddBean.setCAPartyPerson(toJson);
                                    contradictionAddBean.setGisLocation(gisLocations);
                                    contradictionAddBean.setAddress(edAddress.getText().toString().trim());
                                    contradictionAddBean.setMemo(edMemo.getText().toString().trim());
                                    contradictionAddBean.setEnumCAEventOrderType(isSelfHandle);
                                    contradictionAddBean.setPicPath(informationAddPhotoAdapter.getPicPathsJson());
                                    assert mPresenter != null;
                                    if (!TextUtils.isEmpty(resetId)) {
                                        contradictionAddBean.setId(resetId);
                                        mPresenter.setResetContradiction(contradictionAddBean);
                                    } else {
                                        mPresenter.setContradiction(contradictionAddBean);
                                    }
                                }
                            }).setNegativeButton(getString(R.string.ove), null).create().show();
                    break;
                case R.id.delete_layout:
                    new CommonDialog.Builder(ContradictionAddActivity.this)
                            .setContent("确定删除吗？")
                            .setPositiveButton(getString(R.string.sure), (v, dialog) -> {
                                assert mPresenter != null;
                                mPresenter.DeleteContradictionRecord(resetId);
                            })
                            .setNegativeButton(getString(R.string.ove), null)
                            .create().show();
                    break;
                case R.id.invalid:
                    new CommonDialog.Builder(ContradictionAddActivity.this)
                            .setContent("确定作废吗？")
                            .setPositiveButton(getString(R.string.sure), (v, dialog) -> {
                                assert mPresenter != null;
                                mPresenter.InvalidContradictionRecord(resetId);
                            })
                            .setNegativeButton(getString(R.string.ove), null)
                            .create().show();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void animateMapStatus() {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(getCenterOfGravityPoint(latLngLists.get(0)));
        builder.zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    /**
     * 点击软键盘外面的区域关闭软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /*if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                KeybordUtil.hideKeyboard(ContradictionAddActivity.this);
            }
        }*/
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
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
    public void setContradictionData(String s) {
        finish();
        EventBus.getDefault().post(RouterHub.APP_CONTRADICTIONADDACTIVITY, RouterHub.APP_CONTRADICTIONADDACTIVITY);

    }

    @Override
    public void getContradictionRecord(ContradictionRecordBean item) {
        try {
            initCoord();
            ContradictionRecordBean.CAEventOrderBean caEventOrder = item.getCAEventOrder();
            gisLocations = item.getCAEventOrder().getGisLocations();
            if (caEventOrder != null) {
                resetId = caEventOrder.getId();

                if (StringUtil.isNotNullString(item.getCAEventOrder().getPicPath())) {
                    String photo = item.getCAEventOrder().getPicPath();
                    List<String> tbList1 = gson.fromJson(photo, List.class);
                    List<InformationItemPictureBean> list1 = new ArrayList<>();


                    if (caEventOrder.getEnumCAEventOrderSaveStatus() == 4) {
                        list1.add(0, new InformationItemPictureBean(R.drawable.picture));
                        for (String s : tbList1) {
                            list1.add(new InformationItemPictureBean((s), InformationItemPictureBean.IS_URL));
                        }
                    } else {
                        for (String s : tbList1) {
                            list1.add(new InformationItemPictureBean((s), InformationItemPictureBean.IS_VIS_DELETE));
                        }
                    }
                    informationAddPhotoAdapter.setNewData(list1);
                } else {
                    if (caEventOrder.getEnumCAEventOrderSaveStatus() != 0 && caEventOrder.getEnumCAEventOrderSaveStatus() != 4) {
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                if (!TextUtils.isEmpty(caEventOrder.getGisLocations())) {
                    StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean = gson.fromJson(caEventOrder.getGisLocations(), StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
                    LatLng latLng = new LatLng(pathBean.getLat(), pathBean.getLng());
                    MarkerOptions ooA1 = new MarkerOptions().position(latLng).icon(mbitmap);
                    mBaiduMap.addOverlay(ooA1);
                }

                zancunPeople = new ArrayList<>();
                selectAddressId = caEventOrder.getStandardAddressId();
                tvSelectAddress.setText(caEventOrder.getAddress());

                edAddress.setText(caEventOrder.getAddress());

                strpvResolve = caEventOrder.getEnumResolveType();
                if (strpvResolve == 0) {
                    tvDoType.setText("不愿化解");
                } else if (strpvResolve == 1) {
                    tvDoType.setText("自愿化解");
                } else if (strpvResolve == 2) {
                    tvDoType.setText("人民调解");
                } else if (strpvResolve == 3) {
                    tvDoType.setText("行政调解");
                } else if (strpvResolve == 4) {
                    tvDoType.setText("律师调解");
                } else if (strpvResolve == 5) {
                    tvDoType.setText("行政复议");
                } else if (strpvResolve == 6) {
                    tvDoType.setText("行政裁决");
                } else if (strpvResolve == 7) {
                    tvDoType.setText("仲裁");
                } else if (strpvResolve == 8) {
                    tvDoType.setText("公证");
                } else if (strpvResolve == 9) {
                    tvDoType.setText("民主协商");
                }

                optionResolveList.add("");
                optionResolveList.add("");
                optionResolveList.add("");
                optionResolveList.add("");
                optionResolveList.add("");
                edMemo.setText(caEventOrder.getMemo());

                edName.setText(item.getCAPartyPerson().get(0).getName());
                edName1.setText(item.getCAPartyPerson().get(1).getName());
                //防止空值
                if (!TextUtils.isEmpty(item.getCAPartyPerson().get(0).getCID())) {
                    edCid.setText(item.getCAPartyPerson().get(0).getCID());
                }
                if (!TextUtils.isEmpty(item.getCAPartyPerson().get(1).getCID())) {
                    edCid1.setText(item.getCAPartyPerson().get(1).getCID());
                }


                strSex = item.getCAPartyPerson().get(0).getEnumGender() + "";
                strSex1 = item.getCAPartyPerson().get(1).getEnumGender() + "";
                if ("0".equals(strSex)) {
                    tvSex.setText("不确定");
                } else if ("1".equals(strSex)) {
                    tvSex.setText("男");
                } else if ("2".equals(strSex)) {
                    tvSex.setText("女");
                }

                if ("0".equals(strSex1)) {
                    tvSex1.setText("不确定");
                } else if ("1".equals(strSex1)) {
                    tvSex1.setText("男");
                } else if ("2".equals(strSex1)) {
                    tvSex1.setText("女");
                }

                strBirth = (item.getCAPartyPerson().get(0).getBirthDate());
                strBirth1 = (item.getCAPartyPerson().get(1).getBirthDate());

                tvBirth.setText(strBirth);
                tvBirth1.setText(strBirth1);

                edMinzu.setText((item.getCAPartyPerson().get(0).getNation()));
                edMinzu1.setText((item.getCAPartyPerson().get(1).getNation()));

                edJob.setText((item.getCAPartyPerson().get(0).getJob()));
                edJob1.setText((item.getCAPartyPerson().get(1).getJob()));

                edUnit.setText((item.getCAPartyPerson().get(0).getJobUnit()));
                edUnit1.setText((item.getCAPartyPerson().get(1).getJobUnit()));

                edTel.setText(item.getCAPartyPerson().get(0).getTel());
                edTel1.setText(item.getCAPartyPerson().get(1).getTel());

                zancunPeople.addAll(item.getCAPartyPerson());

                if (caEventOrder.getEnumCAEventOrderSaveStatus() == 0 || caEventOrder.getEnumCAEventOrderSaveStatus() == 4) {
                    selfHandle.setClickable(true);
                } else {
                    edName.setFocusable(false);
                    edName.setFocusableInTouchMode(false);
                    edName1.setFocusable(false);
                    edName1.setFocusableInTouchMode(false);

                    edCid.setFocusable(false);
                    edCid.setFocusableInTouchMode(false);

                    edCid1.setFocusable(false);
                    edCid1.setFocusableInTouchMode(false);

                    tvSex.setClickable(false);
                    tvSex1.setClickable(false);

                    tvBirth.setClickable(false);
                    tvBirth1.setClickable(false);

                    edMinzu.setFocusable(false);
                    edMinzu.setFocusableInTouchMode(false);
                    edMinzu1.setFocusable(false);
                    edMinzu1.setFocusableInTouchMode(false);

                    edTel.setFocusable(false);
                    edTel.setFocusableInTouchMode(false);
                    edTel1.setFocusable(false);
                    edTel1.setFocusableInTouchMode(false);

                    edJob.setFocusable(false);
                    edJob.setFocusableInTouchMode(false);
                    edJob1.setFocusable(false);
                    edJob1.setFocusableInTouchMode(false);

                    edUnit.setFocusable(false);
                    edUnit.setFocusableInTouchMode(false);
                    edUnit1.setFocusable(false);
                    edUnit1.setFocusableInTouchMode(false);
                    selfHandle.setClickable(false);
                }
                if (caEventOrder.getEnumCAEventOrderType() == 0) {
                    isSelfHandle = 0;
                    selfHandle.setImageResource(R.drawable.s_close);
                } else {
                    isSelfHandle = 1;
                    selfHandle.setImageResource(R.drawable.s_open);
                }

                if (recordStatus == 0) {
                    deleteLayout.setVisibility(View.VISIBLE);
                } else if (recordStatus == 4) {
                    invalid.setVisibility(View.VISIBLE);
                    staging.setVisibility(View.GONE);
                    layoutRefuseReason.setVisibility(View.VISIBLE);
                    refuseReason.setText((caEventOrder.getRefuseReason()));
                } else {

                    tvSelectAddress.setClickable(false);

                    edAddress.setFocusable(false);
                    edAddress.setFocusableInTouchMode(false);

                    tvDoType.setClickable(false);

                    edMemo.setFocusable(false);
                    edMemo.setFocusableInTouchMode(false);


                    submit.setVisibility(View.GONE);
                    staging.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void isContradictionRecordDelete(String contradictionRecordBean) {
        finish();
        EventBus.getDefault().post(RouterHub.APP_CONTRADICTIONADDACTIVITY, RouterHub.APP_CONTRADICTIONADDACTIVITY);
    }


    @Override
    public void isInvalidContradictionRecord(String contradictionRecordBean) {
        finish();
        EventBus.getDefault().post(RouterHub.APP_CONTRADICTIONADDACTIVITY, RouterHub.APP_CONTRADICTIONADDACTIVITY);
    }

    /**
     * 暂存的修改返回
     *
     * @param contradictionRecordBean
     */
    @Override
    public void stagingReset(String contradictionRecordBean) {
        finish();
        EventBus.getDefault().post(RouterHub.APP_CONTRADICTIONADDACTIVITY, RouterHub.APP_CONTRADICTIONADDACTIVITY);
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void goToPhotoAlbum(int max) {
        PictureSelectorUtils.gotoPhoto(this, PictureMimeType.ofImage(),
                max, false, false, new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        try {
                            List<String> strings = new ArrayList<>();
                            for (LocalMedia media : result) {
                                if (StringUtil.isNotNullString(media.getCompressPath())) {
                                    strings.add(media.getCompressPath());
                                } else {
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                        strings.add(PictureFileUtils.getPath(ContradictionAddActivity.this, Uri.parse(media.getPath())));
                                    } else {
                                        if (StringUtil.isNotNullString(media.getPath()) && new File(media.getPath()).exists()) {
                                            strings.add(media.getPath());
                                        }
                                    }
                                }
                            }
                            mPresenter.upLoadFile("", strings);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    @Override
    public void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans) {
        try {
            for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                String path = uploadFileRsponseBean.getUrl();
                list.add(new InformationItemPictureBean((path)));
            }
            informationAddPhotoAdapter.setNewData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            if (mLocationClient.isStarted()) {
                mLocationClient.stop();
            }

        }

        super.onStop();
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
            mLocationClient = null;
            mapVessel.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
