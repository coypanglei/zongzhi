package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.AnswerContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * @author GK
 */
@ActivityScope
public class AnswerPresenter extends ReworkBasePresenter<AnswerContract.Model, AnswerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AnswerPresenter(AnswerContract.Model model, AnswerContract.View rootView) {
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

    public void getPartyAnswerData(boolean pullToRefresh, boolean isLoadMore) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getPartyAnswerData(pageSize, ignoreNumber), mErrorHandler, itemBean -> {
            try {
                mRootView.setAnswerListData(itemBean,isLoadMore);
                handlePaginLoadMore((itemBean == null || itemBean.getSubject() == null) ? 0 : itemBean.getSubject().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
