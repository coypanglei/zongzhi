package com.weique.overhaul.v2.mvp.ui.activity.visit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
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
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerResourceSearchDetailComponent;
import com.weique.overhaul.v2.mvp.contract.ResourceSearchDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchDetailItemBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressBean;
import com.weique.overhaul.v2.mvp.presenter.ResourceSearchDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.ResourceSearchDetailAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity.INFO;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity.LAT;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity.LNG;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity.REMIND;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity.RESOURCE;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity.TYPE;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/04/2019 11:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_RESOURCESEARCHDETAILACTIVITY)
public class ResourceSearchDetailActivity extends BaseActivity<ResourceSearchDetailPresenter> implements ResourceSearchDetailContract.View {


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
    @BindView(R.id.head_img)
    ImageView headImg;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.top_layout)
    LinearLayout topLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;
    private ResourceSearchDetailAdapter resourceSearchDetailAdapter;


    @Autowired(name = "NAME")
    String pName;
    @Autowired(name = "ADDRESS")
    String pAddress;
    @Autowired(name = "TYPE")
    String pType;
    @Autowired(name = "ResourceId")
    String ResourceId;
    @Autowired(name = "ElementTypeId")
    String ElementTypeId;


    @Inject
    Gson gson;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerResourceSearchDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_resource_search_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle("走访详情记录");
        rightBtnText.setText(ArmsUtils.getString(this, R.string.add_new));
        rightBtnText.setTextSize(17);
        rightBtnText.setTextColor(ArmsUtils.getColor(this, R.color.blue_ff3982f6));
        AccessControlUtil.controlByLevelGrid(rightBtnText);
        name.setText(pName);
        address.setText(pAddress);
        type.setText(pType);
        initApapter();

//        assert mPresenter != null;
//        mPresenter.getResourceSearchDetailListData(false, false, ResourceId, ElementTypeId);
    }

    private void initApapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getResourceSearchDetailListData(true, false, ResourceId, ElementTypeId);
            });

            resourceSearchDetailAdapter = new ResourceSearchDetailAdapter();
            resourceSearchDetailAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(resourceSearchDetailAdapter);
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                    .colorResId(R.color.gray_eee)
                    .sizeResId(R.dimen.dp_1)
                    .build());
            resourceSearchDetailAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            resourceSearchDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (view.getId() == R.id.edit_btn) {
                        String pointInJson = ((ResourceSearchDetailItemBean.ListBean) adapter.getData().get(position)).getPointInJson();
                        String picList = ((ResourceSearchDetailItemBean.ListBean) adapter.getData().get(position)).getIVImageUrlsInJson();
                        List<String> jsonList = new ArrayList<>();
                        jsonList = gson.fromJson(picList, new TypeToken<List<String>>() {
                        }.getType());
                        StandardAddressBean.LonAndLat lonAndLat = null;
                        try {
                            lonAndLat = gson.fromJson(pointInJson, StandardAddressBean.LonAndLat.class);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                        ARouter.getInstance().build(RouterHub.APP_TOURVISITACTIVITY)
                                .withString(TYPE, INFO)
                                .withString(REMIND, ((ResourceSearchDetailItemBean.ListBean) adapter.getData().get(position)).getMemo())
                                .withString(RESOURCE, pName)
                                .withString(ARouerConstant.TITLE, "编辑走访记录")
                                .withDouble(LNG, lonAndLat.getLng())
                                .withDouble(LAT, lonAndLat.getLat())
                                .withStringArrayList("list", (ArrayList<String>) jsonList)
                                .withSerializable("model", ((ResourceSearchDetailItemBean.ListBean) adapter.getData().get(position)))
                                .navigation();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            resourceSearchDetailAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getResourceSearchDetailListData(false, true, ResourceId, ElementTypeId);
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

    @Override
    protected void onResume() {
        super.onResume();
        assert mPresenter != null;
        mPresenter.getResourceSearchDetailListData(false, false, ResourceId, ElementTypeId);
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


    @OnClick(R.id.right_btn)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            ARouter.getInstance().build(RouterHub.APP_TOURVISITACTIVITY)
                    .withString(TYPE, "NEW")
                    .withString(RESOURCE, pName)
                    .withString(ARouerConstant.TITLE, "新增走访记录")
                    .withSerializable("model", (ResourceSearchDetailItemBean.ListBean) resourceSearchDetailAdapter.getData().get(0))
                    .navigation();
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
            resourceSearchDetailAdapter.loadMoreEnd(true);
        } else {
            resourceSearchDetailAdapter.loadMoreComplete();
        }
    }


    @Override
    public void setResourceSearchDetailListData(ResourceSearchDetailItemBean data, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                resourceSearchDetailAdapter.addData(data.getList());
            } else {
                resourceSearchDetailAdapter.setNewData(data.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
