package com.weique.overhaul.v2.mvp.ui.fragment.economicmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EnumConstant;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.di.component.DaggerEconomicManagementBaseInfoComponent;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementBaseInfoContract;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.presenter.EconomicManagementBaseInfoPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.CommentCurrencyAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonRecyclerPopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.app.common.ARouerConstant.ID;
import static com.weique.overhaul.v2.app.common.Constant.ADDRESS_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.IMG_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.LARGE_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.MORE_IMG_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.MORE_URL_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.NUMBER_EDIT_ITEM;


/**
 * ================================================
 * ================================================
 *
 * @author Administrator
 */
public class EconomicManagementBaseInfoFragment extends BaseLazyLoadFragment<EconomicManagementBaseInfoPresenter> implements EconomicManagementBaseInfoContract.View {

    private static final String BEHAVIOR = "behavior";
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    /**
     * 初始化  选择框
     */
    private List<NameAndIdBean> typeList = new ArrayList<>();
    private CommonRecyclerPopupWindow<NameAndIdBean> commonRecyclerPopupWindow;
    private CommonRecyclerPopupAdapter commonRecyclerPopupAdapter;

    private int type = 0;

    private String id = "";

    /**
     * 通用adapter
     */
    @Inject
    CommentCurrencyAdapter mAdapter;

