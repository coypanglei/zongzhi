package com.weique.overhaul.v2.mvp.ui.activity.standardaddress;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

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
import com.jess.arms.utils.ArmsUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerStandardAddressUpListComponent;
import com.weique.overhaul.v2.mvp.contract.StandardAddressUpListContract;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressUpItemBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.StandardAddressUpListPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.StandardAddressUpListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 上级列表
 * <p>
 * ================================================
 * Description:
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_STANDARDADDRESSUPLISTACTIVITY)
public class StandardAddressUpListActivity extends BaseActivity<StandardAddressUpListPresenter> implements StandardAddressUpListContract.View {

    public static final String NO_SUPERIOR_ADDRESS_PID = "00000000-0000-0000-0000-000000000000";
    public static final String NO_SUPERIOR_ADDRESS_PID_HINT = "无上级地址";

    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    StandardAddressUpListAdapter mAdapter;
    /**
     * 标准地址类型Id
     */
    public static final String ADDRESSTYPE_ID = "AddressTypeId";

    @Autowired(name = Constant.DEPARTMENT_ID)
    String departmentId;
    @Autowired(name = ADDRESSTYPE_ID)
    String addressTypeId;
    @BindView(R.id.up_address_list)
    RecyclerView upAddressList;
    @BindView(R.id.swipeRefreshLayout)
    VerticalSwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.search_view)
    MaterialSearchView materialSearchView;

    private String keyWord = "";
    private CountDownTimer timer;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStandardAddressUpListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_standard_address_up_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle(ArmsUtils.getString(this, R.string.up_address));
        initSearch();
        initAdapter();
        mPresenter.getAddressUpList(false, false, departmentId, addressTypeId, keyWord);
    }

    private void initAdapter() {
        try {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                mPresenter.getAddressUpList(true, false, departmentId, addressTypeId, keyWord);
            });
            mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            ArmsUtils.configRecyclerView(upAddressList, linearLayoutManager);
            upAddressList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                    .colorResId(R.color.gray_eee)
                    .sizeResId(R.dimen.dp_1)
                    .build());
            upAddressList.setAdapter(mAdapter);
            upAddressList.setClipToPadding(false);
            mAdapter.setOnItemChildClickListener((mAdapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (mAdapter.getItem(position) instanceof StandardAddressUpItemBean.DataBean) {
                        StandardAddressUpItemBean.DataBean bean =
                                (StandardAddressUpItemBean.DataBean) mAdapter.getItem(position);
                        if (view.getId() == R.id.name) {
                            finish();
                            EventBus.getDefault().post(new EventBusBean<>(
                                            EventBusConstant.UPDATE_UP_ADDRESS, "", bean)
                                    , RouterHub.APP_STANDARDADDRESSADDALERTACTIVITY);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mAdapter.setOnLoadMoreListener(() -> {
                mPresenter.getAddressUpList(false, true, departmentId, addressTypeId, keyWord);
            }, upAddressList);
            mAdapter.setEmptyView(R.layout.null_result_layout, upAddressList);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        ArmsUtils.makeText(message);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    /**
     * 跟新数据
     *
     * @param data       standardAddressUpItemBean
     * @param isLoadMore isLoadMore
     */
    @Override
    public void setNewData(List<StandardAddressUpItemBean.DataBean> data, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                mAdapter.addData(data);
            } else {
                data.add(0, new StandardAddressUpItemBean.DataBean(NO_SUPERIOR_ADDRESS_PID, NO_SUPERIOR_ADDRESS_PID_HINT));
                mAdapter.setNewData(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.right_btn})
    public void onClick(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.right_btn:
                    if (!materialSearchView.isSearchOpen()) {
                        materialSearchView.showSearch();
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化  搜索相关问题
     */
    private void initSearch() {
        materialSearchView.setVoiceSearch(false);
        materialSearchView.setEllipsize(true);
        materialSearchView.setHint(getString(R.string.input_search_content));
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                assert mPresenter != null;
                if (!newText.equals(keyWord)) {
                    if (timer != null) {
                        timer.cancel();
                    }
                    timer = new CountDownTimer(1000, 1000) {
                        @SuppressLint("StringFormatMatches")
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            keyWord = newText;
                            mPresenter.getAddressUpList(true, false, departmentId, addressTypeId, keyWord);
                        }
                    }.start();
                }
                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.clearFocus();
            materialSearchView.closeSearch();
        }else{
            super.onBackPressed();
        }
    }

}
