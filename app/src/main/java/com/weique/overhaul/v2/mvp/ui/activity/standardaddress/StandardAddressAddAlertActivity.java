package com.weique.overhaul.v2.mvp.ui.activity.standardaddress;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.CommonDialogFragment;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerAddressAddAlertComponent;
import com.weique.overhaul.v2.mvp.contract.AddressAddAlertContract;
import com.weique.overhaul.v2.mvp.model.entity.BaseSearchPopupBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressUpItemBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.AddressAddAlertPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.popupwindow.SearchPopup;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 添加 标准地址 或是修改
 * <p>
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_STANDARDADDRESSADDALERTACTIVITY)
public class StandardAddressAddAlertActivity extends BaseActivity<AddressAddAlertPresenter> implements AddressAddAlertContract.View {


    /**
     * 状态码 @activ
     */
    public static final String STATE = "STATE";
    /**
     * 查询  或是 修改时  查询详情的did @detailId
     */
    public static final String STANDARD_ADDRESSB_BEAN = "StandardAddressBean";
    @Autowired(name = STANDARD_ADDRESSB_BEAN)
    StandardAddressBean mStandardAddressBean;
    public static final String STANDARD_ADDRESSB_BEAN_DEPARTMENT_BEAN = "StandardAddressBeanDepartmentBean";

    @Autowired(name = STANDARD_ADDRESSB_BEAN_DEPARTMENT_BEAN)
    StandardAddressBean.DepartmentBean mStandardAddressBeanDepartmentBean;

    String[] activ = {"增加", "修改", "查看", "删除"};
    /**
     * 当是  *添加* 标准地址时 用上面传下来的值网格id
     */
    @Autowired(name = Constant.DEPARTMENT_ID)
    String departmentId;
    /**
     * 上层传过来的  类型
     */
    @Autowired(name = StandardAddressUpListActivity.ADDRESSTYPE_ID)
    String addressTypeId;
    /**
     * 添加时 上层传过来的 多变进行JSON
     */
    @Autowired(name = MapActivity.POINTS_IN_JSON)
    String pointsInJSON;
    /**
     * 状态码 区分是添加还是修改
     */
    @Autowired(name = STATE)
    int state;


    @BindString(R.string.select_tier)
    String selectTier;
    @BindView(R.id.map_address)
    TextView mapAddress;
    @BindView(R.id.up_address)
    TextView upAddress;
    @BindView(R.id.standard_name)
    EditText standardName;
    @BindView(R.id.select_up_address)
    LinearLayout selectUpAddress;
    @BindView(R.id.standard_address_code)
    EditText standardAddressCode;
    @BindView(R.id.remark)
    EditText remark;
    @BindView(R.id.select_map_location)
    LinearLayout selectMapLocation;
    @BindView(R.id.toolbar_submit)
    RelativeLayout toolbarSubmit;

    @Inject
    Gson gson;
    @BindView(R.id.select_standard_address)
    TextView selectStandardAddress;
    @BindView(R.id.select_standard_address_layout)
    LinearLayout selectStandardAddressLayout;

