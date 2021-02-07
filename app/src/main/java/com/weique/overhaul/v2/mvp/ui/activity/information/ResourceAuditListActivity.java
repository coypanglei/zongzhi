package com.weique.overhaul.v2.mvp.ui.activity.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerResourceAuditListComponent;
import com.weique.overhaul.v2.mvp.contract.ResourceAuditListContract;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceAuditBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.ResourceAuditListPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.ResourceAuditListAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.NewOrderSortPopup;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author Administrator
 */
@Route(path = RouterHub.APP_RESOURCEAUDITLISTACTIVITY)
public class ResourceAuditListActivity extends
        BaseActivity<ResourceAuditListPresenter> implements
        ResourceAuditListContract.View {
    @BindView(R.id.sort)
    LinearLayout sort;
    @BindView(R.id.status_text)
    TextView statusText;
    @BindView(R.id.screen)
    LinearLayout screen;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.sw)
    SwipeRefreshLayout sw;

    private NewOrderSortPopup<NameAndIdBean> orderSortPopup;
    private List<NameAndIdBean> statusList;
    private ResourceAuditListAdapter adapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerResourceAuditListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_resource_audit_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        sort.setVisibility(View.INVISIBLE);
        setTitle("资源核查列表");
        sw.setOnRefreshListener(() -> {
            getList(true, false);
        });
        getList(true, false);
        ResourceAuditListAdapter adapter = new ResourceAuditListAdapter(null);
    }

    @Override
    public void showLoading() {
        sw.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        sw.setRefreshing(false);
    }

    @Override
    public void LoadingMore(boolean b) {
        if (adapter == null) {
            return;
        }
        if (b) {
            adapter.loadMoreEnd(true);
        } else {
            adapter.loadMoreComplete();
        }
    }

    @OnClick({R.id.status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.status:
                if (statusList == null) {
                    statusList = new ArrayList<>();
                    statusList.add(new NameAndIdBean("-1", "全部"));
                    statusList.add(new NameAndIdBean("0", "待处理"));
                    statusList.add(new NameAndIdBean("1", "已处理"));
                    statusList.add(new NameAndIdBean("2", "拒绝"));
                    statusList.add(new NameAndIdBean("3", "上报"));
                }
                showPopup(statusList, statusText);
                break;
            default:
        }
    }


    private void showPopup(List<NameAndIdBean> list, TextView tv) {
        try {
            orderSortPopup = new NewOrderSortPopup<>(this);
            orderSortPopup.setListItemClickListener(new NewOrderSortPopup.ListItemClickListener() {
                @Override
                public void onItemClick(String id, String name) {
                    if (StringUtil.isNotNullString(name)) {
                        name = "-" + name;
                    }
                    tv.setText("状态" + name);
                    tv.setTag(id);
                    getList(true, false);
                }

                @Override
                public void reset() {
                    tv.setText("状态");
                    tv.setTag("-1");
                    getList(true, false);
                }
            });
            orderSortPopup.setBeans(list);
            int i = 0;
            for (NameAndIdBean b : list) {
                if (statusText.getText().toString().contains(b.getName())) {
                    orderSortPopup.setCheckPos(i);
                    break;
                }
                i++;
            }
            if (!orderSortPopup.isShowing()) {
                orderSortPopup.showPopupWindow(screen);
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

    @Override
    public void setList(List<ResourceAuditBean.ListBean> list, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                if (adapter != null) {
                    adapter.addData(list);
                }
            } else {
                if (adapter == null) {
                    adapter = new ResourceAuditListAdapter(list);
                    adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                    rv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                            .colorResId(R.color.gray_eee)
                            .sizeResId(R.dimen.dp_1)
                            .build());
                    ArmsUtils.configRecyclerView(rv, new LinearLayoutManager(this));
                    rv.setAdapter(adapter);
                    adapter.setEmptyView(R.layout.null_content_layout, rv);
                    adapter.setOnItemClickListener((adapter, view, position) -> {
                        ResourceAuditBean.ListBean item = (ResourceAuditBean.ListBean) adapter.getItem(position);
                        ARouter.getInstance()
                                .build(RouterHub.APP_RESOURCEAUDITDETAILACTIVITY)
                                .withParcelable(ARouerConstant.DATA_BEAN, item)
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_RESOURCEAUDITLISTACTIVITY)
                                .navigation();
                    });
                    adapter.setOnLoadMoreListener(() -> {
                        getList(false, true);
                    }, rv);
                } else {
                    adapter.setNewData(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getList(boolean pullToRefresh, boolean isLoadMore) {
        mPresenter.getList(pullToRefresh, isLoadMore, statusText.getTag() == null ? "-1" : statusText.getTag());
    }

    @Subscriber(tag = RouterHub.APP_RESOURCEAUDITLISTACTIVITY)
    public void onEventCallBack(EventBusBean eventBusBean) {
        try {
            getList(true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
