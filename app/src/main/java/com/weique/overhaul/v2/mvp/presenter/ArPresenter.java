package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.provider.Settings;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.mvp.contract.ArContract;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/22/2020 11:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ArPresenter extends BasePresenter<ArContract.Model, ArContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ArPresenter(ArContract.Model model, ArContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getPermissionCamera() {
        try {
            CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
                try {
                    if (AppUtils.isLocationEnabled(mRootView.getActivity())) {
                        mRootView.permissionSuccess();
                    } else {
                        new CommonDialog.Builder(mRootView.getActivity()).setTitle("提醒")
                                .setContent("需要打开GPS 定位服务")
                                .setPositiveButton("去设置", (v, commonDialog) -> {
                                    //跳转GPS设置界面
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    mRootView.getActivity().startActivity(intent);
                                })
                                .setNegativeButton("取消", null).create().show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, Constant.LOCATION_CAMERA_LOCATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
