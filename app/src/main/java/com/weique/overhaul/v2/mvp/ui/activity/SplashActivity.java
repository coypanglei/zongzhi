package com.weique.overhaul.v2.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ACacheConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.MetaDataUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerSplashComponent;
import com.weique.overhaul.v2.mvp.contract.SplashContract;
import com.weique.overhaul.v2.mvp.presenter.SplashPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_SPLASHACTIVITY)
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @BindView(R.id.down_time)
    TextView downTime;
    @BindView(R.id.jump_layout)
    LinearLayout jumpLayout;
    @BindView(R.id._app_name)
    TextView appName;
    @BindView(R.id.version_number_information)
    TextView versionNumberInformation;
    @BindView(R.id.splash_icon)
    ImageView splashIcon;

    @Inject
    RxPermissions mRxPermissions;
    @Inject
    ACache aCache;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSplashComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //首次启动 Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT 为 0，再次点击图标启动时就不为零了
        try {
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
            mPresenter.getPermission();
            appName.setText(StringUtil.setText(MetaDataUtil.getAppName()));
            versionNumberInformation.setText(StringUtil.setText(MetaDataUtil.getCopyright()));
            splashIcon.setImageResource(MetaDataUtil.getSplashIcon());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.jump_layout})
    public void onViewClick(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            if (view.getId() == R.id.jump_layout) {
                jumpPage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新倒计时
     */
    @Override
    public void updateTime(Long second) {
        try {
            if (second <= 0) {
                jumpPage();
                return;
            }
            downTime.setText(String.format(ArmsUtils.getString(this, R.string.placeholder_s), second + ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }


    @Override
    public void launchActivityByRouter(@NonNull String path) {
        ARouterUtils.navigation(this, path);
        killMyself();
    }

    /**
     * 条界面
     */
    @Override
    public void jumpPage() {
        //第一次启动
        try {
            if (StringUtil.isNullString(aCache.getAsString(ACacheConstant.IS_FIRST_START_APP))) {
                //引导页
                ARouterUtils.navigation(this, RouterHub.APP_GUIDEACTIVITY);
            } else {
                //首页
                if (UserInfoUtil.getUserInfo() != null) {
                    ARouterUtils.navigation(this, RouterHub.APP_MAINACTIVITY);
                } else {
                    //登录页
                    ARouterUtils.navigation(this, RouterHub.APP_LOGINACTIVITY);
                }
            }
            killMyself();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
