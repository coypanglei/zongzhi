package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.newlibrary.adapter.base.ProviderBinderAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerPersonalWorkComponent;
import com.weique.overhaul.v2.mvp.contract.PersonalWorkContract;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;
import com.weique.overhaul.v2.mvp.model.entity.WorkLogBean;
import com.weique.overhaul.v2.mvp.presenter.PersonalWorkPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/05/2021 15:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.APP_PERSONAL_ACTIVITY, name = "个人工作日志")
public class PersonalWorkActivity extends BaseActivity<PersonalWorkPresenter> implements PersonalWorkContract.View {
    /**
     * 上个界面跳转的参数
     */
    @Autowired(name = Constant.COMMON_COLLECTION)
    CommonCollectBean commonCollectBean;
    @BindView(R.id.swipe_refresh)
    VerticalSwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.tv_report)
    TextView tvReport;
    @BindView(R.id.tv_visit)
    TextView tvVisit;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.select_data)
    TextView selectData;
    /**
     * 选择时间
     */
    private String time;

    private TimePickerView startTimePickerView;

    private ProviderBinderAdapter baseBinderAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPersonalWorkComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_personal_work; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    /**
     * 初始化 recyclerview
     */
    private void initRv() {
        try {
            tvName.setText(UserInfoUtil.getUserInfo().getName());
            baseBinderAdapter = new ProviderBinderAdapter();
            ArmsUtils.configRecyclerView(rv, new LinearLayoutManager(this));
            rv.setAdapter(baseBinderAdapter);
            /*
             *  获取数据赋值
             */
            assert mPresenter != null;
            /*
             *  初始化数据
             */
            mPresenter.addItemBinders(commonCollectBean);
            mPresenter.getAllData(commonCollectBean, true);
            /*
             *  刷新
             */
            swipeRefresh.setOnRefreshListener(() -> mPresenter.getAllData(commonCollectBean, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            baseBinderAdapter.getLoadMoreModule().loadMoreEnd(true);
        } else {
            baseBinderAdapter.getLoadMoreModule().loadMoreComplete();
        }
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle(commonCollectBean.getTitle());
        initTimePicker();
        initRv();
    }

    @Override
    public void showLoading() {
        showProgressDialog();
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
    public ProviderBinderAdapter getBinderAdapter() {
        return baseBinderAdapter;
    }

    @Override
    public String getTime() {
        return time;
    }


    @OnClick({R.id.select_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_data:
                if (!startTimePickerView.isShowing()) {
                    startTimePickerView.show();
                }
                break;
            default:
                break;
        }
    }


    /**
     * 初始化时间选择器
     */
    private void initTimePicker() {

        startTimePickerView = PickerViewUtil.selectPickerTimeToday(this, Constant.YMD1, (date, v) -> {
            try {
                //开始时间
                SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
                selectData.setText(format.format(date));
                time = format.format(date);
                mPresenter.getAllData(commonCollectBean, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //开始时间 当天的时间
        Calendar selectedDate = Calendar.getInstance();
        //开始时间
        SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
        selectData.setText(format.format(selectedDate.getTime()));
        time = format.format(selectedDate.getTime());
    }

    @Override
    public void getWorkLogBean(WorkLogBean workLogBean) {
        try {
            if (ObjectUtils.isEmpty(workLogBean)) {
                tvCollection.setText("0");
                tvReport.setText("0");
                tvVisit.setText("0");
            } else {
                tvCollection.setText(workLogBean.getElementCount());
                tvReport.setText(workLogBean.getEventCount());
                tvVisit.setText(workLogBean.getInspectionInterView());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
