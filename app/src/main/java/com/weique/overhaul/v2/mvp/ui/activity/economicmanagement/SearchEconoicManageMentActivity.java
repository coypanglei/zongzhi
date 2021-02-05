package com.weique.overhaul.v2.mvp.ui.activity.economicmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.KeyboardUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EnumConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerSearchEconoicManageMentComponent;
import com.weique.overhaul.v2.mvp.contract.SearchEconoicManageMentContract;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressAreaBean;
import com.weique.overhaul.v2.mvp.presenter.SearchEconoicManageMentPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.SearchEconoicAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonTreeListPopup;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.app.common.Constant.ADDRESS_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.DATA_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.IMG_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.LARGE_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.MORE_IMG_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.MORE_URL_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.NUMBER_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.SELECT_ITEM;


/**
 * ================================================
 * ================================================
 *
 * @author Administrator
 */
@Route(path = RouterHub.APP_PROJECT_COLLECTION_SEARCH, name = "项目采集搜索")
public class SearchEconoicManageMentActivity extends BaseActivity<SearchEconoicManageMentPresenter>
        implements SearchEconoicManageMentContract.View {


    @Inject
    SearchEconoicAdapter<NameAndIdBean> mAdapter;
    @BindView(R.id.rv_content)
    RecyclerView recycler;
    @Inject
    HorizontalDividerItemDecoration decoration;
    @BindView(R.id.input_et)
    EditText inputEt;
    @BindView(R.id.screen_text)
    TextView screenText;
    @BindView(R.id.clean_text)
    ImageView cleanText;
    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 企业
     */
    public static final int IS_ENTERPRISE = 1;
    /**
     * 项目
     */
    public static final int IS_PROJECT = 2;
    /**
     * 上个界面跳转名称
     */
    @Autowired(name = ARouerConstant.TYPE)
    int type;

    /**
     * 上个界面跳转名称
     */
    @Autowired(name = ARouerConstant.TITLE)
    String title = "企业列表";

    @BindView(R.id.swipe_refresh)
    VerticalSwipeRefreshLayout swipeRefreshLayout;

    private CommonTreeListPopup<StandardAddressAreaBean.DepartmentBean> popup;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchEconoicManageMentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search_econoic_manage_ment;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle(title);
        initView();
        initEditText();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            mAdapter.loadMoreEnd(true);
        } else {
            mAdapter.loadMoreComplete();
        }
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
        finish();
    }


    private void initView() {
        try {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                getEnterpriseOrObjectList(true, false, keyword);
            });
            ArmsUtils.configRecyclerView(recycler, new LinearLayoutManager(this));
            recycler.addItemDecoration(decoration);
            recycler.setAdapter(mAdapter);
            mAdapter.setOnLoadMoreListener(() -> getEnterpriseOrObjectList(false, true, keyword), recycler);
            mAdapter.setEmptyView(R.layout.null_content_layout, recycler);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                        NameAndIdBean nameAndIdBean = (NameAndIdBean) adapter.getItem(position);
                        String content = "";
                        switch (view.getId()) {
                            case R.id.name:

                                if (type == 1) {

                                    ARouter.getInstance()
                                            .build(RouterHub.APP_FIRM_MANAGE_ACTIVITY)
                                            .withString(ARouerConstant.ID, nameAndIdBean.getId())
                                            .navigation();
                                } else {
                                    ARouter.getInstance()
                                            .build(RouterHub.APP_PM_ACTIVITY)
                                            .withString(ARouerConstant.ID, nameAndIdBean.getId())
                                            .navigation();
                                }
                                break;
                            case R.id.iv_delete:

                                if (type == 1) {
                                    content = "确定删除企业信息吗？";
                                } else {
                                    content = "确定删除项目信息吗？";
                                }
                                new CommonDialog.Builder(this).setTitle("提示")
                                        .setContent(content)
                                        .setPositiveButton("确定", (v, commonDialog) -> {
                                            mPresenter.delete(nameAndIdBean, type, position);
                                        }).create().show();
                                break;
                            case R.id.iv_alert:
                                List<BasicInformationBean.RecordsBean> list =new ArrayList<>();
                                CommonCollectBean commonCollectBean = new CommonCollectBean();
                                Map<String, Object> map = new HashMap<>();
                                map.put("type", type);
                                map.put("EntInfoId", nameAndIdBean.getId());
                                map.put("MProjectId", nameAndIdBean.getId());
                                map.put("Id", nameAndIdBean.getId());
                                if (type == 1) {
                                    content = "企业信息";
                                    commonCollectBean.setPath("/api/EcoDev/EditEntInfo");
                                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "企业名称", "Name", "请填写企业名称(必填)", true));
                                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "统一社会信用代码", "Code", "请填写信用代码(必填)", true));
                                    list.add(new BasicInformationBean.RecordsBean(IMG_ITEM, "公司图片", "PhotoUrls", "",true));
                                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "法定代表人", "LegalMan", "请填写法定代表人(必填)", true));
                                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "电话", "Tel", "请填写电话(必填)", true));
                                    list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "成立日期", "FoundDate", "请填写成立日期"));
                                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "注册地址", "RegAddress", "请填写注册地址(必填)", true));
                                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "经营地址", "OperAddress", "请选择经营地址(必填)", true));
                                    list.add(new BasicInformationBean.RecordsBean(ADDRESS_ITEM, "经营位置", "PointInJSON", "请选择经营位置(必填)", true));
                                    BasicInformationBean.RecordsBean bean =new BasicInformationBean.RecordsBean(SELECT_ITEM, "所属行业", "EntBusinessTypeName", "请选择所属行业");
                                    list.add(bean);

                                    list.add(new BasicInformationBean.RecordsBean(SELECT_ITEM, "企业登记注册类型", "EnumEntRegType", "请选择企业登记注册类型"));
                                    list.add(new BasicInformationBean.RecordsBean(SELECT_ITEM, "五上企业", "EnumEntFUpType", "请选择五上企业"));
                                    list.add(new BasicInformationBean.RecordsBean(SELECT_ITEM, "生产经营方式", "EnumEntManageType", "请选择生产经营方式"));
                                    list.add(new BasicInformationBean.RecordsBean(SELECT_ITEM, "行政隶属关系", "EnumEntAdminType", "请选择行政隶属关系"));
                                    list.add(new BasicInformationBean.RecordsBean(SELECT_ITEM, "企业规模", "EnumEntScaleType", "请选择企业规模"));
                                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "从业人员（人数）", "EmployeeCount", "请填写从业人员"));
                                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "包挂领导", "Charger", "请填写包挂领导"));
                                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "信用评价", "CreditEvaluates", "请填写信用评价"));

                                } else {
                                    content = "项目信息";
                                    commonCollectBean.setPath("/api/EcoDev/EditMProject");
                                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "项目名称", "Name", "请填写项目名称(必填)", true));
                                    list.add(new BasicInformationBean.RecordsBean(IMG_ITEM, "项目图片", "PhotoUrls", "",true));
                                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "项目地址", "Address", "请填写项目地址", true));
                                    list.add(new BasicInformationBean.RecordsBean(ADDRESS_ITEM, "项目位置", "PointInJSON", "请选择项目位置(必填)", true));
                                    list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "开工日期", "StartDate", "请填写开工日期"));
                                    list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "完工日期", "FinishDate", "请填写完工日期"));
                                    list.add(new BasicInformationBean.RecordsBean(LARGE_EDIT_ITEM, "项目介绍", "Desc", "请填写项目介绍"));
                                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "项目总投资(万元)", "TotalInvestment", "请填写项目总投资"));
                                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "当年计划投资额(万元)", "PlotInvestment", "请填写计划投资"));
                                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "当年已完成投资(万元)", "Invested", "请填写已完成投资"));

                                }
                                commonCollectBean.setList(list);
                                commonCollectBean.setTitle(content);
                                commonCollectBean.setMap(map);
                                commonCollectBean.setType(Constant.CommonCollectionEnum.COMMON_COLLECTION_EDIT);
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
            );
            getEnterpriseOrObjectList(true, false, keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取企业 或项目 列表
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore  是否加载更多
     * @param keyword       keyword  关键字
     */
    private void getEnterpriseOrObjectList(boolean pullToRefresh, boolean isLoadMore, String keyword) {
        String did = "";
        if (screenText.getTag() != null) {
            did = screenText.getTag().toString();
        }
        if (type == IS_ENTERPRISE) {
            mPresenter.getEnterpriseList(did, pullToRefresh, isLoadMore, keyword);
        } else {
            mPresenter.getProjectList(did, pullToRefresh, isLoadMore, keyword);
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setNewData(List<NameAndIdBean> newData, boolean isLoadMore) {
        if (isLoadMore) {
            mAdapter.addData(newData);
        } else {
            mAdapter.setNewData(newData);
        }
    }

    @Override
    public void showAreaListPopup(List<StandardAddressAreaBean.DepartmentBean> department, boolean isChild) {
        try {
            if (popup == null) {
                popup = new CommonTreeListPopup<>(this);
                popup.setListener(new CommonTreeListPopup.
                        CommonTreeListPopupListener<StandardAddressAreaBean.DepartmentBean>() {
                    @Override
                    public void onItemClickGetSubset(String elementId) {
                        mPresenter.getAreaList(elementId, true);
                    }

                    @Override
                    public void onItemClickInput(StandardAddressAreaBean.DepartmentBean da) {
                        screenText.setVisibility(View.VISIBLE);
                        screenText.setText(da.getName());
                        screenText.setTag(da.getId());
                        getEnterpriseOrObjectList(true, false, keyword);
                        popup.dismiss();
                    }

                    @Override
                    public void onSearchByKeyword(String keyWord) {

                    }

                    @Override
                    public void onLoadMore() {

                    }
                });
            }
            if (isChild) {
                popup.setDataTree(department);
            } else {
                popup.setData(department, false);
            }
            if (!popup.isShowing()) {
                popup.showPopupWindow();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteInfo(NameAndIdBean nameAndIdBean, int position) {
        getEnterpriseOrObjectList(true, false, keyword);
    }


    @OnClick({R.id.screen_text, R.id.screen, R.id.clean_text})
    public void onViewClicked(View v) {
        try {
            switch (v.getId()) {
                case R.id.screen:
                    KeyboardUtils.hideSoftInput(this);
                    mPresenter.getAreaList("", false);
                    break;
                case R.id.clean_text:
                    inputEt.setText("");
                    keyword = "";
                    getEnterpriseOrObjectList(true, false, keyword);
                    break;
                case R.id.screen_text:
                    screenText.setVisibility(View.GONE);
                    screenText.setTag("");
                    getEnterpriseOrObjectList(true, false, keyword);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始话edit text
     */
    private void initEditText() {
        try {
            inputEt.setFocusable(true);
            inputEt.setFocusableInTouchMode(true);
            inputEt.requestFocus();
            inputEt.setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        getEnterpriseOrObjectList(true, false, keyword);
                    }
                    return true;
                }
                return false;
            });
            inputEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    keyword = s.toString();
                    if (StringUtil.isNullString(keyword)) {
                        cleanText.setVisibility(View.GONE);
                    } else {
                        if (cleanText.getVisibility() == View.GONE) {
                            cleanText.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
