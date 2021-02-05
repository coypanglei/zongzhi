package com.weique.overhaul.v2.mvp.ui.fragment.matter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerMatterListComponent;
import com.weique.overhaul.v2.mvp.contract.MatterListContract;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonEnumBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterListBean;
import com.weique.overhaul.v2.mvp.presenter.MatterListPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MatterListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.app.common.Constant.ADDRESS_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.LARGE_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.MORE_IMG_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.MORE_URL_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.NUMBER_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.SELECT_ITEM;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author Administrator
 */
public class MatterListFragment extends BaseLazyLoadFragment<MatterListPresenter> implements MatterListContract.View {

    /**
     * 我的
     */
    public static final String MY = "历史事项";
    /**
     * 主办
     */
    public static final String SPONSOR = "待处理(主办)";
    /**
     * 协同
     */
    public static final String SYNERGY = "待处理(协同)";

    /**
     * 缓存key
     */
    private static final String TYPE_ = "type";
    private static final String SORT_ = "sort";
    private static final String ORDER_STATE_ = "state";
    private static final String KEYWORD_ = "keyword";

    private int defaultSort = -2;
    private int defaultState = -2;
    private String keyword = "";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.sr)
    SwipeRefreshLayout sr;
    @BindView(R.id.add)
    FloatingActionButton add;
    private MatterListAdapter mAdapter;
    private int flag;
    private String type;

    public static MatterListFragment newInstance(String type) {
        MatterListFragment fragment = new MatterListFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_, type);
        fragment.setArguments(args);
        return fragment;
    }

    public void setStateAndSort(int state, int sort) {
        Bundle bundle = getArguments();
        bundle.putInt(SORT_, sort);
        bundle.putInt(ORDER_STATE_, state);
        defaultState = getArguments().getInt(ORDER_STATE_);
        defaultSort = getArguments().getInt(SORT_);
        if (isVisibleToUser()) {
            getMatterList(true, false);
        } else {
            setDataLoaded(false);
        }
    }

    public void setKeyword(String keyword) {
        Bundle bundle = getArguments();
        bundle.putString(KEYWORD_, keyword);
        this.keyword = getArguments().getString(KEYWORD_);
        if (isVisibleToUser()) {
            getMatterList(true, false);
        } else {
            setDataLoaded(false);
        }
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMatterListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matter_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getArguments().getString(TYPE_);
        if (SPONSOR.equals(type)) {
            //我的提交
            flag = 0;
            add.setVisibility(View.GONE);
        } else if (SYNERGY.equals(type)) {
            //协同
            flag = 1;
            add.setVisibility(View.GONE);
        } else if (MY.equals(type)) {
            //我的
            flag = -1;
            AccessControlUtil.controlByLevelCommunity(add);
        }
        initAdapter();
    }

    private void initAdapter() {
        try {
            assert mPresenter != null;
            sr.setOnRefreshListener(() -> {
                getMatterList(true, false);
            });
            mAdapter = new MatterListAdapter(flag);
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(mAdapter);
            mAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                    .Builder(Objects.requireNonNull(getContext()))
                    .colorResId(R.color.gray_eee)
                    .sizeResId(R.dimen.dp_1)
                    .build());
            mAdapter.setOnLoadMoreListener(() -> {
                getMatterList(false, true);
            }, recyclerView);
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    MatterListBean.ListBean bean = (MatterListBean.ListBean) adapter.getItem(position);
                    ARouter.getInstance().build(RouterHub.APP_MATTERDETAILACTIVITY)
                            .withString(ARouerConstant.ID, bean.getId())
                            .withString(ARouerConstant.ID2, bean.getEventRecordId())
                            .withInt(ARouerConstant.STATUS, bean.getEnumOrderStatus())
                            .withString(ARouerConstant.TYPE, type)
                            .navigation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMatterList(boolean b, boolean b2) {
        try {
            mPresenter.getList(b, b2, flag, defaultState, defaultSort, keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        sr.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        sr.setRefreshing(false);
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
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.add})
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.add:
                    List<BasicInformationBean.RecordsBean> list = new ArrayList<>();
                    CommonCollectBean commonCollectBean = new CommonCollectBean();
                    Map<String, Object> map = new HashMap<>();
                    commonCollectBean.setPath("/app/EventRecords/CreateEventRecord");
                    list.add(new BasicInformationBean.RecordsBean(SELECT_ITEM, "事项等级",
                            "EnumEventLevel", "请填写事项等级", true));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "事项标题",
                            "Title", "请填写事项标题", true));
                    list.add(new BasicInformationBean.RecordsBean(LARGE_EDIT_ITEM, "事项详情",
                            "Content", "请填写事项详情", true));
                    list.add(new BasicInformationBean.RecordsBean(ADDRESS_ITEM, "发生位置",
                            "LocationInJson", "请选择发生位置", true));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "发生地址",
                            "EventAddress", "请填写发生地址", true));
                    list.add(new BasicInformationBean.RecordsBean(SELECT_ITEM, "事项信息来源",
                            "EnumDataSource", "请填写事项信息来源", true));
                    list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "上报人姓名",
                            "CreateEmName", "请填写上报人姓名", true));
                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "上报人电话",
                            "CreateEmTel", "请填写上报人电话", true));
                    list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "上报人身份证",
                            "CreateEmSID", "请填写上报人身份证", true));
                    list.add(new BasicInformationBean.RecordsBean(MORE_IMG_ITEM, "现场照片",
                            "ImgsInJson", "现场照片"));
                    list.add(new BasicInformationBean.RecordsBean(MORE_URL_ITEM, "现场视频",
                            "VideoUrl", "现场视频"));
                    commonCollectBean.setList(list);
                    commonCollectBean.setTitle("事项");
                    commonCollectBean.setMap(map);
                    commonCollectBean.setType(Constant.CommonCollectionEnum.COMMON_COLLECTION_ADD);
                    if (commonCollectBean.getList().size() > 0) {
                        ARouter.getInstance().build(RouterHub.APP_COMMON_COLLECTION).
                                withParcelable(ARouerConstant.COMMON_COLLECTION, commonCollectBean).
                                navigation();
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void lazyLoadData() {
        getMatterList(true, false);
    }

    @Override
    public void setList(List<MatterListBean.ListBean> list, boolean isLoadMore) {
        try {
            if (mAdapter != null) {
                if (isLoadMore) {
                    mAdapter.addData(list);
                } else {
                    mAdapter.setNewData(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param o 别管这个 公用P层 产生的
     */
    @Override
    public void setEnum(List<CommonEnumBean> o) {

    }
}
