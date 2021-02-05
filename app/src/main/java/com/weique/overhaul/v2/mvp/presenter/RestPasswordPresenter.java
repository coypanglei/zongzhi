package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.RestPasswordContract;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class RestPasswordPresenter extends ReworkBasePresenter<RestPasswordContract.Model, RestPasswordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RestPasswordPresenter(RestPasswordContract.Model model, RestPasswordContract.View rootView) {
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

    public void resetPassword(String oldSHA, String newpSHA) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("OldPwd", oldSHA);
            map.put("Pwd", newpSHA);
            commonGetData(mModel.resetPassword(map), mErrorHandler, o -> {
                new CommonDialog.Builder(mRootView.getActivity())
                        .setContent("密码重置成功 请重新登录")
                        .setCancelable(false)
                        .setNegativeBtnShow(false)
                        .setPositiveButton("重新登录", (v, commonDialog) -> {
                            AppUtils.logout();
                        }).create().show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
