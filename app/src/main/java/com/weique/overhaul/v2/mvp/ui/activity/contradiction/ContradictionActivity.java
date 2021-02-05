package com.weique.overhaul.v2.mvp.ui.activity.contradiction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerContradictionComponent;
import com.weique.overhaul.v2.mvp.contract.ContradictionContract;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionItemBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.presenter.ContradictionPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.ContradictionListAdapter;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * @author GreatKing
 */
@Route(path = RouterHub.APP_CONTRADICTIONACTIVITY, name = "")
public class ContradictionActivity extends BaseActivity<ContradictionPresenter> implements ContradictionContract.View {

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
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    GridInformationBean Bean = new GridInformationBean();


    private ContradictionListAdapter contradictionListAdapter;
    private String temp;
    private String status;

    OptionsPickerView pvOptions;
    private ArrayList<String> statusList;


    @Subscriber(tag = RouterHub.APP_CONTRADICTIONADDACTIVITY)
    private void update(String s) {
        temp = "";
        status = "";
        search.setText("");
        tvStatus.setText(getString(R.string.all));
        assert mPresenter != null;
        mPresenter.getContradictionListData(true, false, "", "");
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerContradictionComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_contradiction;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle("矛盾调解");
        if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.GRIDDING) {
            rightBtn.setVisibility(View.VISIBLE);
        } else {
            rightBtn.setVisibility(View.GONE);
        }
        temp = "";
        status = "";
        rightBtnText.setText(ArmsUtils.getString(this, R.string.add_new));
        rightBtnText.setTextColor(getResources().getColor(R.color.colorPrimary));
        rightBtnText.setTextSize(17);

        statusList = new ArrayList<>();
        statusList.add("全部");
        statusList.add("暂存");
        statusList.add("上报");
        statusList.add("已指派");
        statusList.add("已受理");
        statusList.add("退回");
        statusList.add("作废");
        statusList.add("处理完毕");

        initAdapter();
        initSearch();
        initOptionsPicker();

        assert mPresenter != null;
        //网格坐标
        mPresenter.GetDepartment(UserInfoUtil.getUserInfo().getDepartmentId());
        mPresenter.getContradictionListData(true, false, temp, status);

    }

    private void initOptionsPicker() {
        pvOptions = PickerViewUtil.selectPickerSelecter(this, (options1, options2, options3, v) -> {
            if (options1 == 0) {
                status = "";
                tvStatus.setText(statusList.get(options1));
            } else {
                status = options1 - 1 + "";
                tvStatus.setText(statusList.get(options1));
            }
            assert mPresenter != null;
            mPresenter.getContradictionListData(true, false, temp, status);
        });
    }


    private void initSearch() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                temp = charSequence.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                assert mPresenter != null;
                mPresenter.getContradictionListData(true, false, temp, status);

            }
        });
    }

    private void initAdapter() {

        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getContradictionListData(true, false, temp, status);
            });
            contradictionListAdapter = new ContradictionListAdapter();
            contradictionListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(contradictionListAdapter);
            recyclerView.setClipToPadding(false);
            contradictionListAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            recyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        ARouter.getInstance().build(RouterHub.APP_CONTRADICTIONADDACTIVITY)
                                .withString(ContradictionAddActivity.POINTS_IN_JSON, ((ContradictionItemBean.ListBean) adapter.getData().get(position)).getPointsInJSON())
                                .withString("ID", ((ContradictionItemBean.ListBean) adapter.getData().get(position)).getId())
                                .withInt("STATUS", ((ContradictionItemBean.ListBean) adapter.getData().get(position)).getEnumCAEventOrderSaveStatus())
                                .navigation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });


            contradictionListAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getContradictionListData(false, true, temp, status);
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
            contradictionListAdapter.loadMoreEnd(true);
        } else {
            contradictionListAdapter.loadMoreComplete();
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
    public void setContradictionListData(ContradictionItemBean data, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                contradictionListAdapter.addData(data.getList());
            } else {
                contradictionListAdapter.setNewData(data.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void gotoMapTv(GridInformationBean gridInformationBean) {
        try {
            Bean = gridInformationBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.right_btn, R.id.tv_status})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.right_btn:
                    ARouter.getInstance().build(RouterHub.APP_CONTRADICTIONADDACTIVITY)
                            .withString(ContradictionAddActivity.POINTS_IN_JSON, Bean.getPointsInJSON())
                            .navigation();
                    break;
                case R.id.tv_status:
                    KeybordUtil.hideKeyboard(this);
                    pvOptions.setPicker(statusList);
                    pvOptions.show();
                    break;

                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
