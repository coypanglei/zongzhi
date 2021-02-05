package com.weique.overhaul.v2.mvp.ui.fragment.economicmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.draw.ImageResDrawFormat;
import com.bin.david.form.data.format.draw.MultiLineDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.google.gson.Gson;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EnumConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerEconomicManagementComponent;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementTableContract;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoItemBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoItemListBean;
import com.weique.overhaul.v2.mvp.model.entity.TimeSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EconomicManagementTablePresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.app.common.ARouerConstant.ID;
import static com.weique.overhaul.v2.app.common.Constant.DATA_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.M;
import static com.weique.overhaul.v2.app.common.Constant.NUMBER_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.SELECT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.Y;
import static com.weique.overhaul.v2.app.common.Constant.YM;


/**
 * ================================================
 * ================================================
 *
 * @author Administrator
 */
public class EconomicManagementTableFragment extends BaseLazyLoadFragment<EconomicManagementTablePresenter> implements EconomicManagementTableContract.View {
    private static final String BEHAVIOR = "behavior";
    @BindView(R.id.table)
    SmartTable table;

    @BindColor(R.color.color_656565)
    int color65656;

    @Inject
    Gson gson;
    @BindView(R.id.ll_package_leader)
    LinearLayout llPackageLeader;
    @BindView(R.id.package_leader_name)
    EditText editName;
    @BindView(R.id.add)
    TextView add;

    private int type = 0;

    private String id = "";

    private CommonCollectBean commonCollectBean;

    List<BasicInformationBean.RecordsBean> list;

    private String path;

    public static EconomicManagementTableFragment newInstance(@EnumConstant.EconomicManagementEnum int behavior, String id) {
        EconomicManagementTableFragment fragment = new EconomicManagementTableFragment();
        Bundle args = new Bundle();
        args.putInt(BEHAVIOR, behavior);
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerEconomicManagementComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_economic_management, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getArguments().getInt(BEHAVIOR);
        id = getArguments().getString(ID);
        mPresenter.getTabledata(id, type);
        commonCollectBean = new CommonCollectBean();
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("EntInfoId", id);
        map.put("MProjectId", id);
        commonCollectBean.setMap(map);
        commonCollectBean.setType(Constant.CommonCollectionEnum.COMMON_COLLECTION_ADD);
        list = new ArrayList<>();

        switch (type) {
            case EnumConstant.EconomicManagementEnum.PM_MANAGE_PROGRESS:
                list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "项目进展描述", "ProgDesc", "请填写项目进展描述"));
                list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "记录日期", "RecordDate", "请填写日期"));
                commonCollectBean.setTitle("项目进展");
                commonCollectBean.setPath("api/EcoDev/EditMProjectsItem");
                break;
            case EnumConstant.EconomicManagementEnum.PM_MANAGE_INSPECT_STATE:
                list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "项目检查情况", "CheckDesc", "请填写项目检查情况"));
                list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "检查日期", "CheckedDate", "请填写检查日期"));
                list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "项目检查人", "Checker", "请填写项目检查人"));
                commonCollectBean.setTitle("检查情况");
                commonCollectBean.setPath("api/EcoDev/EditMProjectsItem");
                break;
            case EnumConstant.EconomicManagementEnum.FIRM_MANAGE_BASE:
                add.setVisibility(View.GONE);
