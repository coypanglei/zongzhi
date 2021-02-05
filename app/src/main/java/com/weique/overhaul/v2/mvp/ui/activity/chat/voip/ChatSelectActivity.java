package com.weique.overhaul.v2.mvp.ui.activity.chat.voip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
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
import com.weique.overhaul.v2.di.component.DaggerChatSelectComponent;
import com.weique.overhaul.v2.mvp.contract.ChatSelectContract;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookListBean;
import com.weique.overhaul.v2.mvp.presenter.ChatSelectPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.chat.nodejs.WebrtcUtil;
import com.weique.overhaul.v2.mvp.ui.adapter.ChatAlreadySelectListAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.ChatSelectListAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 视频会商选择
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_CHATSELECTACTIVITY)
public class ChatSelectActivity extends BaseActivity<ChatSelectPresenter> implements ChatSelectContract.View {

    @Autowired(name = ARouerConstant.ID)
    String id;


    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.select_recycler)
    RecyclerView selectRecycler;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;
    @BindView(R.id.btn_chat)
    Button btnChat;


    private ChatSelectListAdapter chatSelectListAdapter;
    /**
     * 关键字
     */
    private String keyword = "";

    private ChatAlreadySelectListAdapter chatAlreadySelectListAdapter;
    /**
     * 选中的人员列表
     */
    private List<AddressBookListBean.ListBean> alreadyList;

    private String roomId;
    /**
     * 接口--所有人员列表
     */
    private List<AddressBookListBean.ListBean> listBeanList;

    /**
     * 最大数量
     */
    private static final int MAX_NUM = 5;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChatSelectComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_chat_select;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            listBeanList = new ArrayList<>();
            alreadyList = new ArrayList<>();
            ARouter.getInstance().inject(this);
            setTitle("视频会商");
            if (StringUtil.isNullString(id)) {
                id = UserInfoUtil.getUserInfo().getDepartmentId();
            }
            btnChat.setVisibility(View.GONE);
            initAdapter();
            assert mPresenter != null;
            mPresenter.getAllAddressBookListData(true, false, keyword, id);
            initSearch();
            initSelect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSelect() {
        try {
            chatAlreadySelectListAdapter = new ChatAlreadySelectListAdapter();
            chatAlreadySelectListAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            ArmsUtils.configRecyclerView(selectRecycler, new GridLayoutManager(this, 3));
            selectRecycler.setAdapter(chatAlreadySelectListAdapter);
            selectRecycler.setClipToPadding(false);
            chatAlreadySelectListAdapter.setNewData(alreadyList);

            selectRecycler.addOnItemTouchListener(new OnItemChildClickListener() {
                @Override
                public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        if (view.getId() == R.id.tv_delete) {
                            assert mPresenter != null;
                            mPresenter.visableBtn();
                            /*
                             * 删除选中的item
                             */
                            chatAlreadySelectListAdapter.getData().remove(position);
                            chatAlreadySelectListAdapter.notifyDataSetChanged();
                            /*
                             *被选中的集合被删除时 联动所有的集合更新
                             */
                            for (AddressBookListBean.ListBean bean : chatSelectListAdapter.getData()) {
                                bean.setSelect(false);
                            }
                            for (AddressBookListBean.ListBean bean : chatSelectListAdapter.getData()) {
                                for (int i = 0; i < chatAlreadySelectListAdapter.getData().size(); i++) {
                                    if (bean.getId().equals(chatAlreadySelectListAdapter.getData().get(i).getId())) {
                                        bean.setSelect(true);
                                    }
                                }
                            }

                            chatSelectListAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 搜索
     */
    private void initSearch() {
        try {
            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    keyword = charSequence.toString().trim();
                    assert mPresenter != null;
                    mPresenter.getAllAddressBookListData(true, false, keyword, id);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getAllAddressBookListData(true, false, keyword, id);
            });
            chatSelectListAdapter = new ChatSelectListAdapter();
            chatSelectListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(chatSelectListAdapter);
            recyclerView.setClipToPadding(false);
            recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
                @Override
                public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (view.getId() == R.id.layout) {
                            AddressBookListBean.ListBean listBean = (AddressBookListBean.ListBean) adapter.getData().get(position);
                            Timber.e("listbean%s", listBean.toString());
                            if (!listBean.isSelect()) {
                                if (chatAlreadySelectListAdapter.getData().size() >= MAX_NUM) {
                                    ArmsUtils.makeText("最多选择5人！");
                                    return;
                                }
                                listBean.setSelect(true);
                                boolean isContain = false;
                                for (AddressBookListBean.ListBean bean : chatAlreadySelectListAdapter.getData()) {
                                    if (bean.getId().equals(listBean.getId())) {
                                        isContain = true;
                                    }
                                }
                                /*
                                 * 不包含在当前集合才能添加
                                 */
                                if (!isContain) {
                                    chatAlreadySelectListAdapter.getData().add(listBean);
                                }

                            } else {
                                listBean.setSelect(false);
                                /*
                                 * 包含在当前集合才能移除
                                 */
                                Iterator<AddressBookListBean.ListBean> list = chatAlreadySelectListAdapter.getData().iterator();
                                while (list.hasNext()) {
                                    AddressBookListBean.ListBean bean = list.next();
                                    if (bean.getId().equals(listBean.getId())) {
                                        list.remove();
                                    }
                                }
                            }
                            chatAlreadySelectListAdapter.notifyDataSetChanged();
                            Timber.e("listbean%s", listBean.toString());
                            chatSelectListAdapter.notifyItemChanged(position);

                        }
                        assert mPresenter != null;
                        mPresenter.visableBtn();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            /*
             上拉刷新
             */
            chatSelectListAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getAllAddressBookListData(false, true, keyword, id);
            }, recyclerView);
            chatSelectListAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
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
        try {
            if (b) {
                chatSelectListAdapter.loadMoreEnd(true);
            } else {
                chatSelectListAdapter.loadMoreComplete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAllAddressBookListData(AddressBookListBean data, boolean isLoadMore) {
        try {
            /*
             *  循环当前选中的集合 把选中的状态保存到新集合里
             */
            Timber.e(chatAlreadySelectListAdapter.getData().toString());
            for (AddressBookListBean.ListBean bean : data.getList()) {
                for (int i = 0; i < chatAlreadySelectListAdapter.getData().size(); i++) {
                    if (bean.getId().equals(chatAlreadySelectListAdapter.getData().get(i).getId())) {
                        Timber.e(bean.toString());
                        bean.setSelect(true);
                    }
                }
            }
            Timber.e("data_list%s", data.getList());
            /*
              清除集合更新数据
             */
            if (!isLoadMore) {
                listBeanList.clear();
                listBeanList.addAll(data.getList());
                chatSelectListAdapter.setNewData(listBeanList);
            } else {
                chatSelectListAdapter.addData(data.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void isOpen() {
        WebrtcUtil.call(this, Constant.CHAT_IP, roomId);
        finish();
    }

    @Override
    public void visableBtn() {
        /*
         * 选择集合 大于0 显示视频会商发起的按钮
         */
        try {
            if (!ArmsUtils.isEmpty(chatAlreadySelectListAdapter)) {
                if (chatAlreadySelectListAdapter.getData().size() > 0) {
                    btnChat.setVisibility(View.VISIBLE);
                } else {
                    btnChat.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    @OnClick(R.id.btn_chat)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            roomId = String.valueOf(System.currentTimeMillis() + (int) (1 + Math.random() * (50 - 1 + 1)));
            assert mPresenter != null;
            List<String> list = new ArrayList<>();
            //遍历集合所有选中的元素 发起视频会商
            for (AddressBookListBean.ListBean listBean : chatSelectListAdapter.getData()) {
                if (listBean.isSelect()) {
                    String alias = listBean.getId().replace("-", "");
                    list.add(alias);
                }
            }

            mPresenter.setChatList(list, roomId);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
