package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.content.Intent;
import android.os.Bundle;
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
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerBusinessInformationDetailComponent;
import com.weique.overhaul.v2.mvp.contract.BusinessInformationDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInformationDetailBean;
import com.weique.overhaul.v2.mvp.presenter.BusinessInformationDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.BusinessInformationDetailAdapter;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 领导人查看 企业列表
 * Description: 企业信息详情
 * ================================================
 */
@Route(path = RouterHub.APP_BUSINESSINFORMATIONDETAILACTIVITY)
public class BusinessInformationDetailActivity extends BaseActivity<BusinessInformationDetailPresenter> implements BusinessInformationDetailContract.View {
    @Autowired(name = ARouerConstant.ID)
    String id;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.legal_person_name)
    TextView legalPersonName;
    @BindView(R.id.legal_person_number)
    TextView legalPersonNumber;
    @BindView(R.id.contact_people)
    TextView contactPeople;
    @BindView(R.id.contact_people_number)
    TextView contactPeopleNumber;
    @BindView(R.id.people_count)
    TextView peopleCount;
    @BindView(R.id.belong_address)
    TextView belongAddress;
    @BindView(R.id.address_name)
    TextView addressName;
    @BindView(R.id.business_type)
    TextView businessType;
    @BindView(R.id.industry_type)
    TextView industryType;
    @BindView(R.id.social_code)
    TextView socialCode;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;


    private BusinessInformationDetailAdapter businessInformationDetailAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBusinessInformationDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_business_information_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            setTitle("企业信息详情");
            ARouter.getInstance().inject(this);
            assert mPresenter != null;
            mPresenter.getBusinessInformationDetailListData(true, false, id);
            initAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        try {
            if (swipeRefresh != null) {
                swipeRefresh.setOnRefreshListener(() -> {
                    assert mPresenter != null;
                    mPresenter.getBusinessInformationDetailListData(true, false, id);
                });
            }

            businessInformationDetailAdapter = new BusinessInformationDetailAdapter();
            businessInformationDetailAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView,new LinearLayoutManager(this));
            recyclerView.setAdapter(businessInformationDetailAdapter);
            recyclerView.setClipToPadding(false);
            businessInformationDetailAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            businessInformationDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    BusinessInformationDetailBean.PeopleBean.ListBean listBean
                            = (BusinessInformationDetailBean.PeopleBean.ListBean) adapter.getItem(position);
                    if (view.getId() == R.id.input) {
                        ARouter.getInstance().build(RouterHub.APP_BUSINESSSTAFFDETAILACTIVITY)
                                .withString(ARouerConstant.ID, (listBean.getId()))
                                .navigation();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            businessInformationDetailAdapter.setOnLoadMoreListener(() -> {
                mPresenter.getBusinessInformationDetailListData(false, true, id);
            }, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
        try {
            swipeRefresh.setRefreshing(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideLoading() {
        try {
            swipeRefresh.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏或结束加载更多
     * true 结束  false 隐藏
     */
    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            businessInformationDetailAdapter.loadMoreEnd(true);
        } else {
            businessInformationDetailAdapter.loadMoreComplete();
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
    public void setBusinessInformationDetailListData(BusinessInformationDetailBean itemBean, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                if (itemBean.getPeople() != null) {
                    businessInformationDetailAdapter.addData(itemBean.getPeople().getList());
                }
            } else {
                if (itemBean.getEnterpriseInf() != null) {
                    BusinessInformationDetailBean.EnterpriseInfBean enterpriseInf = itemBean.getEnterpriseInf();
                    peopleCount.setText((enterpriseInf.getPeopleCount()) + "人");
                    name.setText((enterpriseInf.getName()));
                    belongAddress.setText((enterpriseInf.getFullPath()));
                    businessType.setText((enterpriseInf.getEnumEnType()));
                    industryType.setText((enterpriseInf.getEnumIndustry()));
                    socialCode.setText((enterpriseInf.getCode()));
                    addressName.setText((enterpriseInf.getAddress()));
                    legalPersonName.setText((enterpriseInf.getLegalPerson()));
                    legalPersonNumber.setText((enterpriseInf.getTel()));
                    contactPeople.setText((enterpriseInf.getLiaisonMan()));
                    contactPeopleNumber.setText((enterpriseInf.getLiaisonTel()));
                    time.setText((enterpriseInf.getReturnWorkTime()));
                }
                if (itemBean.getPeople() != null) {
                    businessInformationDetailAdapter.setNewData(itemBean.getPeople().getList());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
