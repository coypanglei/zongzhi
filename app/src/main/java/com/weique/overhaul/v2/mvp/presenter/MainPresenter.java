package com.weique.overhaul.v2.mvp.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.service.localtion.LocSdkClient;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.MetaDataUtil;
import com.weique.overhaul.v2.mvp.contract.MainContract;
import com.weique.overhaul.v2.mvp.model.entity.NewVersionInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import me.jessyan.progressmanager.body.ProgressInfo;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class MainPresenter extends ReworkBasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    Gson gson;
    private ProgressInfo mLastDownloadingInfo;
    private String mLongAndLatJson = "";
    private Disposable subscribe;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
    }

    public void automaticCheckInOrder(String PointsInJson, String Address) {
        commonGetData(mModel.automaticCheckInOrder(PointsInJson, Address), mErrorHandler, s -> {
            mRootView.setSignData(s);
        });
    }

    /**
     * 获取最新版本
     */
    public void getAppVersionInfo() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("APPId", "");
//            map.put("CPUType", PhoneInfoUtil.getCpuName());
            map.put("Channel", MetaDataUtil.getChannelName());
            commonGetData(mModel.getAppVersionInfo(map), mErrorHandler, newVersionInfoBean -> {
                if (newVersionInfoBean.getVersionNumber() > AppUtils.getVersionCode(mRootView.getActivity())) {
                    //处理url  添加 域名  前缀
                    newVersionInfoBean.setApkUrl(GlideUtil.handleUrl(mRootView.getActivity(), newVersionInfoBean.getApkUrl()));
                    mRootView.showVersionDialog(newVersionInfoBean);
                } else {
                    mRootView.getMessageStatus();
                    EventBus.getDefault().post(new EventBusBean(EventBusConstant.GET_NOTICE), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取信息状态
     */
    public void getMessageStatus() {
        commonGetData(mModel.getMessageStatus(), mErrorHandler, o -> {
            mRootView.setHasNewMessage(o);
        });
    }


    /**
     * @param newVersionInfoBean 获取读写权限
     */
    public void getPermission(NewVersionInfoBean newVersionInfoBean) {
        try {

            //huo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasInstallPermission = mRootView.getActivity().getPackageManager().canRequestPackageInstalls();
                if (!hasInstallPermission) {
                    getInstallPermission();
                    return;
                }
            }
            CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
                try {
                    mRootView.showStartDownLoad(newVersionInfoBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, Constant.READ_WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取相机权限
     */
    public void getPermissionCamera() {
        CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
            try {
                ARouter.getInstance().build(RouterHub.APP_CAPTUREACTIVITY).navigation(mRootView.getActivity(), Constant.REQUEST_CODE_SCAN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Manifest.permission.CAMERA);
    }

    /**
     * 安装权限
     */
    private void getInstallPermission() {
        PermissionUtil.requestPermission(new PermissionUtil.RequestPermission() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onRequestPermissionSuccess() {
                startInstallPermissionSettingActivity();
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                startInstallPermissionSettingActivity();
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                startInstallPermissionSettingActivity();
            }
        }, new RxPermissions((FragmentActivity) mRootView.getActivity()), mErrorHandler, Manifest.permission.REQUEST_INSTALL_PACKAGES);
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + mRootView.getActivity().getPackageName()));
        mRootView.getActivity().startActivityForResult(intent, 1);
    }


    public void getPermissionGps() {
        CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
            try {
                if (AppUtils.isLocationEnabled(mRootView.getActivity())) {
                    startRecordTrack();
//                    mRootView.registerReceiver();
                } else {
                    mRootView.showOpenGpsDialog();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Constant.PERMISSIONS_LOCATION);
    }

    @SuppressLint("CheckResult")
    public void startRecordTrack() {
        try {
            if (subscribe != null) {
                if (!subscribe.isDisposed()) {
                    return;
                }
                subscribe.dispose();
            }
            subscribe = Observable.timer(4 * 60, TimeUnit.SECONDS)
                    .repeat()
                    .subscribe(aLong -> {
                        try {
                            BDLocation bdLocation =
                                    LocSdkClient.getInstance(mApplication).getLocationStart()
                                            .getLastKnownLocation();
                            if (bdLocation != null) {
                                TourVistLonAndLatBean lonAndLat = new TourVistLonAndLatBean();
                                lonAndLat.setLat(bdLocation.getLatitude());
                                lonAndLat.setLng(bdLocation.getLongitude());
                                String longAndLatJson = gson.toJson(lonAndLat);
//                        if (!mLongAndLatJson.equals(longAndLatJson)) {
                                mLongAndLatJson = longAndLatJson;
                                automaticCheckInOrder(longAndLatJson, bdLocation.getAddrStr());
//                        }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 苏格通用户绑定到综治
     *
     * @param id 苏格通 用户id
     */
    public void sgtToZzBinding(String id) {
        commonGetData(mModel.sgtToZzBinding(id), mErrorHandler, o -> {

        });
    }
}
