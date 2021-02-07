package com.weique.overhaul.v2.mvp.ui.activity.standardaddress.newactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerStandardAddressOneNewComponent;
import com.weique.overhaul.v2.mvp.contract.StandardAddressOneNewContract;
import com.weique.overhaul.v2.mvp.model.SearchProjectBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseSearchPopupBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.UserGideBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.StandardAddressOneNewPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.activity.standardaddress.StandardAddressAddAlertActivity;
import com.weique.overhaul.v2.mvp.ui.activity.standardaddress.StandardAddressLookActivity;
import com.weique.overhaul.v2.mvp.ui.activity.standardaddress.StandardAddressUpListActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.StandardAddressOneAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonTreeListPopup;
import com.weique.overhaul.v2.mvp.ui.popupwindow.SearchPopup;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_STANDARDADDRESSONENEWACTIVITY)
public class StandardAddressOneNewActivity extends BaseActivity<StandardAddressOneNewPresenter>
        implements StandardAddressOneNewContract.View {

    @BindView(R.id.select_grid)
    TextView selectGrid;
    @BindView(R.id.select_grid_layout)
    LinearLayout selectGridLayout;
    @BindView(R.id.select_standard_address)
    TextView selectTier;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.select_tier_layout)
    LinearLayout selectTierLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.add)
    FloatingActionButton add;
    private String keyWord;

    @Inject
    AppManager mAppManger;
    @Inject
    HorizontalDividerItemDecoration decoration;
    @Inject
    StandardAddressOneAdapter adapter;

    /**
     * 进入详情的 pos 在详情中删除时 要 根据这个删除
     */
    private int deletePosition;
    /**
     * 搜索框
     */
    private SearchPopup searchPopup;
    /**
     * departmentId
     */
    private String departmentId;
    /**
     * 层级
     */
    private String tierId;
    /**
     * departmentName
     */
    private String departmentName;
    /**
     * 判断从那个界面跳转过来的
     */
    @Autowired(name = ARouerConstant.SOURCE)
    public static String source;
    /**
     * 判断从那个界面跳转过来的
     */
    @Autowired(name = ARouerConstant.TITLE)
    public static String title;
    /**
     * 新增  资源时选择的网格 信息
     */
    @Autowired(name = ARouerConstant.DATA_BEAN)
    SearchProjectBean.DepartmentsBean selectedObject;

    /**
     * 选择网格的弹框
     */
    private static final int GRID = 0;
    /**
     * 选择层级的弹框
     */
    private static final int TIER = 1;
    private int clickItem = -1;

    private String pointsInJSON;

    private List<? extends BaseSearchPopupBean> tierList;

    /**
     * 这里和 InformationCollectionActivity  isCRUD 的又区分
     */
    private boolean isCRUD = false;
    /**
     * 是否是第一次获取数据
     */
    private boolean isFirstGetData = true;

    /**
     * popup
     */
    private CommonTreeListPopup<SearchProjectBean.DepartmentsBean> popup;
    private SearchProjectBean.DepartmentsBean defaultDepartmentBean;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStandardAddressOneNewComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_standard_address_one_new;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            setTitle(title);
            Timber.e("sadasd");
            if (StringUtil.isNotNullString(source)) {
                isCRUD = source.equals(RouterHub.APP_INFORMATIONCOLLECTIONACTIVITY);
            } else {
                isCRUD = false;
            }
            initRecycler();
            clickItem = GRID;
            if (selectedObject != null) {
                selectGridLayout.setClickable(false);
                selectGrid.setText(selectedObject.getName());
                selectGrid.setTag(selectedObject.getId());
                departmentId = selectedObject.getId();
            } else {
                selectGridLayout.setClickable(true);
                defaultDepartmentBean = new SearchProjectBean.DepartmentsBean();
                defaultDepartmentBean.setLevel(StandardAddressStairBean.AREA);
                defaultDepartmentBean.setEnumCommunityLevel(UserInfoUtil.getUserInfo().getEnumCommunityLevel());
                defaultDepartmentBean.setLeaf(true);
                defaultDepartmentBean.setId(UserInfoUtil.getUserInfo().getDepartmentId());
                defaultDepartmentBean.setFullPath(UserInfoUtil.getUserInfo().getFullPath());
                defaultDepartmentBean.setName(UserInfoUtil.getUserInfo().getDepartmentName() + "(默认)");
                selectedObject = defaultDepartmentBean;
                selectGrid.setText(defaultDepartmentBean.getName());
                selectGrid.setTag(defaultDepartmentBean.getId());
                departmentId = defaultDepartmentBean.getId();
            }
            searchEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        keyWord = s.toString();
                        if (StringUtil.isNullString(departmentId)) {
                            return;
                        }
                        getGriddingFourRace(true, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            AccessControlUtil.controlByLevelCommunity(add);
            getGriddingFourRace(true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getGriddingFourRace(boolean pullToRefresh, boolean isLoadMore) {
        mPresenter.getGriddingFourRace(pullToRefresh, isLoadMore, departmentId, tierId, keyWord);
    }

    private void getDepartments(boolean pullToRefresh, boolean isLoadMore, String keyword, String id, boolean addChild) {
//        mPresenter.getGridList(pullToRefresh, isLoadMore, keyword);
        mPresenter.getDepartments(id, addChild);
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
        try {
            if (searchPopup != null && searchPopup.isShowing()) {
                searchPopup.setLoadMoreViewHide(b);
            } else {
                if (b) {
                    adapter.loadMoreEnd(true);
                } else {
                    adapter.loadMoreComplete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.select_grid_layout, R.id.select_tier_layout, R.id.add})
    public void onClickView(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.select_grid_layout:
                    clickItem = GRID;
                    isFirstGetData = false;
                    getDepartments(true, false, "", "", false);
                    break;
                case R.id.select_tier_layout:
                    if (StringUtil.isNullString(departmentId)) {
                        ArmsUtils.makeText("请先选择网格");
                        break;
                    }
                    clickItem = TIER;
                    if (tierList != null) {
                        showPopup(tierList, false);
                    } else {
                        mPresenter.getDepartmentDownInfo(true, false, departmentId);
                    }
                    break;
                case R.id.add:
                    if (StringUtil.isNullString(departmentId)) {
                        ArmsUtils.makeText("请选择网格");
                        break;
                    }
                    if (selectedObject.getEnumCommunityLevel() > StandardAddressStairBean.GRIDDING) {
                        ArmsUtils.makeText("区域选择到网格,才可以添加数据");
                        return;
                    }
                    /*if (StringUtil.isNullString(tierId)) {
                        ArmsUtils.makeText("请选择层级");
                        return;
                    }*/
                    ARouter.getInstance().build(RouterHub.APP_STANDARDADDRESSADDALERTACTIVITY)
                            .withInt(StandardAddressAddAlertActivity.STATE, EventBusConstant.ADD)
                            .withString(Constant.DEPARTMENT_ID, departmentId)
                            .withString(StandardAddressUpListActivity.ADDRESSTYPE_ID, tierId)
                            .withString(MapActivity.POINTS_IN_JSON, pointsInJSON)
                            .navigation();
                    break;
                default:
                    KeybordUtil.hideKeyboard(this);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
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


    private void initRecycler() {
        try {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                getGriddingFourRace(true, false);
            });
            adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            linearLayoutManager = new LinearLayoutManager(this);
            ArmsUtils.configRecyclerView(recyclerView, linearLayoutManager);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (adapter.getItem(position) instanceof StandardAddressStairBean.StandardAddressStairBaseBean) {
                        StandardAddressStairBean.StandardAddressStairBaseBean bean =
                                (StandardAddressStairBean.StandardAddressStairBaseBean) adapter.getItem(position);
                        if (view.getId() == R.id.all_item) {
                            if (isCRUD) {
                                ARouter.getInstance().build(RouterHub.APP_STANDARDADDRESSLOOKACTIVITY)
                                        .withString(StandardAddressLookActivity.DETAIL_ID, bean.getId())
                                        .withString(ARouerConstant.DEPARTMENT_ID, departmentId)
                                        .navigation();
                                deletePosition = position;
                            } else {
                                finish();
                                bean.setDepartmentId(departmentId);
                                EventBus.getDefault().post(
                                        new EventBusBean(EventBusConstant.UPDATE_UP_ADDRESS, "", bean)
                                        , source);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            adapter.setOnLoadMoreListener(() -> {
                try {
                    getGriddingFourRace(false, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, recyclerView);
            adapter.setEmptyView(R.layout.null_content_layout, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showPopup(List<? extends BaseSearchPopupBean> listBeans, boolean isMore) {
        try {
            if (listBeans != null && listBeans.size() > 0) {
                KeybordUtil.hideKeyboard(this);
                switch (clickItem) {
                    case TIER:
                        if (tierList == null || tierList.size() <= 0) {
                            tierList = listBeans;
                        }
                        break;
                    case GRID:
                        if (isFirstGetData) {
                            if (listBeans != null && listBeans.size() > 0) {
                                BaseSearchPopupBean baseSearchPopupBean = listBeans.get(0);
                                departmentId = baseSearchPopupBean.getId();
                                departmentName = baseSearchPopupBean.getName();
                                selectGrid.setText(StringUtil.setText(departmentName));
                                if (baseSearchPopupBean instanceof UserGideBean.ListBean) {
                                    UserGideBean.ListBean userGideBean
                                            = (UserGideBean.ListBean) baseSearchPopupBean;
                                    pointsInJSON = userGideBean.getPointsInJSON();
                                }
                                if (StringUtil.isNotNullString(departmentId)) {
                                    getGriddingFourRace(true, false);
                                }
                            }
                            return;
                        }
                        break;
                    default:
                }
                if (searchPopup == null) {
                    searchPopup = new SearchPopup(this);
                    searchPopup.setPopupClickListener(new SearchPopup.PopupClickListener() {
                        @Override
                        public void onSearchBtnClick(String keyword) {

                            try {
                                switch (clickItem) {

                                    case GRID:
                                        getDepartments(true, false, keyword, "", false);
                                        break;
                                    case TIER:
                                        mPresenter.getDepartmentDownInfo(true, false, departmentId);
                                        break;
                                    default:
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onLoadMore(String keyword) {
                            try {
                                switch (clickItem) {
                                    case GRID:
                                        getDepartments(false, true, keyword, "", false);
                                        break;
                                    case TIER:
                                        searchPopup.setLoadMoreViewHide(true);
                                        break;
                                    default:
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onItemClick(BaseSearchPopupBean bean) {
                            try {
                                if (bean == null) {
                                    ArmsUtils.makeText("选中信息为空");
                                    return;
                                }
                                switch (clickItem) {
                                    case GRID:
                                        departmentId = bean.getId();
                                        departmentName = bean.getName();
                                        selectGrid.setText(StringUtil.setText(departmentName));
                                        if (bean instanceof UserGideBean.ListBean) {
                                            UserGideBean.ListBean userGideBean = (UserGideBean.ListBean) bean;
                                            pointsInJSON = userGideBean.getPointsInJSON();
                                        }
                                        //判断当前是否选择网格
                                        if (StringUtil.isNullString(bean.getName())) {
                                            return;
                                        }
                                        break;
                                    case TIER:
                                        tierId = bean.getId();
                                        selectTier.setText(StringUtil.setText(bean.getName()));
                                        break;
                                    default:
                                }
                                getGriddingFourRace(true, false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                if (!searchPopup.isShowing()) {
                    searchPopup.showPopupWindow();
                }
                searchPopup.setRecyclerViewData(listBeans, isMore, clickItem != TIER);
            } else {
                if (!isMore) {
                    ArmsUtils.makeText("未获取到您需要的信息");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setNewData(StandardAddressStairBean standardAddress, boolean isLoadMore, int count) {
        try {
            adapter.setGridArea(pointsInJSON);
            if (isLoadMore) {
                adapter.addData(new ArrayList<>(standardAddress.getStandardAddress()));
            } else {
                adapter.setNewData(new ArrayList<>(standardAddress.getStandardAddress()));
                number.setText(String.format(getResources().getString(R.string.data_count), count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showTreePopup(List<SearchProjectBean.DepartmentsBean> departmentsBeans, boolean addChild) {
        try {
            if (departmentsBeans == null || departmentsBeans.size() <= 0) {
                ArmsUtils.makeText("未查询到相关信息");
                return;
            }
            for (SearchProjectBean.DepartmentsBean bean : departmentsBeans) {
                bean.setLeaf(bean.getEnumCommunityLevel() == StandardAddressStairBean.GRIDDING);
            }

            if (popup == null) {
                popup = new CommonTreeListPopup<>(this, true);
                popup.setListener(new CommonTreeListPopup.CommonTreeListPopupListener<SearchProjectBean.DepartmentsBean>() {
                    @Override
                    public void onItemClickGetSubset(String elementId) {
                        getDepartments(true, false, "", elementId, true);
                    }

                    @Override
                    public void onItemClickInput(SearchProjectBean.DepartmentsBean da) {
                        selectGrid.setText(da.getName());
                        selectGrid.setTag(da.getId());
                        departmentId = da.getId();
                        selectedObject = da;
                        popup.dismiss();
                        getGriddingFourRace(true, false);
                    }

                    @Override
                    public void onSearchByKeyword(String keyWord) {
//                        mKeyWord = keyWord;
                    }

                    @Override
                    public void onLoadMore() {

                    }
                });
            }
            if (addChild) {
                popup.setDataTree(departmentsBeans);
            } else {
                departmentsBeans.add(0, defaultDepartmentBean);
                popup.setData(departmentsBeans, false);
            }
            if (!popup.isShowing()) {
                popup.showPopupWindow();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新 item 信息 删除  修改和新增
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_STANDARDADDRESSONENEWACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.ADD:
                    if (eventBusBean.getData() instanceof StandardAddressStairBean.StandardAddressStairBaseBean) {
                        StandardAddressStairBean.StandardAddressStairBaseBean bean = (StandardAddressStairBean.StandardAddressStairBaseBean) eventBusBean.getData();
                        if (adapter.getData().size() <= 0) {
                            ArrayList<StandardAddressStairBean.StandardAddressStairBaseBean> beans = new ArrayList<>();
                            beans.add(bean);
                            adapter.setNewData(beans);
                        } else {
                            adapter.addData(0, bean);
                        }
                        linearLayoutManager.scrollToPosition(0);
                    }
                    break;
                case EventBusConstant.ALERT:
                    if (eventBusBean.getData() instanceof StandardAddressStairBean.StandardAddressStairBaseBean && deletePosition >= 0) {
                        StandardAddressStairBean.StandardAddressStairBaseBean bean = (StandardAddressStairBean.StandardAddressStairBaseBean) eventBusBean.getData();
                        adapter.setData(deletePosition, bean);
                    }
                    break;
                case EventBusConstant.DELETE:
                    if (deletePosition >= 0) {
                        adapter.remove(deletePosition);
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
