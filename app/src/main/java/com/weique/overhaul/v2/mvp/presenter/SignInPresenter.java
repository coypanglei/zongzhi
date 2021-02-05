package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.SignInContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * @author GreatKing
 */
@ActivityScope
public class SignInPresenter extends ReworkBasePresenter<SignInContract.Model, SignInContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SignInPresenter(SignInContract.Model model, SignInContract.View rootView) {
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


    public void setSign(String PointsInJson, String Address,boolean isInGrid,int signActive) {
        commonGetData(mModel.setSign(PointsInJson, Address,isInGrid,signActive), mErrorHandler, s -> {
            mRootView.setSignData(s);
        });
    }

    public void getSignStatus() {
        commonGetData( mModel.getSignStatus(), mErrorHandler, b ->{
            mRootView.setSignBtnStatus(b);
        });
    }

    public void GetDepartment() {
        commonGetData(mModel.getGetDepartment(), mErrorHandler, gridInformationBean -> {
            mRootView.setReseau(gridInformationBean);
        });
    }
}
