package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.KnowledgeBaseContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * @author GreatKing
 */
@ActivityScope
public class KnowledgeBasePresenter extends ReworkBasePresenter<KnowledgeBaseContract.Model, KnowledgeBaseContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public KnowledgeBasePresenter(KnowledgeBaseContract.Model model, KnowledgeBaseContract.View rootView) {
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

    public void getTypeListData() {
        commonGetData(mModel.getTypeListData(), mErrorHandler, gridInformationBean -> {
            mRootView.setTypeList(gridInformationBean);
        });
    }

    public void getLabelsListData() {
        commonGetData(mModel.getLabelsListData(), mErrorHandler, gridInformationBean -> {
            mRootView.setLabelList(gridInformationBean);
        });
    }


    public void getKnowledgeBaseListData(boolean pullToRefresh, boolean isLoadMore, String TId, String LId) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getKnowledgeBaseListData(pageSize, ignoreNumber, TId, LId), mErrorHandler, itemBean -> {
            mRootView.setKnowledgeBaseListData(itemBean, isLoadMore);
            handlePaginLoadMore((itemBean == null || itemBean.getList() == null) ? 0 : itemBean.getList().size());
        });
    }
}
