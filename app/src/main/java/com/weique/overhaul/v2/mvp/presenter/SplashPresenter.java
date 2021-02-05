package com.weique.overhaul.v2.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.mvp.contract.SplashContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 * @author GreatKing
 */
@ActivityScope
public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public SplashPresenter(SplashContract.Model model, SplashContract.View rootView) {
        super(model, rootView);
    }

    @SuppressLint("CheckResult")
    public void startDownTime() {
        try {
            mModel.downTime(5L)
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(aLong -> mRootView.updateTime(aLong));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("CheckResult")
    public void getPermission() {
        try {
            CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
                startDownTime();
            }, false, Constant.PERMISSIONS_LIST_SPLASH_);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
