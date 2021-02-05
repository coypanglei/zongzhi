package com.weique.overhaul.v2.mvp.ui.activity.scan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerScanResultComponent;
import com.weique.overhaul.v2.mvp.contract.ScanResultContract;
import com.weique.overhaul.v2.mvp.model.entity.ScanResultBean;
import com.weique.overhaul.v2.mvp.presenter.ScanResultPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.ScanResultListAdapter;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/09/2020 13:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.APP_SCANRESULTACTIVITY)
public class ScanResultActivity extends BaseActivity<ScanResultPresenter> implements ScanResultContract.View {
    @Autowired(name = ARouerConstant.SOURCE)
    String id;

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
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.id_number)
    TextView idNumber;
    @BindView(R.id.allAddress)
    TextView allAddress;
    private ScanResultListAdapter scanResultListAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerScanResultComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_scan_result;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            setTitle("扫描");
            ARouter.getInstance().inject(this);
            initApapter();
            if (!TextUtils.isEmpty(id)) {
                assert mPresenter != null;
                mPresenter.getScanResultListData(true, false, id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initApapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getScanResultListData(true, false, id);
            });
            scanResultListAdapter = new ScanResultListAdapter();
            scanResultListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(scanResultListAdapter);
            scanResultListAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
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
            scanResultListAdapter.loadMoreEnd(true);
        } else {
            scanResultListAdapter.loadMoreComplete();
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
    public void setScanResultListData(ScanResultBean itemBean) {
        try {
            if (itemBean.getStandarAddress() == null) {
                killMyself();
                ArmsUtils.makeText("当前标准地址下无资源!");
            } else if (itemBean.getList() == null || itemBean.getList().size() == 0) {
                idNumber.setText(StringUtil.setText(itemBean.getStandarAddress().getCode()));
                address.setText(StringUtil.setText(itemBean.getStandarAddress().getFullPath()));
                allAddress.setText(StringUtil.setText(itemBean.getStandarAddress().getDepartment().getFullPath()));
            } else {
                idNumber.setText(StringUtil.setText(itemBean.getStandarAddress().getCode()));
                address.setText(StringUtil.setText(itemBean.getStandarAddress().getFullPath()));
                allAddress.setText(StringUtil.setText(itemBean.getStandarAddress().getDepartment().getFullPath()));
                scanResultListAdapter.setHavePermission(itemBean.isHavePermission());
                scanResultListAdapter.setNewData(itemBean.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
//

    }


}
