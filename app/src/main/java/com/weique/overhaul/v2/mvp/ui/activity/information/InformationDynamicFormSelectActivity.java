package com.weique.overhaul.v2.mvp.ui.activity.information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerInformationDynamicFormSelectComponent;
import com.weique.overhaul.v2.mvp.contract.InformationDynamicFormSelectContract;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.InformationDynamicFormSelectPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.DynamicFormSelectAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Iterator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 动态查询表单
 * <p>
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
public class InformationDynamicFormSelectActivity extends BaseActivity<InformationDynamicFormSelectPresenter>
        implements InformationDynamicFormSelectContract.View, View.OnClickListener {
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    DynamicFormSelectAdapter formSelectAdapter;
    @Inject
    Gson gson;
    /**
     * 动态表单json
     */
    public static final String DYNAMIC_FORM_JSON = "dynamicFormJson";
    @Autowired(name = DYNAMIC_FORM_JSON)
    String dynamicFormJson;
    /**
     * 详情id
     */
    @Autowired(name = ARouerConstant.ID)
    String id;
    /**
     * title
     */
    @Autowired(name = ARouerConstant.TITLE)
    String title;
    /**
     * 网格id
     */
    @Autowired(name = ARouerConstant.DEPARTMENT_ID)
    String departmentId;
    /**
     * TYPE 区分上级人地事物 的类型id
     */
    public static final String TYPE_ID = "TYPE_ID";
    @Autowired(name = TYPE_ID)
    String typeId;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.alert_layout)
    LinearLayout alertLayout;
    @BindView(R.id.delete_layout)
    LinearLayout deleteLayout;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    /**
     * 个人信息详情
     */
    private InformationDetailBean informationDetailBean;
    private LinearLayout locationLayout;
    private TextView map_address;
    private TextView up_address;
    private InformationDynamicFormSelectBean informationDynamicFormSelectBean;
    private String detailJson;
    private View headView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInformationDynamicFormSelectComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_information_dynamic_form_select;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle(title);

        if (InformationCollectionActivity.mType != InformationCollectionActivity.GATHER) {
            alertLayout.setVisibility(View.GONE);
            deleteLayout.setVisibility(View.GONE);
        } else {
            AccessControlUtil.controlByLevelCommunity(alertLayout, deleteLayout);
        }
        mPresenter.getDetailInfoById(id, typeId);
        recycler.scrollToPosition(0);
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
    public Context getContext() {
        return this;
    }

    @Override
    public void setData(Object o) {
        //初始化动态表单数据  这里是由 动态表单和 具体人事物。。 信息 拼合而成
        try {
            if (StringUtil.isNullString(o.toString())) {
                ArmsUtils.makeText("未查询到详情信息");
                alertLayout.setVisibility(View.GONE);
                deleteLayout.setVisibility(View.GONE);
                return;
            }
            alertLayout.setVisibility(View.VISIBLE);
            deleteLayout.setVisibility(View.VISIBLE);
            //动态表单数据
            informationDynamicFormSelectBean = gson.fromJson(dynamicFormJson, InformationDynamicFormSelectBean.class);
            //用户详情数据
            detailJson = gson.toJson(o);

            initDynamicFormData();

            informationDetailBean = gson.fromJson(detailJson, InformationDetailBean.class);
            informationDetailBean.setUserId(UserInfoUtil.getUserInfo().getUid());
            informationDetailBean.setElementId(informationDetailBean.getId());
            /**
             *  如果不是从外界传过来的departmentId ，就使用接口获取的departmentId
             */
            if (ObjectUtils.isEmpty(departmentId)) {
                departmentId = informationDetailBean.getDepartmentId();
            }
            initHeadView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始话 动态表单数据
     */
    private void initDynamicFormData() {
        try {
            ArrayList<InformationDynamicFormSelectBean.StructureInJsonBean> structureInJsonBeans = informationDynamicFormSelectBean.getStructureInJson();
            JSONObject jsonObject = new JSONObject(detailJson);
            for (InformationDynamicFormSelectBean.StructureInJsonBean bean : structureInJsonBeans) {
                bean.setDefaultVal(jsonObject.getString(bean.getPropertyName()));
            }
            if (recycler.getAdapter() == null) {
                ArmsUtils.configRecyclerView(recycler, linearLayoutManager);
                recycler.setClipToPadding(false);
                formSelectAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                        .colorResId(R.color.gray_eee)
                        .sizeResId(R.dimen.dp_1)
                        .build());
                recycler.setAdapter(formSelectAdapter);
            }
            setAdapterNewDate(structureInJsonBeans);
        } catch (JSONException e) {
            e.printStackTrace();
            ArmsUtils.makeText( "信息解析错误 请联系服务人员");
        }
    }

    /**
     * 删除成功
     */
    @Override
    public void deleteSuccess() {
        try {
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.DELETE), RouterHub.APP_INFORMATIONTYPESECONDACTIVITY);
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.DELETE), RouterHub.APP_STANDARDADDRESSLOOKACTIVITY);
            //删除成功更新 上两个界面的的  数量信息
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.DELETE), RouterHub.APP_INFORMATIONTYPEFIRSTACTIVITY);
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.DELETE), RouterHub.APP_INFORMATIONCOLLECTIONACTIVITY);
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.DELETE), RouterHub.APP_INFORMATIONLISTFORADDRESSDETAILFRAGMENT);
            finish();
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
                    .withString(MapActivity.LONANDLAT, informationDetailBean.getPointsInJson())
                    .withString(ARouerConstant.SOURCE, RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
                    .navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加头布局
     */
    private void initHeadView() {
        try {
            if (headView == null) {
                headView = View.inflate(this, R.layout.dynamic_form_select_head_view, null);
                map_address = headView.findViewById(R.id.map_address);
                up_address = headView.findViewById(R.id.up_address);
                locationLayout = headView.findViewById(R.id.location_layout);
                locationLayout.setOnClickListener(this);
                formSelectAdapter.addHeaderView(headView);
            }
            up_address.setText((informationDetailBean.getStandardAddressFullPath()));
            String replaceS = informationDetailBean.getPointsInJson();
            if (StringUtil.isNotNullString(replaceS)) {
                if (informationDetailBean.getPointsInJson() != null && informationDetailBean.getPointsInJson().contains("\\")) {
                    replaceS = replaceS.replace("\\", "");
                }
                StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                        gson.fromJson(replaceS, StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
                if (pathBean != null) {
                    map_address.setText(String.format(getResources().getString(R.string.lon_lat), pathBean.getLng(), pathBean.getLat()));
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 回调 event bus 更新详情信息
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
    public void onEventCallBack(EventBusBean eventBusBean) {
        try {
            if (eventBusBean != null) {
                if (eventBusBean.getData() instanceof InformationDetailBean) {
                    informationDetailBean = (InformationDetailBean) eventBusBean.getData();
                    //用户详情数据
                    initHeadView();
                } else if (eventBusBean.getData() instanceof InformationTypeOneSecondBean.ElementListBean) {
                    InformationTypeOneSecondBean.ElementListBean listBean = (InformationTypeOneSecondBean.ElementListBean) eventBusBean.getData();
                    toolbarTitle.setText(StringUtil.setText(listBean.getName()));
                } else if (eventBusBean.getData() instanceof InformationDynamicFormSelectBean) {
                    informationDynamicFormSelectBean = (InformationDynamicFormSelectBean) eventBusBean.getData();
                    setAdapterNewDate(informationDynamicFormSelectBean.getStructureInJson());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置新的界面数据
     *
     * @param sStructureInJson sStructureInJson
     */
    private void setAdapterNewDate(ArrayList<InformationDynamicFormSelectBean.StructureInJsonBean> sStructureInJson) {
        try {
            ArrayList<InformationDynamicFormSelectBean.StructureInJsonBean> newStructureInJson = new ArrayList<>();
            newStructureInJson.addAll(sStructureInJson);
            if (newStructureInJson != null && newStructureInJson.size() > 0) {
                Iterator<InformationDynamicFormSelectBean.StructureInJsonBean> iterator = newStructureInJson.iterator();
                while (iterator.hasNext()) {
                    try {
                        InformationDynamicFormSelectBean.StructureInJsonBean next = iterator.next();
                        if (StringUtil.isNullString(next.getDefaultVal()) || next.isDisabled()) {
                            iterator.remove();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                formSelectAdapter.setNewData(newStructureInJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.delete_layout, R.id.alert_layout})
    public void onViewClick(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.delete_layout:
                    if (informationDetailBean == null) {
                        ArmsUtils.makeText("获取信息报错");
                        return;
                    }
                    new CommonDialog.Builder(this).setTitle("提示")
                            .setContent("确认删除")
                            .setPositiveButton("删除", (view1, commonDialog) -> {
                                mPresenter.delete(id, StringUtil.setText(informationDetailBean.getElementTypeId()));
                            }).create().show();
                    break;
                case R.id.alert_layout:
                    ARouter.getInstance().build(RouterHub.APP_INFORMATIONDYNAMICFORMAAACTIVITY)
                            .withParcelable(InformationDynamicFormCrudActivity.LIST, informationDynamicFormSelectBean)
                            .withString(ARouerConstant.TITLE, StringUtil.setText(informationDetailBean.getName()))
                            .withString(ARouerConstant.DEPARTMENT_ID, departmentId)
                            .withInt(InformationDynamicFormCrudActivity.TYPE_KEY, EventBusConstant.ALERT)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
                            .withParcelable(InformationDynamicFormCrudActivity.INFORMATIONDETAILBEAN, informationDetailBean)
                            .navigation();

                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_layout:
                String pointsInJson = informationDetailBean.getPointsInJson();
                if (StringUtil.isNotNullString(pointsInJson)) {
                    mPresenter.getDepartment(departmentId);
                } else {
                    ArmsUtils.makeText("未发现定位地址");
                }
                break;
            default:
        }
    }
}
