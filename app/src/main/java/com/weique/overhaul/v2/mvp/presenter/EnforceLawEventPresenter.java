package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.EnforceLawEventContract;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class EnforceLawEventPresenter extends ReworkBasePresenter<EnforceLawEventContract.Model, EnforceLawEventContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EnforceLawEventPresenter(EnforceLawEventContract.Model model, EnforceLawEventContract.View rootView) {
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
     * 获取网格信息
     */
    public void gridOperatorInformation() {
        commonGetData(mModel.gridOperatorInformation(), mErrorHandler, gridInformationBean -> {
            try {
                if (gridInformationBean != null && StringUtil.isNotNullString(gridInformationBean.getPointsInJSON())) {
                    mRootView.getGridInfoSuccess(gridInformationBean.getPointsInJSON());
                } else {
                    ArmsUtils.makeText("获取用户网格信息失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getIsInGrid() {
        commonGetData(mModel.getIsInGrid(), mErrorHandler, o -> {
            mRootView.setAddressCanChanged(o);
        });
    }
}
