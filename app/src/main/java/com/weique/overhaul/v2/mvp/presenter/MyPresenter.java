package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.provider.Settings;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.mvp.contract.MyContract;
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
 *
 * @author GK
 */
@FragmentScope
public class MyPresenter extends ReworkBasePresenter<MyContract.Model, MyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public MyPresenter(MyContract.Model model, MyContract.View rootView) {
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

    /**
     *
     */
    public void getPermission() {
        try {
            CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
                if (AppUtils.isLocationEnabled(mRootView.getActivity())) {
                    ARouter.getInstance().build(RouterHub.APP_SIGNINACTIVITY)
                            .navigation();
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
            }, Constant.PERMISSIONS_LOCATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取积分总和
     */
    public void setIntegral() {
        Map<String, Object> map = new HashMap<>();
        commonGetDataNoProgress(mModel.getIntegral(map), mErrorHandler, Integral -> {
            mRootView.getIntegral(Integral);
        });
    }

}
