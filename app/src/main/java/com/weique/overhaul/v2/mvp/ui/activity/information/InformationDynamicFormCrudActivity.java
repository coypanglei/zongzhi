package com.weique.overhaul.v2.mvp.ui.activity.information;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.CommonDialogFragment;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.MapUtils;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.PictureSelectorUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerInformationDynamicFormAAComponent;
import com.weique.overhaul.v2.mvp.contract.InformationDynamicFormACrudContract;
import com.weique.overhaul.v2.mvp.model.SearchProjectBean;
import com.weique.overhaul.v2.mvp.model.entity.DynamicFormOrmBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.InformationDynamicFormACrudPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.DynamicFormAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationTypeSecondAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonRecyclerPopupWindow;
import com.weique.overhaul.v2.mvp.ui.popupwindow.OptionPopup;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 信息采集   添加和修改界面
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_INFORMATIONDYNAMICFORMAAACTIVITY)
public class InformationDynamicFormCrudActivity extends BaseActivity<InformationDynamicFormACrudPresenter> implements InformationDynamicFormACrudContract.View {

    /**
     * 详情数据
     */
    public static final String LIST = "List";
    @Autowired(name = LIST)
    InformationDynamicFormSelectBean informationDynamicFormSelectBean;
    /**
     * 区分 添加还是修改
     */
    public static final String TYPE_KEY = "TYPE_KEY";
    @Autowired(name = TYPE_KEY)
    int type;
    /**
     * 标准地址id
     */
    public static final String STANDARD_ADDRESS_ID = "standardAddressId";
    @Autowired(name = STANDARD_ADDRESS_ID)
    String standardAddressId;
    /**
     * 标准地址名称
     */
    public static final String STANDARD_ADDRESS_NAME = "standardAddressName";
    @Autowired(name = STANDARD_ADDRESS_NAME)
    String standardAddressName;
    /**
     * 元素下的类型 elementTypeId
     */
    public static final String INFORMATIONDETAILBEAN = "INFORMATIONDETAILBEAN";
    @Autowired(name = INFORMATIONDETAILBEAN)
    InformationDetailBean informationDetailBean;
    /**
     * 新增  资源时选择的网格 信息
     */
    @Autowired(name = ARouerConstant.DATA_BEAN)
    SearchProjectBean.DepartmentsBean selectedObject;
    /**
     * 上个界面
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    /**
     * title
     */
    @Autowired(name = ARouerConstant.TITLE)
    String title;
    @Inject
    DynamicFormAdapter formCrudAdapter;
    @Inject
    HorizontalDividerItemDecoration decoration;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private LinearLayout mapAddressLayout;
    private LinearLayout upAddressLayout;
    private LinearLayout idScanLayout;
    private TextView mapAddress;
    private TextView upAddress;
    @Inject
    Gson gson;
    private View inflate;

    private List<DynamicFormOrmBean.MapListBean> lists;

    /**
     * 点击的图片所在的 adapter position
     */
    private int adapterPosition = -1;
    private static final int REQUEST_CODE_CAMERA = 102;
    private TimePickerView timePickerView;


    private CommonRecyclerPopupWindow<InformationTypeOneSecondBean.ElementListBean> popupWindow;
    private InformationTypeSecondAdapter mAdapter;

