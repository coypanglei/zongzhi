package com.weique.overhaul.v2.mvp.ui.activity.standardaddress;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerStandardAddressLookComponent;
import com.weique.overhaul.v2.mvp.contract.StandardAddressLookContract;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.ScanResultBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.StandardAddressLookPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.fragment.standardaddress.InformationListForAddressDetailFragment;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.weique.overhaul.v2.mvp.ui.popupwindow.ElasticListPopup;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 查看地址详情界面
 * <p>
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_STANDARDADDRESSLOOKACTIVITY)
public class StandardAddressLookActivity extends BaseActivity<StandardAddressLookPresenter> implements StandardAddressLookContract.View {

    /**
     * 查询  或是 修改时  查询详情的did @detailId
     */
    public static final String DETAIL_ID = "DETAIL_ID";
    @Autowired(name = DETAIL_ID)
    String detailId;
    @Autowired(name = ARouerConstant.DEPARTMENT_ID)
    String departmentId;
    @Inject
    Gson gson;
    @BindView(R.id.standard_name)
    TextView standardName;
    @BindView(R.id.delete_layout)
    LinearLayout deleteLayout;
    @BindView(R.id.alert_layout)
    LinearLayout alertLayout;
    @BindView(R.id.map_address)
    TextView mapAddress;
    @BindView(R.id.location_layout)
    LinearLayout locationLayout;
    @BindView(R.id.up_address)
    TextView upAddress;
    @BindView(R.id.standard_address_code)
    TextView standardAddressCode;
    @BindView(R.id.remark)
    TextView remark;
    @BindView(R.id.select_map_location)
    LinearLayout selectMapLocation;
    @BindView(R.id.tl_2)
    SlidingTabLayout tl2;
    @BindView(R.id.view_page)
    ViewPager viewPage;
    @BindView(R.id.collect_info)
    Button collectInfo;
    @BindView(R.id.tier_text)
    TextView tierText;
    @BindView(R.id.tier_layout)
    LinearLayout tierLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    private StandardAddressBean mStandardAddressBean;
    private ArrayList<BaseLazyLoadFragment> mFragments;
    private MyPagerAdapter mAdapter;
    private List<String> strings;
    private ElasticListPopup popup;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStandardAddressLookComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_standard_address_look;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            setTitle("标准地址详情");
            AccessControlUtil.controlByLevelCommunity(deleteLayout, alertLayout, collectInfo);
            mPresenter.getAddressDetail(detailId);
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
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.delete_layout, R.id.alert_layout, R.id.collect_info})
    public void onClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.alert_layout:
                    ARouter.getInstance().build(RouterHub.APP_STANDARDADDRESSADDALERTACTIVITY)
                            .withInt(StandardAddressAddAlertActivity.STATE, EventBusConstant.ALERT)
                            .withString(Constant.DEPARTMENT_ID, departmentId)
                            .withParcelable(StandardAddressAddAlertActivity.STANDARD_ADDRESSB_BEAN, mStandardAddressBean)
                            .withParcelable(StandardAddressAddAlertActivity.STANDARD_ADDRESSB_BEAN_DEPARTMENT_BEAN, mStandardAddressBean.getDepartment())
                            .navigation();
                    break;
                case R.id.location_layout:
                    String pointsInJson = mStandardAddressBean.getGisLocations();
                    if (StringUtil.isNotNullString(pointsInJson)) {
                        mPresenter.getDepartment();
                    } else {
                        ArmsUtils.makeText("未发现定位地址");
                    }
                    break;
                case R.id.delete_layout:
                    new CommonDialog.Builder(this).setTitle("提示")
                            .setContent("确认删除地址选项")
                            .setPositiveButton("删除", (v1, commonDialog) -> {
                                mPresenter.deleteItem(detailId);
                            }).create().show();
                    break;
                case R.id.collect_info:
                    if (mStandardAddressBean != null) {
                        mPresenter.getInformationTypeStartersList(mStandardAddressBean.getId(), true);
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新 信息
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_STANDARDADDRESSLOOKACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                //修改的来源是 来自 标准地址的修改
                case EventBusConstant.ALERT:
                    if (eventBusBean.getData() instanceof StandardAddressBean) {
                        mStandardAddressBean = (StandardAddressBean) eventBusBean.getData();
                        //重新刷新获取数据
                        mPresenter.getAddressDetail(detailId);
                    }
                    break;
                //这里是来自  资源采集 中的回调  新增成功  更新列表数据
                case EventBusConstant.ADD:
                case EventBusConstant.DELETE:
                    mPresenter.getInformationTypeStartersList(mStandardAddressBean.getId(), false);
                    if (mFragments != null && mFragments.size() > 0) {
                        for (BaseLazyLoadFragment mFragment : mFragments) {
                            mFragment.setDataLoaded(false);
                        }
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setData(StandardAddressBean standardAddressBean) {
        try {
            mStandardAddressBean = standardAddressBean;
            standardName.setText(StringUtil.setText(mStandardAddressBean.getName()));
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
            remark.setText((mStandardAddressBean.getMemo()));
            if (mStandardAddressBean != null) {
                mPresenter.getInformationTypeStartersList(mStandardAddressBean.getId(), false);
            }
            try {
                if (StringUtil.isNotNullString(mStandardAddressBean.getGisLocations())) {
                    StandardAddressBean.LonAndLat lonAndLat = gson.fromJson(mStandardAddressBean.getGisLocations(), StandardAddressBean.LonAndLat.class);
                    mapAddress.setText(String.format(getResources().getString(R.string.lon_lat), lonAndLat.getLng(), lonAndLat.getLat()));
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
            StandardAddressBean.StandardAddressTypeBean standardAddressType = mStandardAddressBean.getStandardAddressType();
            tierText.setText(standardAddressType.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化列表 标准地址下的
     *
     * @param informationType informationType
     */
    private void initViewPage(List<InformationTypeOneSecondBean.ElementTypeListBean> informationType) {
        try {
//            if (mAdapter == null) {
            strings = new ArrayList<>();
            mFragments = new ArrayList<>();
            //移动端  订单状态 从 待接单开始
            for (InformationTypeOneSecondBean.ElementTypeListBean typeBean : informationType) {
                if (typeBean.isHasResourece()) {
                    mFragments.add(InformationListForAddressDetailFragment.newInstance(mStandardAddressBean.getId(), typeBean.getId()));
                    if (StringUtil.isNotNullString(typeBean.getAnotherName())) {
                        strings.add(typeBean.getAnotherName());
                    } else {
                        strings.add(typeBean.getName());
                    }
                }
            }
            if (strings.size() <= 0) {
                return;
            }
            String[] array = this.strings.toArray(new String[this.strings.size()]);
            mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, array);
            viewPage.setAdapter(mAdapter);
            tl2.setViewPager(viewPage, array);
            viewPage.setOffscreenPageLimit(array.length);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gotoMapTv(GridInformationBean gridInformationBean) {
        try {
            if (gridInformationBean == null || StringUtil.isNullString(gridInformationBean.getPointsInJSON())) {
                ArmsUtils.makeText("未获取到网格信息");
                return;
            }
            ARouter.getInstance().build(RouterHub.APP_MAPACTIVITY)
                    .withString(MapActivity.POINTS_IN_JSON, gridInformationBean.getPointsInJSON())
                    .withString(MapActivity.LONANDLAT, mStandardAddressBean.getGisLocations())
                    .withString(ARouerConstant.SOURCE, RouterHub.APP_STANDARDADDRESSLOOKACTIVITY)
                    .navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setInfoData(ScanResultBean itemBean) {

    }

    /**
     * 设置信息类型数据
     *
     * @param beans beans
     */
    @Override
    public void setInformationTypeStarters(
            List<InformationTypeOneSecondBean.ElementTypeListBean> beans, boolean showPopup) {
        try {
            if (beans != null && beans.size() > 0) {
                if (popup == null) {
                    popup = new ElasticListPopup(this);
                    popup.setListener(new ElasticListPopup.ElasticListPopupListener() {
                        @Override
                        public void onItemClickGetSubset(String elementId) {
                            mPresenter.getElementTypes(elementId);
                        }

                        @Override
                        public void onInputClick(String elementId, String elementName, String dataStructureInJson) {
                            try {
                                mPresenter.getGetNewGuidForApp(departmentId, elementId, mStandardAddressBean, dataStructureInJson, elementName);
                                if (popup != null && popup.isShowing()) {
                                    popup.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
                if (!showPopup) {
                    initViewPage(beans);
                } else {
                    if (!popup.isShowing()) {
                        popup.showPopupWindow();
                    }
                    popup.setData(beans);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setElementList(InformationTypeOneSecondBean beans) {
        try {
            if (popup != null && popup.isShowing()) {
                if (beans != null && beans.getElementTypeList() != null) {
                    popup.setDataTree(beans.getElementTypeList());
                } else {
                    ArmsUtils.makeText("无下级类型");
                }
            } else {
                ArmsUtils.makeText("无下级类型");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * fragment 调用
     *
     * @return departmentId
     */
    public String getDepartmentId() {
        return departmentId;
    }
}
