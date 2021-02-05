package com.weique.overhaul.v2.mvp.ui.fragment.enforcelaw;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.ViewAnimationUtil;
import com.weique.overhaul.v2.di.component.DaggerEnforceLawLawListComponent;
import com.weique.overhaul.v2.mvp.contract.EnforceLawLawListContract;
import com.weique.overhaul.v2.mvp.model.entity.CaseReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EnforceLawLawListPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.CasesReportedAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.NewOrderSortPopup;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 案件列表
 * ================================================
 *
 * @author GreatKing
 */
public class EnforceLawLawListFragment extends BaseLazyLoadFragment<EnforceLawLawListPresenter> implements EnforceLawLawListContract.View {


    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    CasesReportedAdapter mAdapter;
    @Inject
    HorizontalDividerItemDecoration decoration;
    @BindView(R.id.swipe_refresh)
    VerticalSwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.sort_text)
    TextView sortText;
    @BindView(R.id.status_text)
    TextView statusText;
    @BindView(R.id.status_arrow)
    ImageView statusArrow;
    @BindView(R.id.screen)
    LinearLayout screen;

    @BindView(R.id.sort_arrow)
    ImageView sortArrow;
    /**
     * 事件来源
     */
    private String type = "";

    /**
     * 状态
     */
    private String sortType = "";

    private String keyword;

    private List<CommonTitleBean> types;

    private List<CommonTitleBean> sortTypes;


    /**
     * 案件影响
     */
    public static final String ENUM_CASE_LEVEL = "EnumCaseLevel";
    /**
     * 案件来源
     */
    public static final String ENUMCASESOURCE = "EnumCaseSource";

    /**
     * 案件状态
     */
    public static final String CASE_SORT = "EnumComprehensiveLawEnforcementCaseStatus";
    /**
     * 案件处理记录状态
     */
    public static final String CASE_SORT_ = "EnumComprehensiveLawEnforcementCaseProcessStatus";

    private NewOrderSortPopup orderSortPopup;


    public static EnforceLawLawListFragment newInstance() {
        EnforceLawLawListFragment fragment = new EnforceLawLawListFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerEnforceLawLawListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enforce_law_law_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            types = new ArrayList<>();
            sortTypes = new ArrayList<>();
            refreshLayout.setOnRefreshListener(() -> {
                sortText.setText(getString(R.string.law_source));
                type = "";
                statusText.setText(getString(R.string.status));
                sortType = "";
                getOrderList(true, false, keyword);
            });
            ArmsUtils.configRecyclerView(recycler, linearLayoutManager);
            recycler.addItemDecoration(decoration);
            recycler.setAdapter(mAdapter);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    CaseReportedBean caseReportedBean = (CaseReportedBean) adapter.getItem(position);
                    if (caseReportedBean == null) {
                        ArmsUtils.makeText("获取案件信息失败");
                        return;
                    }
                    switch (view.getId()) {
                        case R.id.item_view:
                            ARouter.getInstance().build(RouterHub.APP_ENFORCE_LAW_LAW_DETAIL_ACTIVITY)
                                    .withString(ARouerConstant.ID, caseReportedBean.getId())
                                    .navigation();
                            break;
                        default:
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mAdapter.setOnLoadMoreListener(() -> getOrderList(false, true, keyword), recycler);
            mAdapter.setEmptyView(R.layout.null_content_layout, recycler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 分类回调
     *
     * @param eventBusBean
     */

    @Subscriber(tag = RouterHub.APP_EVENTSREPORTEDACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        //事件分类  回调 更新 recycler view
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.SEARCH_KEY:
                    /*
                      搜索的关键字
                     */
                    try {
                        keyword = eventBusBean.getMessage();
                        getOrderList(true, false, keyword);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case EventBusConstant.COMMON_UPDATE:
                    getOrderList(true, false, keyword);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取订单列表
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param keyword       keyword
     */
    private void getOrderList(boolean pullToRefresh, boolean isLoadMore, String keyword) {

        mPresenter.getCases(pullToRefresh, isLoadMore, keyword, type, sortType);
    }


    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void setData(@Nullable Object data) {

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
    public void setNewData(List<CaseReportedBean> newData, boolean isLoadMore) {
        if (isLoadMore) {
            mAdapter.addData(newData);
        } else {
            mAdapter.setNewData(newData);
        }
    }

    /**
     * 获取枚举值
     *
     * @param bean
     * @param sort
     */
    @Override
    public void getCommonEnums(List<CommonTitleBean> bean, String sort) {


        try {
            if (sort.equals(ENUMCASESOURCE)) {
                //            ViewAnimationUtil.rotateAnimation(sortArrow, true);
            } else {
                ViewAnimationUtil.rotateAnimation(statusArrow, true);
            }
            orderSortPopup = new NewOrderSortPopup(getActivity());
            bean.add(0, new CommonTitleBean("-2", "全部"));

            orderSortPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (sort.equals(ENUMCASESOURCE)) {
                        //                        ViewAnimationUtil.rotateAnimation(sortArrow, false);
                    } else {
                        ViewAnimationUtil.rotateAnimation(statusArrow, false);
                    }

                }
            });
            orderSortPopup.setListItemClickListener(new NewOrderSortPopup.ListItemClickListener() {
                @Override
                public void onItemClick(String orderStatus, String name) {
                    try {

                        if (sort.equals(ENUMCASESOURCE)) {
                            type = orderStatus + "";
                            if ("-2".equals(orderStatus)) {
                                type = "";
                            }
                        } else {
                            sortType = orderStatus + "";
                            if ("-2".equals(orderStatus)) {
                                sortType = "";
                            }
                        }

                        if (sort.equals(ENUMCASESOURCE)) {
                            sortText.setText(getString(R.string.law_source) + "(" + name + ")");
                        } else {
                            statusText.setText(getString(R.string.status) + "(" + name + ")");
                        }
                        getOrderList(true, false, keyword);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void reset() {
                    statusText.setText(ArmsUtils.getString(getActivity(), R.string.status));
                    if (sort.equals(ENUMCASESOURCE)) {
                        sortText.setText(getString(R.string.law_source));
                        type = "";
                    } else {
                        statusText.setText(getString(R.string.status));
                        sortType = "";
                    }
                    getOrderList(true, false, keyword);
                }
            });
            orderSortPopup.setBeans(bean);
            if (!orderSortPopup.isShowing()) {
                orderSortPopup.showPopupWindow(screen);
            }
            orderSortPopup.setBeans(bean);
//            orderSortPopup.setCheckPos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.sort, R.id.status})
    public void onClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.sort:
                    mPresenter.getCommonEnums(ENUMCASESOURCE);
                    break;
                case R.id.status:
                    mPresenter.getCommonEnums(CASE_SORT);
                    break;
                default:
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

    }


    @Override
    protected void lazyLoadData() {
        getOrderList(false, false, keyword);
    }
}
