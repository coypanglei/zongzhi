package com.weique.overhaul.v2.mvp.ui.activity.information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerInformationTypeDetailListComponent;
import com.weique.overhaul.v2.mvp.contract.InformationTypeFirstContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.InformationTypeFirstPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.TreeListAdapter;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 人地事物组织 详情 列表
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_INFORMATIONTYPEFIRSTACTIVITY)
public class InformationTypeFirstActivity extends BaseActivity<InformationTypeFirstPresenter> implements InformationTypeFirstContract.View {
    /**
     * 主键查询 elementTypeId
     */
    @Autowired(name = ARouerConstant.ID)
    String id;

    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    AppManager appManager;
//    @Inject
//    InformationTypeListAdapter adapter;

    @BindView(R.id.bar_one)
    TextView barOne;
    @BindView(R.id.bar_two)
    TextView barTwo;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    VerticalSwipeRefreshLayout swipeRefreshLayout;
    private TreeListAdapter treeListAdapter;
    /**
     * 用户点击的item的
     */
    private int mPosition = -1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInformationTypeDetailListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_information_type_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            setTitle("信息类型");
            initRecycler();
            assert mPresenter != null;
            mPresenter.getElementTypes(id, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecycler() {
        try {
            swipeRefreshLayout.setOnRefreshListener(() ->
            {
                mPresenter.getElementTypes(id, false);
            });
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    try {
                        swipeRefreshLayout.setEnabled(recyclerView.getChildCount() == 0
                                || recyclerView.getChildAt(0).getTop() >= 0);
                        super.onScrollStateChanged(recyclerView, newState);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            treeListAdapter = new TreeListAdapter(null);
            treeListAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            ArmsUtils.configRecyclerView(recyclerView, layoutManager);
            recyclerView.setAdapter(treeListAdapter);
            treeListAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            treeListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (adapter.getItem(position) instanceof InformationTypeOneSecondBean.ElementTypeListBean) {
                        InformationTypeOneSecondBean.ElementTypeListBean item = (InformationTypeOneSecondBean.ElementTypeListBean) adapter.getItem(position);
                        if (item == null) {
                            ArmsUtils.makeText("获取信息失败");
                            return;
                        }
                        if (view.getId() == R.id.input) {
                            if (item.isLeaf()) {
                                ARouter.getInstance().build(RouterHub.APP_INFORMATIONTYPESECONDACTIVITY)
                                        .withString(ARouerConstant.ID, item.getId())
                                        .withString(ARouerConstant.TITLE, item.getName())
                                        .withString(ARouerConstant.SOURCE, RouterHub.APP_INFORMATIONTYPEFIRSTACTIVITY)
                                        .withString(InformationDynamicFormSelectActivity.DYNAMIC_FORM_JSON, item.getDataStructureInJson())
                                        .navigation();
                            }
                        }
                        if (view.getId() == R.id.all_item) {
                            if (item.isExpanded()) {
                                adapter.collapse(position);
                                treeListAdapter.getItem(position).setExpand(false);
                                treeListAdapter.notifyItemChanged(position);
                            } else {
                                if (!item.isLeaf()) {
                                    mPosition = position;
                                    if (item.getList() == null || item.getList().size() <= 0) {
                                        mPresenter.getElementTypes(item.getId(), true);
                                    } else {
                                        notifyAndScroll(position);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知adapter 展开  改变  箭头  个 滚动recycler view
     *
     * @param position position
     */
    private void notifyAndScroll(int position) {
        treeListAdapter.expand(position);
        treeListAdapter.getItem(position).setExpand(true);
        treeListAdapter.notifyItemChanged(position);
        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
        llm.scrollToPositionWithOffset(position, 0);
        llm.setStackFromEnd(false);
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
     * 获取 Context
     *
     * @return Context
     */
    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void setDataTree(List<InformationTypeOneSecondBean.ElementTypeListBean> dataTrees, boolean needAdd) {
        try {
            if (needAdd) {
                InformationTypeOneSecondBean.ElementTypeListBean item = treeListAdapter.getItem(mPosition);
                item.setList(dataTrees);
                notifyAndScroll(mPosition);
            } else {
                treeListAdapter.setNewData(dataTrees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 回调
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_INFORMATIONTYPEFIRSTACTIVITY)
    public void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.DELETE:
                case EventBusConstant.ADD:
                    assert mPresenter != null;
                    mPresenter.getElementTypes(id, false);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (!appManager.activityClassIsLive(InformationCollectionActivity.class)) {
                //用完 状态还原未默认
                InformationCollectionActivity.mType = InformationCollectionActivity.GATHER;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
