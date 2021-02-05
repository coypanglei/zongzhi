package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.InformationTypeFirstContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class InformationTypeFirstPresenter extends ReworkBasePresenter<InformationTypeFirstContract.Model, InformationTypeFirstContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public InformationTypeFirstPresenter(InformationTypeFirstContract.Model model, InformationTypeFirstContract.View rootView) {
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
     * @param id id
     */
    public void getElementTypes(String id, boolean needAdd) {
        commonGetData(mModel.getElementTypes(id), mErrorHandler, informationTypeOneSecondBean -> {
            try {
                if (informationTypeOneSecondBean == null ||
                        informationTypeOneSecondBean.getElementTypeList() == null ||
                        informationTypeOneSecondBean.getElementTypeList().size() <= 0) {
                    ArmsUtils.makeText("无资源信息");
                    return;
                }
                mRootView.setDataTree(informationTypeOneSecondBean.getElementTypeList(), needAdd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
