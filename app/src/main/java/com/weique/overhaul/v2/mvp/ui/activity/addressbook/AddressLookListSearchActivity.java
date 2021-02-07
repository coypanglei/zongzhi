package com.weique.overhaul.v2.mvp.ui.activity.addressbook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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
import com.weique.overhaul.v2.di.component.DaggerAddressLookListSearchComponent;
import com.weique.overhaul.v2.mvp.contract.AddressLookListSearchContract;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookListBean;
import com.weique.overhaul.v2.mvp.presenter.AddressLookListSearchPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.chat.nodejs.WebrtcUtil;
import com.weique.overhaul.v2.mvp.ui.adapter.AddressBookListAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 *
 */
@Route(path = RouterHub.APP_ADDRESSLOOKLISTSEARCHACTIVITY)
public class AddressLookListSearchActivity extends BaseActivity<AddressLookListSearchPresenter> implements AddressLookListSearchContract.View {
    @Autowired(name = Constant.DEPARTMENT_ID)
    String id;

    /**
     * 这里记录的是  跳转到  AddressBookActivity 的source
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    @Autowired(name = ARouerConstant.TITLE)
    String title;

    @Autowired(name = "DepartmentId")
    String DepartmentId;

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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;
    @BindView(R.id.search)
    EditText search;
    private AddressBookListAdapter addressBookListAdapter;
    private String temp;
    private String roomId;
    private CountDownTimer timer;
    private boolean videoEnable;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddressLookListSearchComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_address_look_list_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            setTitle("搜索" + StringUtil.setText(title));
            vSwipeRefresh.setEnabled(false);
            ARouter.getInstance().inject(this);
            initAdapter();
            initSearch();

            search.setFocusable(true);
            search.setFocusableInTouchMode(true);
            search.requestFocus();
            InputMethodManager inputManager =
                    (InputMethodManager) search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(search, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initSearch() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();
                if (!newText.equals(temp)) {
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
                            isFirstEnter(true, false, temp, DepartmentId);
                        }
                    }.start();
                }
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

    private void isFirstEnter(boolean refresh, boolean loadMore, String name, String departmentId) {

        if (TextUtils.isEmpty(departmentId)) {
            if (TextUtils.isEmpty(id)) {
                id = UserInfoUtil.getUserInfo().getDepartmentId();
            }
            assert mPresenter != null;
            mPresenter.getAddressBookListData(refresh, loadMore, id, name);
        } else {
            assert mPresenter != null;
            mPresenter.getAddressBookListData(refresh, loadMore, departmentId, name);
        }

    }

    private void AllSearch(String name) {
//        assert mPresenter != null;
//        mPresenter.getAllAddressBookListData(true, false, name);

        assert mPresenter != null;
        mPresenter.getAddressBookListData(true, false, DepartmentId, name);
    }


    private void initAdapter() {

        try {
            addressBookListAdapter = new AddressBookListAdapter();
            addressBookListAdapter.setSource(source);
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
                                new CommonDialog.Builder(AddressLookListSearchActivity.this)
                                        .setContent(listBean.getTel())
                                        .setPositiveButton(getString(R.string.call), (view1, commonDialog) -> {
                                            mPresenter.requestCalls(listBean.getTel());
                                        })
                                        .setNegativeButton(getString(R.string.ove), null)
                                        .create().show();
                                break;
                            case R.id.video:
                                roomId = String.valueOf(System.currentTimeMillis() + (int) (1 + Math.random() * (50 - 1 + 1)));
                                assert mPresenter != null;
                                List<String> list = new ArrayList<>();
                                //遍历集合所有选中的元素 发起视频会商
                                String alias = listBean.getId().replace("-", "");
                                list.add(alias);
                                new CommonDialog.Builder(AddressLookListSearchActivity.this)
                                        .setContent("确定发起会话！")
                                        .setPositiveButton("视频会话", (view1, commonDialog) -> {
                                            videoEnable = true;
                                            mPresenter.setChatList(list, roomId, true);
                                        })
                                        .setNegativeButton("语音会话", (view12, commonDialog) -> {
                                            videoEnable = false;
                                            mPresenter.setChatList(list, roomId, false);
                                        }).create().show();
                                break;
                            default:
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            addressBookListAdapter.setOnLoadMoreListener(() -> {
                isFirstEnter(false, true, temp.toString(), DepartmentId);
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
            if (isLoadMore) {
                addressBookListAdapter.addData(data.getList());
            } else {
                addressBookListAdapter.setNewData(data.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAllAddressBookListData(AddressBookListBean data, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                addressBookListAdapter.addData(data.getList());
            } else {
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
        WebrtcUtil.call(this, Constant.CHAT_IP, roomId, videoEnable,
                UserInfoUtil.getUserInfo().getName(),
                UserInfoUtil.getUserInfo().getHeadUrl());
        finish();
    }
}
