package com.weique.overhaul.v2.mvp.ui.activity.sign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.di.component.DaggerMySignRecordComponent;
import com.weique.overhaul.v2.mvp.contract.MySignRecordContract;
import com.weique.overhaul.v2.mvp.model.entity.MySignListBean;
import com.weique.overhaul.v2.mvp.presenter.MySignRecordPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MySignRecordAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * @author GreatKing
 */
//@Route(path = RouterHub.APP_MYSIGNRECORDACTIVITY)
public class MySignRecordActivity extends BaseActivity<MySignRecordPresenter> implements MySignRecordContract.View {


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
    @BindView(R.id.select_years)
    TextView selectYears;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;

    private String yearMonth;

    @Autowired
    String source;

    private MySignRecordAdapter mySignRecordAdapter;
    private TimePickerView timePickerView;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMySignRecordComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_sign_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle("打卡记录");
        initAdapter();
        initTimePicker();
        assert mPresenter != null;
        getCurrentYearMouthInfo();
    }

    private void getCurrentYearMouthInfo() {
        try {
            Calendar date = Calendar.getInstance();
            String year = String.valueOf(date.get(Calendar.YEAR));
            String month = String.valueOf(date.get(Calendar.MONTH) + 1);
            yearMonth = year + "-" + month;
            selectYears.setText(yearMonth);
            assert mPresenter != null;
            mPresenter.getMySignRecordListData(true, false, yearMonth);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initTimePicker() {
        timePickerView = PickerViewUtil.selectPickerTime(this, Constant.YM, (date, v) -> {
            try {
                SimpleDateFormat format = new SimpleDateFormat(Constant.YM, Locale.CHINA);
                selectYears.setText(format.format(date));
                yearMonth = format.format(date);
                if (!TextUtils.isEmpty(yearMonth)) {
                    assert mPresenter != null;
                    mPresenter.getMySignRecordListData(true, false, yearMonth);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initAdapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getMySignRecordListData(true, false, yearMonth);
            });


            mySignRecordAdapter = new MySignRecordAdapter();
            mySignRecordAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(mySignRecordAdapter);
            recyclerView.setClipToPadding(false);
            mySignRecordAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            recyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            mySignRecordAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getMySignRecordListData(false, true, yearMonth);
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
            mySignRecordAdapter.loadMoreEnd(true);
        } else {
            mySignRecordAdapter.loadMoreComplete();
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


    @OnClick({R.id.select_years})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.select_years:
                    timePickerView.show();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMySignRecordListData(MySignListBean data, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                mySignRecordAdapter.addData(data.getList());
            } else {
                mySignRecordAdapter.setNewData(data.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.reset_time)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            getCurrentYearMouthInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