    private List<Object> targetList;
    /**
     * 动态映射中间层 成员变量
     */
    private DynamicFormOrmBean mDynamicFormOrmBean;
    private String targetTypeFiredFieldName;
    private String checkText;
    private List<Map<String, Object>> mList;
    /**
     * 网格id
     */
    @Autowired(name = ARouerConstant.DEPARTMENT_ID)
    String mDepartmentId;
    private OCR OCRInstance;
    private CountDownTimer timer;
    private CommonDialogFragment commonDialogFragment;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInformationDynamicFormAAComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_information_dynamic_form_a;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ImmersionBar.with(this).statusBarColor(R.color.white).init();
            ARouter.getInstance().inject(this);
            setTitle(title);
            initScan();
            initHeardView();
            if (StringUtil.isNullString(mDepartmentId)) {
                mDepartmentId = UserInfoUtil.getUserInfo().getDepartmentId();
            }
            ArmsUtils.configRecyclerView(recyclerView, linearLayoutManager);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setAdapter(formCrudAdapter);
            if (informationDynamicFormSelectBean == null) {
                return;
            }
            //上级界面带来的  标准地址id
            if (StringUtil.isNotNullString(standardAddressId)) {
                informationDetailBean.setStandardAddressId(standardAddressId);
                informationDetailBean.setStandardAddressFullPath(standardAddressName);
                upAddress.setText(standardAddressName);
            }