    public static EconomicManagementBaseInfoFragment newInstance(@EnumConstant.EconomicManagementEnum int behavior, String id) {
        EconomicManagementBaseInfoFragment fragment = new EconomicManagementBaseInfoFragment();
        Bundle args = new Bundle();
        args.putInt(BEHAVIOR, behavior);
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerEconomicManagementBaseInfoComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_economic_management_base_info, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            type = getArguments().getInt(BEHAVIOR);
            id = getArguments().getString(ID);
            initRecyclerAdapter();
            switch (type) {
                case EnumConstant.EconomicManagementEnum.PROJECT_INFO_BASE:
                    /*
                     * 企业
                     */
                    mPresenter.getEntinfoById(id);
                    break;
                case EnumConstant.EconomicManagementEnum.FIRM_INFO_BASE:
                    /*
                     *项目
                     */
                    assert mPresenter != null;
                    mPresenter.getInfoById(id);
                    break;
                case EnumConstant.EconomicManagementEnum.DETAILS_OF_THE_MATTER:
                    assert mPresenter != null;
                    mPresenter.getMatterById(id);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化 信息类型
     */
    private void initRecyclerAdapter() {
        try {

            ArmsUtils.configRecyclerView(rvContent, new LinearLayoutManager(getActivity()));
            rvContent.setClipToPadding(false);
            rvContent.setAdapter(mAdapter);

            List<BasicInformationBean.RecordsBean> list = new ArrayList<>();
            switch (type) {
                case EnumConstant.EconomicManagementEnum.PROJECT_INFO_BASE:
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "企业名称", "Name", "请填写企业名称(必填)"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "统一社会信用代码", "Code", "请填写信用代码(必填)"));
                    list.add(new BasicInformationBean.RecordsBean(IMG_ITEM, "公司图片", "PhotoUrls", ""));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "法定代表人", "LegalMan", "请填写法定代表人(必填)"));
                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "电话", "Tel", "请填写电话"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "成立日期", "FoundDate", "请填写成立日期"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "注册地址", "RegAddress", "请填写注册地址"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "经营地址", "OperAddress", "请选择经营地址"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "所属行业", "EntBusinessTypeName", "请选择所属行业"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "企业登记注册类型", "EnumEntRegType", "请选择企业登记注册类型"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "五上企业", "EnumEntFUpType", "请选择五上企业"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "生产经营方式", "EnumEntManageType", "请选择生产经营方式"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "行政隶属关系", "EnumEntAdminType", "请选择行政隶属关系"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "企业规模", "EnumEntScaleType", "请选择企业规模"));
                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "从业人员(人数)", "EmployeeCount", "请填写从业人员"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "包挂领导", "Charger", "请填写包挂领导"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "信用评价", "CreditEvaluates", "请填写信用评价"));
                    break;
                case EnumConstant.EconomicManagementEnum.FIRM_INFO_BASE:
                    /*
                     *项目
                     */
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "项目名称", "Name", "请填写项目名称"));
                    list.add(new BasicInformationBean.RecordsBean(IMG_ITEM, "项目图片", "PhotoUrls", ""));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "项目位置", "Address", "请填写项目位置"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "开工日期", "StartDate", "请填写开工日期"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "完工日期", "FinishDate", "请填写完工日期"));
                    list.add(new BasicInformationBean.RecordsBean(LARGE_EDIT_ITEM, "项目介绍", "Desc", "请选择经营地址"));
                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "项目总投资(万元)", "TotalInvestment", "请填写项目总投资"));
                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "当年计划投资额(万元)", "PlotInvestment", "请填写计划投资"));
                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "当年已完成投资(万元)", "Invested", "请填写已完成投资"));
                    break;
                case EnumConstant.EconomicManagementEnum.DETAILS_OF_THE_MATTER:
                    /*
                     * 事项
                     */
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "事项编号", "Code", "请填写项目名称"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "事项标题", "Title", "请填写事项标题"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "事项等级", "EnumEventLevelStr", "请选择事项等级"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "事项处置状态", "EnumOrderStatusStr", "请填写事项等级"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "事项信息来源", "EnumSourceStr", "请选择事项信息来源"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "创建日期", "CreateTimeStr", "请填写创建日期"));
                    list.add(new BasicInformationBean.RecordsBean(LARGE_EDIT_ITEM, "事项详情", "Content", "请填写事项详情"));
                    list.add(new BasicInformationBean.RecordsBean(ADDRESS_ITEM, "发生位置", "LocationInJson", "请选择发生位置"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "发生地址", "EventAddress", "请填写发生地址"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "事项信息来源", "EnumSourceStr", "请填写事项信息来源"));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "上报人姓名", "CreateEmName", "请填写上报人姓名"));
                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "上报人电话", "CreateEmTel", "请填写上报人电话"));
                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "上报人身份证", "CreateEmSID", "请填写上报人身份证"));
                    list.add(new BasicInformationBean.RecordsBean(MORE_IMG_ITEM, "现场照片", "ImgsInJson", "增加图片"));
                    list.add(new BasicInformationBean.RecordsBean(MORE_URL_ITEM, "现场视频", "VideoUrl", "增加视频"));
                    break;
                default:
                    break;
            }


            for (BasicInformationBean.RecordsBean bean : list) {
                bean.setEdit(false);
            }
            mAdapter.setNewData(list);

            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {

                Timber.e(adapter.getData().get(position).toString());
                BasicInformationBean.RecordsBean bean = (BasicInformationBean.RecordsBean) adapter.getData().get(position);

//                switch (view.getId()) {
//                    /*
//                     *  点击编辑
//                     */
//                    case R.id.et_name:
//
//                        try {
//
//                            switch (bean.getParamtype()) {
//                                case DATA_ITEM:
//                                    initTimePicker(bean);
//                                    break;
//                                case SELECT_ITEM:
//                                    initSelectAll(bean);
//                                    break;
//                                default:
//                                    break;
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                        break;
//                        /*
//                         图片添加
//                         */
//                    case R.id.rl_img_add:
////                        if (ObjectUtils.isEmpty(bean.getValue())) {
////                            assert mPresenter != null;
////                            mPresenter.getPermission();
////                            imgBean = bean;
////                        }
//                        break;
//                        /*
//                         地址添加
//                         */
//                    case R.id.iv_address:
////                        imgBean = bean;
////                        ARouter.getInstance().build(RouterHub.APP_NEWMAPACTIVITY)
////                                .withString(ARouerConstant.SOURCE, RouterHub.APP_ENTERPRISE_INFORMATION_COLLECTION)
////                                .navigation();
//
//                        break;
//                    default:
//                        break;
//                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 初始化时间选择器
     */
    private void initTimePicker(final BasicInformationBean.RecordsBean bean) {

        TimePickerView startTimePickerView;
        startTimePickerView = PickerViewUtil.selectPickerTimeToday(getActivity(), Constant.YMD, (date, v) -> {
            try {
                //开始时间
                SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
                /*
                 * 更新数据 ，不会破坏原有的布局
                 */
                bean.setValue(format.format(date));
                mAdapter.setNewData(mAdapter.getData());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        startTimePickerView.show();

    }


    private void initSelectAll(final BasicInformationBean.RecordsBean bean) {
        Timber.e("asd");
        typeList.clear();
        typeList.add(new NameAndIdBean("", "教育"));
        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));
        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));


        if (commonRecyclerPopupAdapter == null) {
            commonRecyclerPopupAdapter = new CommonRecyclerPopupAdapter();
        }
        commonRecyclerPopupWindow = new CommonRecyclerPopupWindow<>(getContext(),
                commonRecyclerPopupAdapter, new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                NameAndIdBean nameAndIdBean = (NameAndIdBean) adapter.getItem(position);
            /*
              更新數據
             */
                if (ObjectUtils.isNotEmpty(nameAndIdBean)) {
                    bean.setValue(nameAndIdBean.getName());
                    bean.setSelectValue(nameAndIdBean.getId());
                    mAdapter.setNewData(mAdapter.getData());
                }
            /*
              关闭选择框
             */
                commonRecyclerPopupWindow.dismiss();
            }
        });

        if (!commonRecyclerPopupWindow.isShowing()) {
            commonRecyclerPopupWindow.showPopupWindow();
        }

        commonRecyclerPopupWindow.setNewData(typeList);
    }


    /**
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

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

    }

    @Override
    protected void lazyLoadData() {

    }


    @Override
    public CommentCurrencyAdapter getAdapter() {
        return mAdapter;
    }
}
