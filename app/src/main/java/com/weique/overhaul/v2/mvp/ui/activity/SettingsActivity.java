package com.weique.overhaul.v2.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppProgressListenerUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.DownLoadUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerSettingsComponent;
import com.weique.overhaul.v2.mvp.contract.SettingsContract;
import com.weique.overhaul.v2.mvp.model.entity.NewVersionInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.SettingsPresenter;
import com.weique.overhaul.v2.mvp.ui.dialogFragment.DialogFragmentHelper;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.weique.overhaul.v2.mvp.ui.popupwindow.VersionDialog;

import org.simple.eventbus.Subscriber;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressManager;
import okhttp3.OkHttpClient;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 个人中心--设置
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_SETTINGSACTIVITY)
public class SettingsActivity extends BaseActivity<SettingsPresenter> implements SettingsContract.View {


    @BindView(R.id.logout)
    Button logout;
    @BindView(R.id.version_text)
    TextView versionText;
    @BindView(R.id.update_version)
    LinearLayout updateVersion;
    @BindView(R.id.reset_password)
    LinearLayout resetPassword;


    private NewVersionInfoBean newVersionInfoBean;

    @BindArray(R.array.data_statistic)
    String[] data;

    @Inject
    OkHttpClient okHttpClient;

    private VersionDialog dialog;
    private String mUrl;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_settings;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.setting));
        versionText.setText(AppUtils.getVersionName(this.getApplicationContext()));
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
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

    @OnClick({R.id.logout, R.id.update_version, R.id.reset_password, R.id.ll_version})
    public void onViewClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.logout:
                    new CommonDialog.Builder(this).setContent("确定退出")
                            .setPositiveButton((view, commonDialog) -> {
                               /* Intent intent2 = new Intent(SettingsActivity.this, MainService.class);
                                stopService(intent2);*/
                                AppUtils.logout();
//                                JPushInterface.setAlias(this, -1, "");
//                                JPushInterface.deleteAlias(this, -1);
//                                JPushInterface.stopPush(this);
                            }).create().show();
                    break;
                case R.id.update_version:
//                    UpdateUtil.appUpdate(this, true);
                    mPresenter.getAppVersionInfo();
                    break;
                case R.id.reset_password:
                    ARouter.getInstance().build(RouterHub.APP_RESTPASSWORDACTIVITY).navigation();
//                    ARouter.getInstance().build(RouterHub.APP_PIECHARTACTIVITY).navigation();
                    break;
                //版本信息
                case R.id.ll_version:
                    mPresenter.getAppVersionInfoCode();

                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public void showVersionDialog(NewVersionInfoBean newVersionInfoBean) {
        this.newVersionInfoBean = newVersionInfoBean;
        mUrl = new String(newVersionInfoBean.getApkUrl());
        dialog = new VersionDialog(this, newVersionInfoBean);
        if (StringUtil.isNullString(mUrl) ||
                !mUrl.endsWith(Constant.APK)) {
            ArmsUtils.makeText("安装包异常");
            dialog.dismiss();
            return;
        }
        dialog.setOnVersionDialogClickLisenter(() -> {
            AppUtils.checkUrl(mUrl)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            ProgressManager.getInstance().addResponseListener(mUrl, AppProgressListenerUtil.getApkDownloadListener());
                            DownLoadUtil.downloadStart(this, mUrl, okHttpClient, newVersionInfoBean);
                        } else {
                            ArmsUtils.makeText("安装包异常，无法下载");
                            dialog.dismiss();
                        }
                    });
        });
        dialog.show();
    }

    /**
     * 设置进度
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_SETTINGSACTIVITY)
    public void onEventBusCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.DOWNLOAD_PREGRESS:
                    int progress = (int) eventBusBean.getData();
                    if (dialog != null) {
                        dialog.setUpdateProgress(progress);
                    }
                    break;
                case EventBusConstant.DOWNLOAD_PREGRESS_OK:
                    if (newVersionInfoBean != null && !dialog.isShowing()) {
                        new CommonDialog.Builder(this)
                                .setContent("安装包下载完成去安装")
                                .setPositiveButton((v, commonDialog) -> {
                                    File file = Constant.getNewVersionApkFile(newVersionInfoBean.getVersionName());
                                    if (file.exists()) {
                                        com.blankj.utilcode.util.AppUtils.installApp(file);
                                    } else {
                                        ArmsUtils.makeText("安装包不存在");
                                    }
                                }).create().show();
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDialogGone() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void setCodeImg(Bitmap bitmap, String versionName) {
        try {
            View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_help_file, null);
            DialogFragmentHelper.showImGDialog(getSupportFragmentManager(), view, true, null);
            TextView tvVison = view.findViewById(R.id.tv_version);
            ImageView imageView = view.findViewById(R.id.iv_code);
            imageView.setImageBitmap(bitmap);
            tvVison.setText(versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUrl = null;
    }
}
