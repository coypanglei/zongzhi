package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerEnterpriseReworkComponent;
import com.weique.overhaul.v2.mvp.contract.EnterpriseReworkContract;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EnterpriseReworkPresenter;

import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 企业复工
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_ENTERPRISEREWORKACTIVITY)
public class EnterpriseReworkActivity extends BaseActivity<EnterpriseReworkPresenter> implements EnterpriseReworkContract.View {

    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.code)
    EditText code;


    @BindView(R.id.address)
    EditText address;


    @BindView(R.id.local)
    TextView local;


    @BindView(R.id.legal_person_name)
    EditText legalPersonName;


    @BindView(R.id.legal_person_number)
    EditText legalPersonNumber;


    @BindView(R.id.linkman_name)
    EditText linkmanName;


    @BindView(R.id.linkman_number)
    EditText linkmanNumber;


    @BindView(R.id.reword_time)
    TextView rewordTime;


    @BindView(R.id.rework_amount)
    EditText reworkAmount;


    @BindView(R.id.health_document)
    TextView healthDocument;


    @BindView(R.id.emphasis_amount)
    EditText emphasisAmount;


    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;


    @BindView(R.id.license)
    ImageView license;

    @Inject
    Gson gson;

    private Map<String, Object> paramMap;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEnterpriseReworkComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_enterprise_rework;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        paramMap = new HashMap<>();
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

    @OnClick({R.id.local, R.id.reword_time, R.id.health_document, R.id.license, R.id.toolbar_submit})
    public void onclickListener(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.local:
                    ARouter.getInstance().build(RouterHub.APP_MAPACTIVITY)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_ENTERPRISEREWORKACTIVITY)
                            .navigation();
                    break;
                case R.id.reword_time:

                    break;
                case R.id.health_document:
                    break;
                case R.id.license:
                    break;
                case R.id.toolbar_submit:
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscriber(tag = RouterHub.APP_ENTERPRISEREWORKACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                //定位
                case EventBusConstant.UPDATE_UP_LOCATION:
                    if (eventBusBean.getData() instanceof StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean) {
                        StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                                (StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean) eventBusBean.getData();
                        local.setText(String.format(getResources().getString(R.string.lon_lat), pathBean.getLng(), pathBean.getLat()));
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
