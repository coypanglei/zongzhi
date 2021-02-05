package com.weique.overhaul.v2.mvp.ui.fragment.standardaddress;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerInformationListForAddressDetailComponent;
import com.weique.overhaul.v2.mvp.contract.InformationListForAddressDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.InformationListForAddressDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.standardaddress.StandardAddressLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationFormStandardDetailAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 标准地址中  的 采集信息列表
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
public class InformationListForAddressDetailFragment extends BaseLazyLoadFragment<InformationListForAddressDetailPresenter> implements InformationListForAddressDetailContract.View {

    /**
     * 标准地址id
     */
    private final static String STANDARDADDRESSID = "STANDARDADDRESSID";
    /**
     * 元素id
     */
    private final static String ELEMENTID = "ELEMENTID";

    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    HorizontalDividerItemDecoration itemDecoration;
    @Inject
    InformationFormStandardDetailAdapter mAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swr)
    SwipeRefreshLayout swr;
    private String sId;
    private String eId;
    private String departmentId;

    public static InformationListForAddressDetailFragment newInstance(String standardAddressId, String elementId) {
        InformationListForAddressDetailFragment fragment = new InformationListForAddressDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STANDARDADDRESSID, standardAddressId);
        bundle.putString(ELEMENTID, elementId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerInformationListForAddressDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        departmentId = ((StandardAddressLookActivity) getActivity()).getDepartmentId();
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information_list_for_address_detail, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            swr.setOnRefreshListener(() -> mPresenter.getInformationListByStarters(true, false, sId, eId));
            mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            ArmsUtils.configRecyclerView(recyclerView, layoutManager);
            recyclerView.setClipToPadding(false);
            recyclerView.setAdapter(mAdapter);
            recyclerView.addItemDecoration(itemDecoration);
            mAdapter.setEmptyView(R.layout.null_content_home_layout, recyclerView);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (adapter.getItem(position) instanceof InformationTypeOneSecondBean.ElementListBean) {
                        InformationTypeOneSecondBean.ElementListBean listBean = (InformationTypeOneSecondBean.ElementListBean) adapter.getItem(position);
                        if (view.getId() == R.id.input) {
                            mPresenter.getDataStructureInJson(listBean, departmentId);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mAdapter.setOnLoadMoreListener(() -> {
                try {
                    mPresenter.getInformationListByStarters(false, true, sId, eId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        swr.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swr.setRefreshing(false);
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

    }

    /**
     * 第一次可见时触发调用,此处实现具体的数据请求逻辑
     */
    @Override
    protected void lazyLoadData() {
        try {
            sId = getArguments().getString(STANDARDADDRESSID);
            eId = getArguments().getString(ELEMENTID);
            mPresenter.getInformationListByStarters(true, false, sId, eId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setInformationData(List<InformationTypeOneSecondBean.ElementListBean> list, boolean isloadmore) {
        if (isloadmore) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
    }


    /**
     * 回调
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_INFORMATIONLISTFORADDRESSDETAILFRAGMENT)
    public void onEventCallBack(EventBusBean eventBusBean) {
        try {
            if (isVisibleToUser()) {
                if (StringUtil.isNullString(sId)) {
                    sId = getArguments().getString(STANDARDADDRESSID);
                }
                if (StringUtil.isNullString(eId)) {
                    eId = getArguments().getString(ELEMENTID);
                }
                switch (eventBusBean.getCode()) {
                    case EventBusConstant.DELETE:
                    case EventBusConstant.ADD:
                        assert mPresenter != null;
                        mPresenter.getInformationListByStarters(true, false, sId, eId);
                        break;
                    default:
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
