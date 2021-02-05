package com.weique.overhaul.v2.mvp.ui.activity.sign;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerSginStaffListComponent;
import com.weique.overhaul.v2.mvp.contract.SignStaffListContract;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookItemBean;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookListBean;
import com.weique.overhaul.v2.mvp.model.entity.SignStaffListCommonBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.presenter.SignStaffListPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.SignStaffListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 签到查询  下属列表
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_SIGNSEARCHACTIVITY)
public class SignStaffListActivity extends BaseActivity<SignStaffListPresenter> implements SignStaffListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private SignStaffListAdapter mAdapter;

    /**
     * 点击的item
     */
    private int mPosition = -1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSginStaffListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sgin_staff_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("员工筛选");
        mPresenter.getDepartmentInfoListData(true, false, UserInfoUtil.getUserInfo().getDepartmentId(), false);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void setDepartmentInfoListData(List<? extends SignStaffListCommonBean> list, boolean needAdd) {
        try {
            if (mAdapter == null) {
                mAdapter = new SignStaffListAdapter();
                mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
                recyclerView.setAdapter(mAdapter);
                recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                        .colorResId(R.color.gray_eee)
                        .sizeResId(R.dimen.dp_1)
                        .build());
                mAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
                mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        Object item = adapter.getItem(position);
                        if (item instanceof AddressBookListBean.ListBean) {
                            AddressBookListBean.ListBean listBean = (AddressBookListBean.ListBean) adapter.getItem(position);
                            ARouter.getInstance().build(RouterHub.APP_MYSIGNRECORDACTIVITY)
                                    .withString(ARouerConstant.ID, listBean.getId())
                                    .withString(ARouerConstant.COMMON_NAME, listBean.getName())
                                    .navigation();
                        } else if (item instanceof AddressBookItemBean.ListBean) {
                            AddressBookItemBean.ListBean dataBean = (AddressBookItemBean.ListBean) adapter.getItem(position);
                            if (dataBean == null) {
                                ArmsUtils.makeText("获取信息失败");
                                return;
                            }
                            if (dataBean.isExpanded()) {
                                adapter.collapse(position);
                            } else {
                                mPosition = position;
                                if (dataBean.getEnumCommunityLevel() != StandardAddressStairBean.GRIDDING) {
                                    if (dataBean.getList() == null || dataBean.getList().size() <= 0) {
                                        mPresenter.getDepartmentInfoListData(true, false, dataBean.getId(), true);
                                    } else {
                                        adapter.expand(position);
                                    }
                                } else {
                                    if (dataBean.getList() == null || dataBean.getList().size() <= 0) {
                                        mPresenter.getSignStaffList(dataBean.getId(), true);
                                    } else {
                                        adapter.expand(position);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (needAdd) {
                SignStaffListCommonBean item = mAdapter.getItem(mPosition);
                for (SignStaffListCommonBean item1 : list) {
                    item1.setLevel(item.getLevel() + 1);
                }
                item.setList(list);
                for (SignStaffListCommonBean bean : list) {
                    item.addSubItem(bean);
                }
                mAdapter.expand(mPosition);
            } else {
                for (SignStaffListCommonBean item : list) {
                    item.setLevel(1);
                }
                mAdapter.setNewData((List<SignStaffListCommonBean>) list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