//                        titleName = "项目实况";
                break;
            case EnumConstant.EconomicManagementEnum.FIRM_MANAGE_DEMAND:
                list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "需求事项标题", "Title", "请填写需求事项标题"));
                list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "需求内容", "Content", "请填写需求内容"));
                commonCollectBean.setPath("api/EcoDev/EditEntInfoItem");
                commonCollectBean.setTitle("企业需求");
                break;
            case EnumConstant.EconomicManagementEnum.FIRM_MANAGE_APPROVE:
                list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "审批事项", "ApprovalItem", "请填申请事项"));
                list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "审批日期", "ApprovalDate", "请填写审批时间"));
                list.add(new BasicInformationBean.RecordsBean(SELECT_ITEM, "审批状态", "EnumEntApprovalStatus", "请填写状态"));
                list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "审批结果详情", "Result", "请填写审批结果"));
                commonCollectBean.setPath("api/EcoDev/EditEntInfoItem");
                commonCollectBean.setTitle("审批记录");
                break;
            case EnumConstant.EconomicManagementEnum.FIRM_MANAGE_LAW:
                list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "执法事项", "Item", "请填写执法事项"));
                list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "执法日期", "ExecuteDate", "请填写执法时间"));
                list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "执法单位", "ExecuteUnit", "请填写执法单位"));
                list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "当前阶段", "CurrentStatusDesc", "请填写当前阶段"));
                commonCollectBean.setPath("api/EcoDev/EditEntInfoItem");
                commonCollectBean.setTitle("执法记录");
                break;
            case EnumConstant.EconomicManagementEnum.FIRM_MANAGE_ECONOMICS:
                BasicInformationBean.RecordsBean recordsBean = new BasicInformationBean.RecordsBean(DATA_ITEM, "年份", "Year", "请填写年份");
                recordsBean.setChangeType(gson.toJson(new TimeSelectBean(Y, 30, 0, 0)));
                list.add(recordsBean);
                BasicInformationBean.RecordsBean monthRecordsBean = new BasicInformationBean.RecordsBean(DATA_ITEM, "月份", "Month", "请填写月份");
                monthRecordsBean.setChangeType(gson.toJson(new TimeSelectBean(M, 30, 0, 0)));
                list.add(monthRecordsBean);
                list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "税收（万元）", "TaxAmount", "请填写税收"));
                commonCollectBean.setPath("api/EcoDev/EditEntInfoItem");
                commonCollectBean.setTitle("税收情况");
                break;
            default:
                break;
        }
        if (ObjectUtils.isNotEmpty(list) && list.size() > 0) {
            for (BasicInformationBean.RecordsBean bean : list) {
                bean.setRequire(true);
            }
        }
    }

    /**
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    public void setTable(EntInfoItemListBean bean) {
        String titleName = "";
        try {
            if (ObjectUtils.isNotEmpty(bean.getCharger())) {
                editName.setText(bean.getCharger());
            }
            List<Column> list = new ArrayList<>();
            switch (type) {
                case EnumConstant.EconomicManagementEnum.FIRM_MANAGE_DEMAND:
                    list.add(new Column<>("需求事项标题", "Title"));
                    list.add(new Column<>("需求内容", "Content"));
                    list.get(0).setDrawFormat(new MultiLineDrawFormat(getActivity(), 100));
                    llPackageLeader.setVisibility(View.VISIBLE);
                    titleName = "企业需求";
                    break;

                case EnumConstant.EconomicManagementEnum.FIRM_MANAGE_APPROVE:
                    list.add(new Column<>("审批事项", "ApprovalItem"));
                    list.add(new Column<>("审批日期", "ApprovalDate"));
                    list.add(new Column<>("审批状态", "EnumEntApprovalStatus"));
                    list.add(new Column<>("审批结果详情", "Result"));
                    list.get(0).setDrawFormat(new MultiLineDrawFormat(getActivity(), 100));
                    titleName = "审批记录";
                    break;

                case EnumConstant.EconomicManagementEnum.FIRM_MANAGE_LAW:
                    list.add(new Column<>("执法事项", "Item"));
                    list.add(new Column<>("立案时间", "ExecuteDate"));
                    list.add(new Column<>("处理部门", "ExecuteUnit"));
                    list.add(new Column<>("当前阶段", "CurrentStatusDesc"));
                    list.get(0).setDrawFormat(new MultiLineDrawFormat(getActivity(), 100));
                    titleName = "执法记录";
                    break;
                case EnumConstant.EconomicManagementEnum.FIRM_MANAGE_ECONOMICS:
                    list.add(new Column<>("年份", "Year"));
                    list.add(new Column<>("月份", "Month"));
                    list.add(new Column<>("税收（万元）", "TaxAmount"));
                    titleName = "税收情况";
                    break;
                default:
                    break;
            }
            // 固定第一项 宽度固定
            list.get(0).setAutoMerge(true);
            list.get(0).setFixed(true);
            //表格数据 datas是需要填充的数据
            final TableData<EntInfoItemBean> tableData = new TableData<>(titleName,
                    bean.getList(), list);
            table.getConfig().setShowXSequence(false);
            table.getConfig().setShowYSequence(false);
            table.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R.color.d7d7d7)));
            table.getConfig().setColumnTitleStyle(new FontStyle(30, color65656));
            table.getConfig().setSequenceGridStyle(new LineStyle(1, R.color.d7d7d7));

            table.getConfig().setMinTableWidth(ScreenUtils.getScreenWidth());
            table.getConfig().setColumnTitleVerticalPadding(20);
            table.getConfig().setColumnTitleHorizontalPadding(10);

            tableData.setShowCount(false);
            table.setTableData(tableData);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setProjectTable(List<EntInfoItemBean> bean) {
        String titleName = "";
        try {

            List<Column> list = new ArrayList<>();
            switch (type) {
                case EnumConstant.EconomicManagementEnum.PM_MANAGE_PROGRESS:
                    list.add(new Column<>("项目进展描述", "ProgDesc"));
                    list.add(new Column<>("记录日期", "RecordDate"));
                    list.get(0).setDrawFormat(new MultiLineDrawFormat(getActivity(), 100));
                    titleName = "项目进展";
                    break;

                case EnumConstant.EconomicManagementEnum.PM_MANAGE_INSPECT_STATE:
                    list.add(new Column<>("项目检情况查", "CheckDesc"));
                    list.add(new Column<>("检查日期", "CheckedDate"));
                    list.add(new Column<>("项目检查人", "Checker"));
                    list.get(0).setDrawFormat(new MultiLineDrawFormat(getActivity(), 100));
                    titleName = "检查情况";
                    break;
                case EnumConstant.EconomicManagementEnum.FIRM_MANAGE_BASE:
                    titleName = "项目实况";
                    Column column = new Column<>("查看", "CameraUrl", new ImageResDrawFormat<String>(50, 50) {
                        @Override
                        protected Context getContext() {
                            return getActivity();
                        }

                        @Override
                        protected int getResourceID(String s, String value, int position) {
                            return R.drawable.icon_video_look;
                        }

                    });
                    column.setOnColumnItemClickListener((OnColumnItemClickListener<String>) (column1, value, url, position) -> {
                        if (ObjectUtils.isNotEmpty(url)) {
                            ARouter.getInstance()
                                    .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                    .withString(PictureLookActivity.URL_, url)
                                    .navigation();
                        }
                    });
                    list.add(column);
                    list.add(new Column<>("摄像机URL", "CameraUrl"));
                    list.get(1).setDrawFormat(new MultiLineDrawFormat(getActivity(), 180));
                    break;

                default:
                    break;
            }
            // 固定第一项 宽度固定
            list.get(0).setAutoMerge(true);
            list.get(0).setFixed(true);
            //表格数据 datas是需要填充的数据
            final TableData<EntInfoItemBean> tableData = new TableData<>(titleName,
                    bean, list);
            table.getConfig().setShowXSequence(false);
            table.getConfig().setShowYSequence(false);
            table.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R.color.d7d7d7)));
            table.getConfig().setColumnTitleStyle(new FontStyle(30, color65656));
            table.getConfig().setSequenceGridStyle(new LineStyle(1, R.color.d7d7d7));
            table.getConfig().setMinTableWidth(ScreenUtils.getScreenWidth());
            table.getConfig().setColumnTitleVerticalPadding(20);
            table.getConfig().setColumnTitleHorizontalPadding(10);

            tableData.setShowCount(false);
            table.setTableData(tableData);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新  tab 数据
     */
    @Subscriber(tag = RouterHub.APP_COMMON_COLLECTION)
    private void onEventCallBack(EventBusBean eventBusBean) {
        Timber.e("%s", eventBusBean.getData());
        if (commonCollectBean.getPath().equals(eventBusBean.getData())) {
            assert mPresenter != null;
            mPresenter.getTabledata(id, type);
        }
    }


    @OnClick({R.id.tv_submit, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                String name = editName.getText().toString();
                assert mPresenter != null;
                mPresenter.createCharger(id, name);
                break;
            case R.id.add:
                commonCollectBean.setList(list);
                if (commonCollectBean.getList().size() > 0) {
                    ARouter.getInstance().build(RouterHub.APP_COMMON_COLLECTION).
                            withParcelable(ARouerConstant.COMMON_COLLECTION, commonCollectBean).
                            navigation();
                }
                break;
            default:
                break;
        }
    }
}
