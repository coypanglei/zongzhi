package com.weique.overhaul.v2.mvp.ui.activity.addressbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerAddressLookListComponent;
import com.weique.overhaul.v2.mvp.contract.AddressLookListContract;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookListBean;
import com.weique.overhaul.v2.mvp.presenter.AddressLookListPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.AddressBookListAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 通讯录 - 网格级别
 */
@Route(path = RouterHub.APP_ADDRESSLOOKLISTACTIVITY)
public class AddressLookListActivity extends BaseActivity<AddressLookListPresenter> implements AddressLookListContract.View {
    @Autowired(name = "ID")
    String id;
    @Autowired(name = ARouerConstant.SOURCE)
    String source;

    @Autowired(name = "addressName")
    String addressName;

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.search)
    CardView search;
    @BindView(R.id.belong_address)
    TextView belongAddress;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private AddressBookListAdapter addressBookListAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddressLookListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_address_look_list1;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        ARouter.getInstance().inject(this);
        setTitle("通讯录");
        belongAddress.setText(addressName);
        initApapter();
        isFirstEnter();
    }

    private void isFirstEnter() {
        if (TextUtils.isEmpty(id)) {
            assert mPresenter != null;
            mPresenter.getAddressBookListData(true, false, UserInfoUtil.getUserInfo().getDepartmentId(), "");
        } else {
            assert mPresenter != null;
            mPresenter.getAddressBookListData(true, false, id, "");
        }
    }

    private void initApapter() {

        try {
            addressBookListAdapter = new AddressBookListAdapter();
            addressBookListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(addressBookListAdapter);
            recyclerView.setClipToPadding(false);
            addressBookListAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            addressBookListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (adapter.getItem(position) instanceof AddressBookListBean.ListBean) {
                        AddressBookListBean.ListBean listBean = (AddressBookListBean.ListBean) adapter.getItem(position);
                        if (StringUtil.isNullString(listBean.getTel()) || listBean.getTel().length() != 11) {
                            ArmsUtils.makeText("号码错误");
                            return;
                        }
                        switch (view.getId()) {
                            case R.id.phone:
                                new CommonDialog.Builder(AddressLookListActivity.this)
                                        .setContent(listBean.getTel())
                                        .setPositiveButton(getString(R.string.call), (view1, commonDialog) -> {
                                            mPresenter.requestCalls(listBean.getTel());
                                        })
                                        .setNegativeButton(getString(R.string.ove), null)
                                        .create().show();
                                break;
                            default:
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            addressBookListAdapter.setOnLoadMoreListener(this::isFirstEnter, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    /**
     * 隐藏或结束加载更多
     * true 结束  false 隐藏
     */
    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            addressBookListAdapter.loadMoreEnd(true);
        } else {
            addressBookListAdapter.loadMoreComplete();
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
    public void setAddressBookListData(AddressBookListBean data, boolean isLoadMore) {
        try {
            if (data == null) {
                return;
            }
            if (isLoadMore) {
                addressBookListAdapter.addData(data.getList());
            } else {
//                areaType.setText(data.getDepartment().getCode());
//                areaName.setText(data.getDepartment().getName());
//                addressType.setText(data.getDepartment().getLevel());
//                unit.setText(data.getDepartmentUp().getName());
//                code.setText(data.getDepartmentUp().getCode());
//                wanggeType.setText(data.getDepartmentUp().getLevel());
                addressBookListAdapter.setNewData(data.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    @OnClick(R.id.search)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            ARouter.getInstance().build(RouterHub.APP_ADDRESSLOOKLISTSEARCHACTIVITY)
                    .withString(Constant.DEPARTMENT_ID, id)
                    .navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
