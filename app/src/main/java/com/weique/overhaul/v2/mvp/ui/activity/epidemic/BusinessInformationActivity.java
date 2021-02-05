package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerBusinessInformationComponent;
import com.weique.overhaul.v2.mvp.contract.BusinessInformationContract;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInformation;
import com.weique.overhaul.v2.mvp.presenter.BusinessInformationPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.BusinessInformationAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 包挂人端入口
 * <p>
 * 领导人查看 企业列表
 * 企业信息
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_BUSINESSINFORMATIONACTIVITY)
public class BusinessInformationActivity extends BaseActivity<BusinessInformationPresenter> implements BusinessInformationContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout vSwipeRefresh;
    @BindView(R.id.search_view)
    MaterialSearchView materialSearchView;

    private String keyWord = "";
    private CountDownTimer timer;
    private BusinessInformationAdapter businessInformationAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBusinessInformationComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_business_information;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("企业信息");
        try {
            ARouter.getInstance().inject(this);
            initSearch();
            assert mPresenter != null;
            mPresenter.getBusinessInformationListData(true, false, keyWord);
            initAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化  搜索相关问题
     */
    private void initSearch() {
        try {
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
                                mPresenter.getBusinessInformationListData(true, false, keyWord);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getBusinessInformationListData(true, false, keyWord);

            });
            businessInformationAdapter = new BusinessInformationAdapter();
            businessInformationAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(businessInformationAdapter);
            recyclerView.setClipToPadding(false);
            businessInformationAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            businessInformationAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    BusinessInformation.ListBean item = (BusinessInformation.ListBean) adapter.getItem(position);
                    new CommonDialog.Builder(BusinessInformationActivity.this)
                            .setContent("请选择")
                            .setNegativeButton("查看", (view1, commonDialog) -> {
                                ARouter.getInstance().build(RouterHub.APP_BUSINESSINFORMATIONDETAILACTIVITY)
                                        .withString(ARouerConstant.ID, item.getId())
                                        .navigation();
                            })
                            .setPositiveButton("走访", (view1, commonDialog) -> {
                                ARouter.getInstance().build(RouterHub.APP_BUSINESSVISITACTIVITY)
                                        .withString(ARouerConstant.ID, item.getId())
                                        .withString(ARouerConstant.COMMON_NAME, item.getName())
                                        .navigation();
                            }).create().show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            businessInformationAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getBusinessInformationListData(false, true, keyWord);
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
            businessInformationAdapter.loadMoreEnd(true);
        } else {
            businessInformationAdapter.loadMoreComplete();
        }
    }

    @OnClick({R.id.visit_record, R.id.right_btn, R.id.issue_list})
    public void onclickLisenter(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                //寻访类列表
                case R.id.visit_record:
                    ARouter.getInstance().build(RouterHub.APP_BUSINESSINTERVIEWLISTACTIVITY)
                            .navigation();
                    break;
                //企业提交问题列表
                case R.id.issue_list:
                    ARouter.getInstance().build(RouterHub.APP_BUSINESSQUESTIONACTIVITY)
                            .navigation();
                    break;
                case R.id.right_btn:
                    if (!materialSearchView.isSearchOpen()) {
                        materialSearchView.showSearch();
                    }
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
        finish();
    }


    @Override
    public void setBusinessInformationListData(BusinessInformation itemBean, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                businessInformationAdapter.addData(itemBean.getList());
            } else {
                businessInformationAdapter.setNewData(itemBean.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