    private List<? extends BaseSearchPopupBean> tierList;
    private SearchPopup searchPopup;
    private CommonDialogFragment commonDialogFragment;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddressAddAlertComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_address_add_alert;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            setTitle(activ[state]);
            switch (state) {
                case EventBusConstant.ADD:
                    mStandardAddressBean = new StandardAddressBean();
                    mStandardAddressBean.setDepartmentId(departmentId);
                    mStandardAddressBean.setStandardAddressTypeId(addressTypeId);
                    mStandardAddressBean.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                    mStandardAddressBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());
                    mPresenter.getGridByDepartmentId(departmentId);
                    break;
                case EventBusConstant.ALERT:
                    setData();
                    break;
                default:
            }
            StandardAddressBean.StandardAddressTypeBean standardAddressType = mStandardAddressBean.getStandardAddressType();
            selectStandardAddress.setText(StringUtil.setText(standardAddressType.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.select_map_location, R.id.select_up_address, R.id.toolbar_submit, R.id.select_standard_address_layout})
    public void onClick(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            KeybordUtil.hideKeyboard(this);
            switch (view.getId()) {
                case R.id.select_map_location:
                    ARouter.getInstance().build(RouterHub.APP_MAPACTIVITY)
                            .withString(MapActivity.LONANDLAT, mStandardAddressBean.getGisLocations())
                            .withString(MapActivity.POINTS_IN_JSON, pointsInJSON)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_STANDARDADDRESSADDALERTACTIVITY)
                            .navigation();
                    break;
                case R.id.select_up_address:
                    if (StringUtil.isNullString(selectStandardAddress.getText().toString())) {
                        ArmsUtils.makeText(selectTier);
                        return;
                    }
                    if (state == EventBusConstant.ADD) {
                        ARouter.getInstance().build(RouterHub.APP_STANDARDADDRESSUPLISTACTIVITY)
                                .withString(Constant.DEPARTMENT_ID, departmentId)
                                .withString(StandardAddressUpListActivity.ADDRESSTYPE_ID, addressTypeId)
                                .navigation();
                    } else if (state == EventBusConstant.ALERT) {
                        ARouter.getInstance().build(RouterHub.APP_STANDARDADDRESSUPLISTACTIVITY)
                                .withString(Constant.DEPARTMENT_ID, mStandardAddressBean.getDepartmentId())
                                .withString(StandardAddressUpListActivity.ADDRESSTYPE_ID, mStandardAddressBean.getStandardAddressTypeId())
                                .navigation();
                    }
                    break;
                case R.id.toolbar_submit:

                    if (StringUtil.isNullString(standardName.getText().toString())) {
                        ArmsUtils.makeText("标准地址名称不能为空");
                        return;
                    } else {
                        mStandardAddressBean.setName(standardName.getText().toString());
                    }

                    if (StringUtil.isNullString(selectStandardAddress.getText().toString())) {
                        ArmsUtils.makeText(selectTier);
                        return;
                    }
                    if (StringUtil.isNullString(mStandardAddressBean.getPId())) {
                        ArmsUtils.makeText("请选择上级地址");
                        return;
                    }

                    if (StringUtil.isNullString(mStandardAddressBean.getGisLocations())) {
                        ArmsUtils.makeText("请选择地图定位");
                        return;
                    }


                    if (StringUtil.isNotNullString(standardAddressCode.getText().toString())) {
                        mStandardAddressBean.setCode(standardAddressCode.getText().toString());
                    }
                    mStandardAddressBean.setMemo(StringUtil.setText(remark.getText().toString()));

                    mStandardAddressBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());
                    mPresenter.submitStandardAddress(mStandardAddressBean, state);
                    break;

                case R.id.select_standard_address_layout:
                    if (tierList != null) {
                        showPopup(tierList);
                    } else {
                        mPresenter.getDepartmentDownInfo(departmentId);
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新  地址 坐标 或 者 是  上级地址
     */
    @Subscriber(tag = RouterHub.APP_STANDARDADDRESSADDALERTACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.UPDATE_UP_ADDRESS:
                    if (eventBusBean.getData() instanceof StandardAddressUpItemBean.DataBean) {
                        StandardAddressUpItemBean.DataBean bean = (StandardAddressUpItemBean.DataBean) eventBusBean.getData();
                        mStandardAddressBean.setPId(bean.getId());
                        upAddress.setText(bean.getName());
                        if (StringUtil.isNotNullString(bean.getGisLocations())) {
                            commonDialogFragment = CommonDialogFragment.newInstance();
                            commonDialogFragment.setOnComfirmClick("使用", v -> {
                                try {
                                    mStandardAddressBean.setGisLocations(bean.getGisLocations());
                                    StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                                            gson.fromJson(bean.getGisLocations(), StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
                                    mapAddress.setText(String.format(getResources().getString(R.string.lon_lat), pathBean.getLng(), pathBean.getLat()));
                                    commonDialogFragment.getDialog().dismiss();
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
                    }
                    break;
                case EventBusConstant.UPDATE_UP_LOCATION:
                    try {
                        if (eventBusBean.getData() instanceof StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean) {
                            StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                                    (StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean) eventBusBean.getData();
                            mStandardAddressBean.setGisLocations(gson.toJson(pathBean));
                            mapAddress.setText(String.format(getResources().getString(R.string.lon_lat), pathBean.getLng(), pathBean.getLat()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
     * 更新界面
     */
    public void setData() {
        try {
            pointsInJSON = mStandardAddressBeanDepartmentBean.getPointsInJSON();
            standardName.setText(mStandardAddressBean.getName());
            standardName.setSelection(mStandardAddressBean.getName().length());
            if (StandardAddressUpListActivity.NO_SUPERIOR_ADDRESS_PID.equals(mStandardAddressBean.getPId())) {
                upAddress.setText(StandardAddressUpListActivity.NO_SUPERIOR_ADDRESS_PID_HINT);
            } else {
                String upName = "";
                String fullPath = mStandardAddressBean.getFullPath();
                if (fullPath.contains("/")) {
                    String[] split = fullPath.split("/");
                    upName = split[split.length - 2];
                }
                upAddress.setText(upName);
            }
            standardAddressCode.setText(mStandardAddressBean.getCode());
            remark.setText(mStandardAddressBean.getMemo());
            if (StringUtil.isNotNullString(mStandardAddressBean.getGisLocations())) {
                StandardAddressBean.LonAndLat lonAndLat = gson.fromJson(mStandardAddressBean.getGisLocations(), StandardAddressBean.LonAndLat.class);
                mapAddress.setText(String.format(getResources().getString(R.string.lon_lat), lonAndLat.getLng(), lonAndLat.getLat()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showPopup(List<? extends BaseSearchPopupBean> listBeans) {
        try {
            if (listBeans != null && listBeans.size() > 0) {
                KeybordUtil.hideKeyboard(this);
                if (tierList == null || tierList.size() <= 0) {
                    tierList = listBeans;
                }
                if (searchPopup == null) {
                    searchPopup = new SearchPopup(this);
                    searchPopup.setPopupClickListener(new SearchPopup.PopupClickListener() {
                        @Override
                        public void onSearchBtnClick(String keyword) {
                        }

                        @Override
                        public void onLoadMore(String keyword) {
                            searchPopup.setLoadMoreViewHide(true);
                        }

                        @Override
                        public void onItemClick(BaseSearchPopupBean bean) {
                            try {
                                if (bean == null) {
                                    ArmsUtils.makeText("选中信息为空");
                                    return;
                                }
                                selectStandardAddress.setText(StringUtil.setText(bean.getName()));
                                addressTypeId = bean.getId();
                                mStandardAddressBean.setStandardAddressTypeId(addressTypeId);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                if (!searchPopup.isShowing()) {
                    searchPopup.showPopupWindow();
                }
                searchPopup.setRecyclerViewData(listBeans, false, true);
            } else {
                ArmsUtils.makeText("未获取到您需要的信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setGrid(String pointsInJSON) {
        this.pointsInJSON = pointsInJSON;
    }
}