            ArrayList<InformationDynamicFormSelectBean.StructureInJsonBean> structureInJson =
                    informationDynamicFormSelectBean.getStructureInJson();
            if (structureInJson != null && structureInJson.size() > 0) {
                Iterator<InformationDynamicFormSelectBean.StructureInJsonBean> iterator = structureInJson.iterator();
                while (iterator.hasNext()) {
                    try {
                        InformationDynamicFormSelectBean.StructureInJsonBean next = iterator.next();
                        if (next.isDisabled()) {
                            iterator.remove();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (InformationDynamicFormSelectBean.StructureInJsonBean structureInJsonBean : structureInJson) {
                    if (StringUtil.isNullString(structureInJsonBean.getDefaultVal())) {
                        if (structureInJsonBean.getOption() != null && structureInJsonBean.getOption().size() > 0) {
                            structureInJsonBean.setDefaultVal(structureInJsonBean.getOption().get(0));
                        }
                    }
                }
                formCrudAdapter.setNewData(structureInJson);
                formCrudAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                formCrudAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        if (adapter.getItem(position) instanceof InformationDynamicFormSelectBean.StructureInJsonBean) {
                            InformationDynamicFormSelectBean.StructureInJsonBean bean =
                                    (InformationDynamicFormSelectBean.StructureInJsonBean) adapter.getItem(position);
                            OptionPopup optionPopup = null;
                            switch (bean.getIdentification().getType()) {
                                case InformationDynamicFormSelectBean.DateTime:
                                    showDateTimeSelector(bean, position);
                                    break;
                                case InformationDynamicFormSelectBean.DropdownList:
                                case InformationDynamicFormSelectBean.Option:
                                    try {
                                        if (view.getId() == R.id.all_item) {
                                            optionPopup = new OptionPopup(this, bean.getName(),
                                                    bean.getIdentification().getType(),
                                                    bean.getOption(), bean.getDefaultVal());
                                            optionPopup.setOnSureClickListener(nameValue -> {
                                                bean.setDefaultVal(nameValue);
                                                formCrudAdapter.setData(position, bean);
                                            });
                                            optionPopup.showPopupWindow();
                                            KeybordUtil.hideKeyboard(InformationDynamicFormCrudActivity.this);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case InformationDynamicFormSelectBean.CheckBox:
                                    try {
                                        if (view.getId() == R.id.all_item) {
                                            List<String> arrayList = new ArrayList<>();
                                            String defaultValString = bean.getDefaultVal();
                                            if (StringUtil.isNotNullString(defaultValString)) {
                                                if (defaultValString.contains("\\")) {
                                                    defaultValString = defaultValString.replace("\\", "");
                                                }
                                                if (defaultValString.contains("[")) {
                                                    arrayList = gson.fromJson(defaultValString, ArrayList.class);
                                                } else {
                                                    if (defaultValString.contains("\"")) {
                                                        arrayList.add(gson.fromJson(defaultValString, String.class));
                                                    } else {
                                                        arrayList.add(defaultValString);
                                                    }
                                                }
                                            }
                                            optionPopup = new OptionPopup(this, bean.getName(),
                                                    bean.getIdentification().getType(),
                                                    bean.getOption(), arrayList);
                                            optionPopup.setOnSureClickListener(nameValue -> {
                                                bean.setDefaultVal(nameValue);
                                                formCrudAdapter.setData(position, bean);
                                            });
                                            optionPopup.showPopupWindow();
                                            KeybordUtil.hideKeyboard(InformationDynamicFormCrudActivity.this);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                default:
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                formCrudAdapter.setOnPictureClickLisenter((adapterPosition, max, type) -> {
                    assert mPresenter != null;
                    mPresenter.getPermission(adapterPosition, max, type);
                });
            }
            //添加 并 表单类型id不为空时 身份证扫描相关
            if (type == EventBusConstant.ADD && StringUtil.isNotNullString(informationDetailBean.getElementTypeId())) {
                mPresenter.getDynamicFormOrm(informationDetailBean.getElementTypeId(), DynamicFormOrmBean.SCAN_TYPE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化本地质量控制模型,释放代码在onDestory中
     * 调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
     */
    private void initScan() {
        try {
            OCRInstance = OCR.getInstance(AppManager.getAppManager().getmApplication());
            CameraNativeHelper.init(this, OCRInstance.getLicense(),
                    (errorCode, e) -> {
                        String msg;
                        switch (errorCode) {
                            case CameraView.NATIVE_SOLOAD_FAIL:
                                msg = "加载so失败，请确保apk中存在ui部分的so";
                                break;
                            case CameraView.NATIVE_AUTH_FAIL:
                                msg = "授权本地质量控制token获取失败";
                                break;
                            case CameraView.NATIVE_INIT_FAIL:
                                msg = "本地质量控制";
                                break;
                            default:
                                msg = String.valueOf(errorCode);
                        }
                        Timber.i("本地质量控制初始化错误，错误原因： " + msg);
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        // 释放本地质量控制模型
        try {
            if (timer != null) {
                timer.cancel();
            }
            if (commonDialogFragment != null) {
                commonDialogFragment.onDestroy();
                commonDialogFragment = null;
            }
            // 释放本地质量控制模型
            try {
                CameraNativeHelper.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 释放内存资源
            try {
                OCR.getInstance(AppManager.getAppManager().getmApplication()).release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * 设置 时间选择器 时间
     *
     * @param bean
     * @param pos
     */
    private void showDateTimeSelector(InformationDynamicFormSelectBean.StructureInJsonBean bean, int pos) {
        /**
         * 显示时间选择器
         */
        try {
            timePickerView = PickerViewUtil.selectPickerTime(this, Constant.YMDHM, (date, v) -> {
                try {
                    SimpleDateFormat format = new SimpleDateFormat(Constant.YMDHM, Locale.CHINA);
                    bean.setDefaultVal(format.format(date));
                    formCrudAdapter.setData(pos, bean);
                    formCrudAdapter.notifyItemChanged(pos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            timePickerView.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化头布局
     */
    private void initHeardView() {
        try {
            if (inflate == null) {
                inflate = View.inflate(this, R.layout.dynamic_item__head_view, null);
                mapAddressLayout = inflate.findViewById(R.id.map_address_l);
                upAddressLayout = inflate.findViewById(R.id.up_address_l);
                idScanLayout = inflate.findViewById(R.id.id_scan_layout);
                mapAddress = inflate.findViewById(R.id.map_address);
                upAddress = inflate.findViewById(R.id.up_address);
                formCrudAdapter.addHeaderView(inflate);
                mapAddressLayout.setOnClickListener(v -> mPresenter.getDepartment(mDepartmentId));
                upAddressLayout.setOnClickListener(v ->
                        //去标准地址
                        ARouter.getInstance().build(RouterHub.APP_STANDARDADDRESSONENEWACTIVITY)
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_INFORMATIONDYNAMICFORMAAACTIVITY)
                                .withString(ARouerConstant.TITLE, "选择标准地址")
                                .withParcelable(ARouerConstant.DATA_BEAN, selectedObject)
                                .navigation());
            }
            if (StringUtil.isNullString(informationDetailBean.getStandardAddressFullPath())) {
                upAddress.setText(informationDetailBean.getFullPath());
            } else {
                upAddress.setText(informationDetailBean.getStandardAddressFullPath());
            }
            String replaceS = informationDetailBean.getPointsInJson();
            if (StringUtil.isNotNullString(replaceS)) {
                //如果是标准地址跳转过来的
                if (RouterHub.APP_STANDARDADDRESSLOOKACTIVITY.equals(source)) {
                    //判断 获取到的 数据是  double类型 再 弹框
                    String finalReplaceS = replaceS;
                    commonDialogFragment = CommonDialogFragment.newInstance();
                    commonDialogFragment.setOnComfirmClick("使用", v -> {
                        try {
                            informationDetailBean.setPointsInJson(finalReplaceS);
                            StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                                    gson.fromJson(finalReplaceS, StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
                            mapAddress.setText(String.format(getResources().getString(R.string.lon_lat), pathBean.getLng(), pathBean.getLat()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            ArmsUtils.makeText("定位坐标错误,请手动添加");
                        }
                        commonDialogFragment.dismiss();
                        commonDialogFragment = null;
                    });
                    commonDialogFragment.setGravity(Gravity.CENTER);
                    commonDialogFragment.setContent("是否以标准地址坐标，作为当前采集信息坐标值");
                    commonDialogFragment.setAnimStyle(R.style.BottomAnimation)
                            .show(getSupportFragmentManager());
                } else {
                    if (informationDetailBean.getPointsInJson() != null && informationDetailBean.getPointsInJson().contains("\\")) {
                        replaceS = replaceS.replace("\\", "");
                    }
                    StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                            gson.fromJson(replaceS, StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
                    mapAddress.setText(String.format(getResources().getString(R.string.lon_lat), pathBean.getLng(), pathBean.getLat()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新  地址 坐标 或 者 是  上级地址
     */
    @Subscriber(tag = RouterHub.APP_INFORMATIONDYNAMICFORMAAACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.UPDATE_UP_ADDRESS:
                    if (eventBusBean.getData() instanceof StandardAddressStairBean.StandardAddressStairBaseBean) {
                        try {
                            StandardAddressStairBean.StandardAddressStairBaseBean bean = (StandardAddressStairBean.StandardAddressStairBaseBean) eventBusBean.getData();
                            informationDetailBean.setStandardAddressId(bean.getId());
                            informationDetailBean.setStandardAddressFullPath(bean.getFullPath());
                            upAddress.setText(bean.getFullPath());
//                        mDepartmentId = bean.getDepartmentId();
                            if (StringUtil.isNotNullString(bean.getGisLocations())) {
                                commonDialogFragment = CommonDialogFragment.newInstance();
                                commonDialogFragment.setOnComfirmClick("使用", v -> {
                                    try {
                                        informationDetailBean.setPointsInJson(bean.getGisLocations());
                                        StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                                                gson.fromJson(bean.getGisLocations(), StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
                                        mapAddress.setText(String.format(getResources().getString(R.string.lon_lat), pathBean.getLng(), pathBean.getLat()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        ArmsUtils.makeText("定位坐标错误,请手动添加");
                                    }
                                    commonDialogFragment.dismiss();
                                    commonDialogFragment = null;
                                });
                                commonDialogFragment.setGravity(Gravity.CENTER);
                                commonDialogFragment.setContent("是否以标准地址坐标，作为当前采集信息坐标值");
                                commonDialogFragment.setAnimStyle(R.style.BottomAnimation)
                                        .show(getSupportFragmentManager());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case EventBusConstant.UPDATE_UP_LOCATION:
                    if (eventBusBean.getData() instanceof StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean) {
                        StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                                (StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean) eventBusBean.getData();
                        informationDetailBean.setPointsInJson(gson.toJson(pathBean));
                        mapAddress.setText(String.format(getResources().getString(R.string.lon_lat), pathBean.getLng(), pathBean.getLat()));
                    }
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
    public void LoadingMore(boolean b) {
        try {
            if (popupWindow != null && popupWindow.isShowing()) {
                if (mAdapter != null) {
                    if (b) {
                        mAdapter.loadMoreEnd(true);
                    } else {
                        mAdapter.loadMoreComplete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.toolbar_submit})
    public void onClick() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            if (StringUtil.isNullString(informationDetailBean.getStandardAddressId())) {
                showMessage("标准地址不能为空");
                return;
            }
            if (StringUtil.isNullString(informationDetailBean.getPointsInJson())) {
                showMessage("地图定位不能为空");
                return;
            }

            List<InformationDynamicFormSelectBean.StructureInJsonBean> data = formCrudAdapter.getData();
            for (InformationDynamicFormSelectBean.StructureInJsonBean bean : data) {
                if (StringUtil.isNotNullString(bean.getDefaultVal()) && bean.getDefaultVal().contains("\\")) {
                    bean.setDefaultVal(bean.getDefaultVal().replace("\\", ""));
                }
                if (bean.isIsRequired() && StringUtil.isNullString(bean.getDefaultVal())) {
                    ArmsUtils.makeText("请填写或选择 " + bean.getName() + " 信息");
                    return;
                }
            }

            informationDynamicFormSelectBean.setStructureInJson((ArrayList<InformationDynamicFormSelectBean.StructureInJsonBean>) data);
            String informationDynamicFormSelectBeanJson = gson.toJson(informationDynamicFormSelectBean);
            informationDetailBean.setStructureInJsons(informationDynamicFormSelectBeanJson);
            informationDetailBean.setFullPath(upAddress.getText().toString());
            //informationDetailBean 中 DepartmentId() 为空时 再设置数据
            if (StringUtil.isNotNullString(mDepartmentId) && StringUtil.isNullString(informationDetailBean.getDepartmentId())) {
                informationDetailBean.setDepartmentId(mDepartmentId);
            }
            if (type == EventBusConstant.ADD) {
                mPresenter.createElement(informationDetailBean);
            }
            if (type == EventBusConstant.ALERT) {
                informationDetailBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());
                mPresenter.editElement(informationDetailBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void killMyself() {
        finish();
    }

    @Override
    public Activity getContext() {
        return this;
    }

    /**
     * 获取网格信息  主要获取 网格 多边形信息
     *
     * @param gridInformationBean gridInformationBean
     */
    @Override
    public void gotoMapTv(GridInformationBean gridInformationBean) {
        try {
            if (gridInformationBean == null || StringUtil.isNullString(gridInformationBean.getPointsInJSON())) {
                ArmsUtils.makeText("未获取到网格信息");
                return;
            }
            ARouter.getInstance().build(RouterHub.APP_MAPACTIVITY)
                    .withString(MapActivity.POINTS_IN_JSON, gridInformationBean.getPointsInJSON())
                    .withString(MapActivity.LONANDLAT, informationDetailBean.getPointsInJson())
                    .withString(ARouerConstant.SOURCE, RouterHub.APP_INFORMATIONDYNAMICFORMAAACTIVITY)
                    .navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新建或修改成功
     */
    @Override
    public void updateData(InformationTypeOneSecondBean.ElementListBean bean) {
        try {
            if (type == EventBusConstant.ADD) {
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.ADD, bean), source);
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.ADD, bean), RouterHub.APP_STANDARDADDRESSLOOKACTIVITY);
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.ADD, bean), RouterHub.APP_INFORMATIONTYPEFIRSTACTIVITY);
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.ADD, bean), RouterHub.APP_INFORMATIONCOLLECTIONACTIVITY);
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.ADD, bean), RouterHub.APP_INFORMATIONLISTFORADDRESSDETAILFRAGMENT);
            } else if (type == EventBusConstant.ALERT) {
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.ALERT, bean), source);
                //这俩顺序别颠倒 更新上两级 界面中的列表/详情
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.ALERT, informationDetailBean), RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY);
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.ALERT, informationDynamicFormSelectBean), RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY);
                //更新 审核 详情界面
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.ALERT, informationDetailBean), RouterHub.APP_RESOURCEAUDITDETAILACTIVITY);
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.ALERT, bean), RouterHub.APP_STANDARDADDRESSLOOKACTIVITY);
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.ALERT, bean), RouterHub.APP_INFORMATIONTYPESECONDACTIVITY);
            }
            killMyself();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片上传成功  跟新界面
     *
     * @param uploadFileRsponseBeans
     */
    @Override
    public void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans) {
        try {
            ArrayList<String> strings = new ArrayList<>();
            for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                strings.add(uploadFileRsponseBean.getUrl());
            }
            if (adapterPosition >= 0 && strings.size() > 0) {
                InformationDynamicFormSelectBean.StructureInJsonBean item = formCrudAdapter.getItem(adapterPosition - 1);
                if (item.getIdentification().getType().equals(InformationDynamicFormSelectBean.SinglePic)) {
                    item.setDefaultVal(strings.get(0));
                } else if (item.getIdentification().getType().equals(InformationDynamicFormSelectBean.MultiPics)
                        || item.getIdentification().getType().equals(InformationDynamicFormSelectBean.SingleVideo)) {
                    if (StringUtil.isNotNullString(item.getDefaultVal())) {
                        String defaultVal = item.getDefaultVal();
                        if (defaultVal.contains("[")) {
                            ArrayList<String> arrayList = gson.fromJson(defaultVal, ArrayList.class);
                            strings.addAll(arrayList);
                        }
                    }
                    item.setDefaultVal(gson.toJson(strings));
                }
                formCrudAdapter.setData(adapterPosition - 1, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goToPhotoAlbum(int adapterPosition, int max, String type) {
        try {
            int pictureMimeType;
            boolean previewVideo;
            if (InformationDynamicFormSelectBean.SingleVideo.equals(type)) {
                pictureMimeType = PictureMimeType.ofVideo();
                previewVideo = true;
            } else {
                pictureMimeType = PictureMimeType.ofImage();
                previewVideo = false;
            }
            InformationDynamicFormCrudActivity.this.adapterPosition = adapterPosition;
            PictureSelectorUtils.gotoPhoto(this, pictureMimeType,
                    max, previewVideo, false, new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(List<LocalMedia> result) {
                            try {
                                List<String> strings = new ArrayList<>();
                                for (LocalMedia media : result) {
                                    if (StringUtil.isNotNullString(media.getCompressPath())) {
                                        strings.add(media.getCompressPath());
                                    } else {
                                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                            strings.add(PictureFileUtils.getPath(InformationDynamicFormCrudActivity.this, Uri.parse(media.getPath())));
                                        } else {
                                            if (StringUtil.isNotNullString(media.getPath()) && new File(media.getPath()).exists()) {
                                                strings.add(media.getPath());
                                            }
                                        }
                                    }
                                }
                                if (strings.size() > 0) {
                                    mPresenter.upLoadFile(informationDetailBean.getElementId(), strings);
                                } else {
                                    ArmsUtils.makeText("获取资源失败");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * lists
     *
     * @param lists lists
     */
    @Override
    public void setDynamicFormOrmData(List<DynamicFormOrmBean.MapListBean> lists) {
        try {
            this.lists = lists;
            idScanLayout.setVisibility(View.VISIBLE);
            idScanLayout.setOnClickListener(v -> {
                OCRInstance.initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
                    @Override
                    public void onResult(AccessToken result) {
                        try {
                            String token = result.getAccessToken();
                            Intent intent = new Intent(InformationDynamicFormCrudActivity.this, CameraActivity.class);
                            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                                    Constant.getSaveFile(getApplication()).getAbsolutePath());
                            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                            startActivityForResult(intent, REQUEST_CODE_CAMERA);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(OCRError error) {
                        error.printStackTrace();
                        Timber.i("AK，SK方式获取token失败" + error.getMessage());
                    }
                }, getApplicationContext(), Constant.BAIDU_SCAN_AK, Constant.BAIDU_SCAN_SK);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 要合并的动态映射
     * 获取到  动态映射  的 检索字段  去 动态表单  中找到 并 再输入完成后去获取对应信息 绘制界面
     *
     * @param dynamicFormOrmBean dynamicFormOrmBean 关系映射中间曾  转换数据
     */
    @Override
    public void setMergeDynamicFormOrmData(DynamicFormOrmBean dynamicFormOrmBean) {
        try {
            this.mDynamicFormOrmBean = dynamicFormOrmBean;
            formCrudAdapter.addMergeInfoSearchClick(dynamicFormOrmBean, text -> {
                if (StringUtil.isNullString(text)) {
                    ArmsUtils.makeText("请输入内容");
                    return;
                }

                for (DynamicFormOrmBean.MapListBean listBean : dynamicFormOrmBean.getMapList()) {
                    if (listBean.getSourceid().equals(dynamicFormOrmBean.getSourceTypeFiredFieldName())) {
                        targetTypeFiredFieldName = listBean.getTargetid();
                        break;
                    }
                }
                checkText = text;
                mPresenter.getFormInfoBySearching(true, false, targetTypeFiredFieldName, dynamicFormOrmBean.getTargetTypeId(), text);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示要同步的目标  信息列表 popup
     *
     * @param list list
     */
    @Override
    public void showMergeTargetListPopup(List<Map<String, Object>> list, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                mList.addAll(list);
            } else {
                this.mList = list;
            }
            List<InformationTypeOneSecondBean.ElementListBean> elementListBeans = new ArrayList<>();
            for (Object ss : list) {
                InformationTypeOneSecondBean.ElementListBean listBean = gson.fromJson(gson.toJson(ss), InformationTypeOneSecondBean.ElementListBean.class);
                elementListBeans.add(listBean);
            }
            if (popupWindow == null) {
                if (mAdapter == null) {
                    mAdapter = new InformationTypeSecondAdapter();
                }
                mAdapter.setNewData(elementListBeans);
                popupWindow = new CommonRecyclerPopupWindow<>(this, mAdapter,
                        (BaseQuickAdapter.OnItemChildClickListener) (adapter, view, position) -> {
                            try {
                                switch (view.getId()) {
                                    case R.id.input:
                                        Map<String, Object> o = mList.get(position);
                                        informationDetailBean = MapUtils.mapValueSetToObject(o, informationDetailBean);
                                        mDepartmentId = informationDetailBean.getDepartmentId();
                                        Object item = adapter.getItem(position);
                                        if (item instanceof InformationTypeOneSecondBean.ElementListBean) {
                                            InformationTypeOneSecondBean.ElementListBean listBean = (InformationTypeOneSecondBean.ElementListBean) item;
                                            //这些是映射中 间成  没有的固定 参数
                                            listBean.setElementTypeId(mDynamicFormOrmBean.getTargetTypeId());
                                            mPresenter.getDataStructureInJson(listBean, o);
                                        } else {
                                            ArmsUtils.makeText("获取信息失败");
                                        }
                                        if (popupWindow != null && popupWindow.isShowing()) {
                                            popupWindow.dismiss();
                                        }
                                        break;
                                    default:
                                        break;
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, (BaseQuickAdapter.RequestLoadMoreListener) () -> {
                    mPresenter.getFormInfoBySearching(false, true, targetTypeFiredFieldName, mDynamicFormOrmBean.getTargetTypeId(), checkText);
                });
            } else {
                popupWindow.setNewData(elementListBeans, isLoadMore);
            }
            if (!popupWindow.isShowing()) {
                popupWindow.showPopupWindow();
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 映射来源 数据映射到中间层
     *
     * @param dynamicFormSelectBean dynamicFormSelectBean 来源映射对象
     */
    @Override
    public void sourceMapMiddle(InformationDynamicFormSelectBean dynamicFormSelectBean) {
        try {
            List<Integer> changedPos = new ArrayList<>();
            if (mDynamicFormOrmBean != null) {
                //来源数据映射到中间层
                List<DynamicFormOrmBean.MapListBean> mapList = mDynamicFormOrmBean.getMapList();
                ArrayList<InformationDynamicFormSelectBean.StructureInJsonBean> sourceStructureInJson = dynamicFormSelectBean.getStructureInJson();
                Iterator<InformationDynamicFormSelectBean.StructureInJsonBean> sourceIterator = sourceStructureInJson.iterator();
                for (DynamicFormOrmBean.MapListBean listBean : mapList) {
                    while (sourceIterator.hasNext()) {
                        InformationDynamicFormSelectBean.StructureInJsonBean next = sourceIterator.next();
                        if (next.getPropertyName().equals(listBean.getTargetid())) {
                            listBean.setValue(next.getDefaultVal());
                            break;
                        }
                    }
                }
                sourceIterator = null;
                //中间层映射到 目标层
                ArrayList<InformationDynamicFormSelectBean.StructureInJsonBean> targetStructureInJson = informationDynamicFormSelectBean.getStructureInJson();
                Iterator<InformationDynamicFormSelectBean.StructureInJsonBean> targetIterator = targetStructureInJson.iterator();
                for (DynamicFormOrmBean.MapListBean listBean : mapList) {
                    while (targetIterator.hasNext()) {
                        InformationDynamicFormSelectBean.StructureInJsonBean next = targetIterator.next();
                        if (next.getPropertyName().equals(listBean.getSourceid())) {
                            next.setDefaultVal(listBean.getValue());
                            break;
                        }
                    }
                }
                targetIterator = null;
                formCrudAdapter.setNewData(targetStructureInJson);
                initHeardView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case REQUEST_CODE_CAMERA:
                        if (data != null) {
                            String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                            String filePath = Constant.getSaveFile(getApplicationContext()).getAbsolutePath();
                            if (!TextUtils.isEmpty(contentType)) {
                                if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                                    recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                                } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                                    recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                                }
                            }
                        }
                        break;
                    default:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启扫描
     *
     * @param idCardSide idCardSide
     * @param filePath   filePath
     */
    private void recIDCard(String idCardSide, String filePath) {
        try {
            IDCardParams param = new IDCardParams();
            param.setImageFile(new File(filePath));
            // 设置身份证正反面
            param.setIdCardSide(idCardSide);
            // 设置方向检测
            param.setDetectDirection(true);
            // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
            param.setImageQuality(20);

            OCRInstance.recognizeIDCard(param, new OnResultListener<IDCardResult>() {
                @Override
                public void onResult(IDCardResult result) {
                    try {
                        if (result != null) {
                            Map<String, Object> map = MapUtils.entityToMap(result);
                            Iterator<String> iterator = map.keySet().iterator();
                            while (iterator.hasNext()) {
                                String next = iterator.next();
                                for (DynamicFormOrmBean.MapListBean bean : lists) {
                                    if (next.equals(bean.getTargetid())) {
                                        String nextV = map.get(next).toString();
                                        //这里需要日期格式  但是  扫描到的不是 日期格式  birthday  暂时写死
                                        if ("birthday".equals(next)) {
                                            StringBuilder sb = new StringBuilder(nextV);
                                            if (sb.length() > 6) {
                                                sb.insert(6, "/");
                                                sb.insert(4, "/");
                                                nextV = sb.toString();
                                            }
                                        }
                                        bean.setValue(nextV);
                                        formCrudAdapter.setDataByDynamicFormOrmBean(bean);
                                        break;
                                    }
                                }
                            }
                        } else {
                            ArmsUtils.makeText("扫描获取信息失败，请手动填写");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(OCRError error) {
                    Timber.i(error.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
