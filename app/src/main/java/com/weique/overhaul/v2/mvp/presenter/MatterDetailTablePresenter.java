package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.MatterDetailTableContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author Administrator
 */
@FragmentScope
public class MatterDetailTablePresenter extends ReworkBasePresenter<MatterDetailTableContract.Model, MatterDetailTableContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MatterDetailTablePresenter(MatterDetailTableContract.Model model, MatterDetailTableContract.View rootView) {
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

    public void getDetailById(String eventRecordId, String id, int flag) {
        commonGetData(mModel.getDetailById(eventRecordId, id, flag), mErrorHandler, o -> {
            mRootView.setDetail(o);
        });
    }

    public void getProgress(String eventRecordId, String id, int flag) {
        commonGetData(mModel.getProgress(eventRecordId, id, flag), mErrorHandler, o -> {
            mRootView.setProgress(o);
        });
    }

    public void getDuBan(String eventRecordId, String id, int flag) {
        commonGetData(mModel.getDuBan(eventRecordId, id, flag), mErrorHandler, o -> {
            mRootView.setDuBan(o);
        });
    }

    public void getCuiBan(String eventRecordId, String id, int flag) {
        commonGetData(mModel.getCuiBan(eventRecordId, id, flag), mErrorHandler, o -> {
            mRootView.setCuiBan(o);
        });
    }
}
