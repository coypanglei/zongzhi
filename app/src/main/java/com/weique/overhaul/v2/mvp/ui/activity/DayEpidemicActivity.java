package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerDayEpidemicComponent;
import com.weique.overhaul.v2.mvp.contract.DayEpidemicContract;
import com.weique.overhaul.v2.mvp.presenter.DayEpidemicPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 *
 */
@Route(path = RouterHub.APP_DAYEPIDEMICACTIVITY)
public class DayEpidemicActivity extends BaseActivity<DayEpidemicPresenter> implements DayEpidemicContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_submit)
    RelativeLayout toolbarSubmit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.work_people_count)
    EditText workPeopleCount;
    @BindView(R.id.other_city_count)
    EditText otherCityCount;
    @BindView(R.id.other_province_count)
    EditText otherProvinceCount;
    @BindView(R.id.info_hubei)
    EditText infoHubei;
    @BindView(R.id.no_problem_count)
    EditText noProblemCount;
    @BindView(R.id.hot_count)
    EditText hotCount;
    @BindView(R.id.already_look_count)
    EditText alreadyLookCount;
    @BindView(R.id.suspected_count)
    EditText suspectedCount;
    @BindView(R.id.confirmed_count)
    EditText confirmedCount;

    private Map<String, Object> map;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDayEpidemicComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_day_epidemic;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        map = new HashMap<>();
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


    @OnClick(R.id.toolbar_submit)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            if (TextUtils.isEmpty(workPeopleCount.getText().toString().trim())) {
                ArmsUtils.makeText("请输入复工人数");
            } else if (TextUtils.isEmpty(otherCityCount.getText().toString().trim())) {
                ArmsUtils.makeText("请输入外市人数");
            } else if (TextUtils.isEmpty(otherProvinceCount.getText().toString().trim())) {
                ArmsUtils.makeText("请输入外省人数");
            } else if (TextUtils.isEmpty(infoHubei.getText().toString().trim())) {
                ArmsUtils.makeText("请输入其中:湖北省、浙江省温州市人数");
            } else if (TextUtils.isEmpty(noProblemCount.getText().toString().trim())) {
                ArmsUtils.makeText("请输入无异常情况人数");
            } else if (TextUtils.isEmpty(hotCount.getText().toString().trim())) {
                ArmsUtils.makeText("请输入发热咳嗽等情况人数");
            } else if (TextUtils.isEmpty(alreadyLookCount.getText().toString().trim())) {
                ArmsUtils.makeText("请输入隔离观察人数");
            } else if (TextUtils.isEmpty(suspectedCount.getText().toString().trim())) {
                ArmsUtils.makeText("请输入疑似病例");
            } else if (TextUtils.isEmpty(confirmedCount.getText().toString().trim())) {
                ArmsUtils.makeText("请输入确诊病例");
            } else {
                map.put("", workPeopleCount.getText().toString().trim());
                map.put("", otherCityCount.getText().toString().trim());
                map.put("", otherProvinceCount.getText().toString().trim());
                map.put("", infoHubei.getText().toString().trim());
                map.put("", noProblemCount.getText().toString().trim());
                map.put("", hotCount.getText().toString().trim());
                map.put("", alreadyLookCount.getText().toString().trim());
                map.put("", suspectedCount.getText().toString().trim());
                map.put("", confirmedCount.getText().toString().trim());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
