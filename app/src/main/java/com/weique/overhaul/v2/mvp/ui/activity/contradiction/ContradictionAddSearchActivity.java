package com.weique.overhaul.v2.mvp.ui.activity.contradiction;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerContradictionAddSearchComponent;
import com.weique.overhaul.v2.mvp.contract.ContradictionAddSearchContract;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionAddSearchItemBean;
import com.weique.overhaul.v2.mvp.presenter.ContradictionAddSearchPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.ContradictionAddSearchAdapter;

import org.simple.eventbus.EventBus;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * @author GreatKing
 */
@Route(path = RouterHub.APP_CONTRADICTIONADDSEARCHACTIVITY)
public class    ContradictionAddSearchActivity extends BaseActivity<ContradictionAddSearchPresenter> implements ContradictionAddSearchContract.View {

    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;

    private ContradictionAddSearchAdapter contradictionAddSearchAdapter;



    private String name = "";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerContradictionAddSearchComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_contradiction_add_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle("标准地址搜索");
        assert mPresenter != null;
        mPresenter.getContradictionAddListData(true, false, UserInfoUtil.getUserInfo().getDepartmentId(), name);

        initApapter();
        initSearch();
    }

    private void initSearch() {
        try {
            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    name = charSequence.toString().trim();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                    assert mPresenter != null;
                    mPresenter.getContradictionAddListData(true, false, UserInfoUtil.getUserInfo().getDepartmentId(), name);


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initApapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getContradictionAddListData(true, false, UserInfoUtil.getUserInfo().getDepartmentId(), name);

            });


            contradictionAddSearchAdapter = new ContradictionAddSearchAdapter();
            contradictionAddSearchAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView,new LinearLayoutManager(this));
            recyclerView.setAdapter(contradictionAddSearchAdapter);
            recyclerView.setClipToPadding(false);
            contradictionAddSearchAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            recyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        EventBus.getDefault().post((ContradictionAddSearchItemBean.DepartmentBean)adapter.getData().get(position),RouterHub.APP_CONTRADICTIONADDACTIVITY);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            contradictionAddSearchAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getContradictionAddListData(false, true, UserInfoUtil.getUserInfo().getDepartmentId(), name);

            }, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
        vSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        vSwipeRefresh.setRefreshing(false);
    }

    /**
     * 隐藏或结束加载更多
     * true 结束  false 隐藏
     */
    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            contradictionAddSearchAdapter.loadMoreEnd(true);
        } else {
            contradictionAddSearchAdapter.loadMoreComplete();
        }
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
    public void setContradictionAddListData(ContradictionAddSearchItemBean itemBean, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                contradictionAddSearchAdapter.addData(itemBean.getDepartment());
            } else {
                contradictionAddSearchAdapter.setNewData(itemBean.getDepartment());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
