package com.weique.overhaul.v2.mvp.ui.activity.addressbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerAddressBookComponent;
import com.weique.overhaul.v2.mvp.contract.AddressBookContract;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookItemBean;
import com.weique.overhaul.v2.mvp.presenter.AddressBookPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.chat.nodejs.WebrtcUtil;
import com.weique.overhaul.v2.mvp.ui.adapter.AddressBookAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.AddressBookDetailAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 *
 */
@Route(path = RouterHub.APP_ADDRESSBOOKACTIVITY)
public class AddressBookActivity extends BaseActivity<AddressBookPresenter> implements AddressBookContract.View {


    @Autowired(name = "ID")
    String id;
    @Autowired(name = ARouerConstant.TITLE)
    String title;
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    @Autowired(name = "addressName")
    String addressName;
    @Autowired(name = "titleName")
    String titleName;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;
    @BindView(R.id.belong_address)
    TextView belongAddress;


    private AddressBookAdapter addressBookAdapter;
    private AddressBookDetailAdapter addressBookDetailAdapter;
    private String roomId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddressBookComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_address_book;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);


        initAdapter();
        isFirstEnter(true, false);


        if (ArmsUtils.isEmpty(addressName)) {
            addressName = UserInfoUtil.getUserInfo().getDepartmentName();
            belongAddress.setText(addressName);
            setTitle(addressName + title);
        } else {
            belongAddress.setText(addressName);
            setTitle(titleName + title);
        }


    }

    private void isFirstEnter(boolean pullToRefresh, boolean isLoadMore) {
        assert mPresenter != null;
        if (TextUtils.isEmpty(id)) {
            mPresenter.getDepartmentInfoListData(pullToRefresh, isLoadMore, UserInfoUtil.getUserInfo().getDepartmentId());
        } else {
            mPresenter.getDepartmentInfoListData(isLoadMore, isLoadMore, id);
        }
    }


    private void initAdapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> isFirstEnter(true, false));

            addressBookAdapter = new AddressBookAdapter();
            addressBookAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(addressBookAdapter);
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                    .colorResId(R.color.gray_eee)
                    .sizeResId(R.dimen.dp_1)
                    .build());
//            addressBookAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            addressBookAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    AddressBookItemBean.ListBean dataBean = (AddressBookItemBean.ListBean) adapter.getItem(position);
                    if (dataBean != null) {
                        if (StringUtil.isNotNullString(dataBean.getEnumCommunityLevel() + "") && "0".equals(dataBean.getEnumCommunityLevel() + "")) {
                            if (RouterHub.APP_CHATSELECTACTIVITY.equals(source)) {
                                ARouter.getInstance()
                                        .build(RouterHub.APP_CHATSELECTACTIVITY)
                                        .withString(ARouerConstant.ID, dataBean.getId())
                                        .navigation();
                            } else {
                                ARouter.getInstance().build(RouterHub.APP_ADDRESSLOOKLISTACTIVITY)
                                        .withString("ID", dataBean.getId())
                                        .withString("addressName", addressName + " / " + dataBean.getName())
                                        .navigation();
                            }
                        } else {
                            ARouter.getInstance().build(RouterHub.APP_ADDRESSBOOKACTIVITY)
                                    .withString("ID", dataBean.getId())
                                    .withString(ARouerConstant.SOURCE, source)
                                    .withString(ARouerConstant.TITLE, title)
                                    .withString("addressName", addressName + " / " + dataBean.getName())
                                    .withString("titleName", dataBean.getName())
                                    .navigation();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            addressBookDetailAdapter = new AddressBookDetailAdapter();
            addressBookDetailAdapter.setSource(source);
            addressBookDetailAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView1, new LinearLayoutManager(this));
            recyclerView1.setAdapter(addressBookDetailAdapter);
            recyclerView1.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                    .colorResId(R.color.gray_eee)
                    .sizeResId(R.dimen.dp_1)
                    .build());
            recyclerView1.setClipToPadding(false);
            addressBookDetailAdapter.setEmptyView(R.layout.null_content_layout, recyclerView1);
            addressBookDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (adapter.getItem(position) instanceof AddressBookItemBean.UsersBean) {
                        AddressBookItemBean.UsersBean listBean = (AddressBookItemBean.UsersBean) adapter.getItem(position);
                        if (StringUtil.isNullString(listBean.getTel()) || listBean.getTel().length() != 11) {
                            ArmsUtils.makeText("号码错误");
                            return;
                        }
                        switch (view.getId()) {
                            case R.id.phone:
                                new CommonDialog.Builder(AddressBookActivity.this)
                                        .setContent(listBean.getTel())
                                        .setPositiveButton(getString(R.string.call), (view1, commonDialog) -> {
                                            mPresenter.requestCalls(listBean.getTel());
                                        })
                                        .setNegativeButton(getString(R.string.ove), null)
                                        .create().show();
                                break;
                            case R.id.video:
                                new CommonDialog.Builder(AddressBookActivity.this)
                                        .setContent("确定发起会话！")
                                        .setPositiveButton((view1, commonDialog) -> {
                                            roomId = String.valueOf(System.currentTimeMillis() + (int) (1 + Math.random() * (50 - 1 + 1)));
                                            assert mPresenter != null;
                                            List<String> list = new ArrayList<>();
                                            //遍历集合所有选中的元素 发起视频会商
                                            String alias = listBean.getId().replace("-", "");
                                            list.add(alias);
                                            mPresenter.setChatList(list, roomId);
                                        }).create().show();
                                break;
                            default:
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            addressBookDetailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    isFirstEnter(false, true);
                }
            }, recyclerView1);
        } catch (
                Exception e) {
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
        if (addressBookDetailAdapter != null) {
            if (b) {
                addressBookDetailAdapter.loadMoreEnd(true);
            } else {
                addressBookDetailAdapter.loadMoreComplete();
            }
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
    public void setDepartmentInfoListData(AddressBookItemBean data, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                if (addressBookDetailAdapter != null) {
                    addressBookDetailAdapter.addData(data.getUsers());
                }
            } else {
                addressBookAdapter.setNewData(data.getList());
                if (addressBookDetailAdapter != null) {
                    addressBookDetailAdapter.setNewData(data.getUsers());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void getChatResult(String itemBean) {
        try {
            assert mPresenter != null;
            mPresenter.getPermission();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isOpen() {
        WebrtcUtil.call(this, Constant.CHAT_IP, roomId);
        finish();
    }

    @OnClick(R.id.search)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            if (TextUtils.isEmpty(id)) {
                id = UserInfoUtil.getUserInfo().getDepartmentId();
            }
            ARouter.getInstance().build(RouterHub.APP_ADDRESSLOOKLISTSEARCHACTIVITY)
                    .withString(Constant.DEPARTMENT_ID, id)
                    .withString(ARouerConstant.SOURCE, source)
                    .navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
