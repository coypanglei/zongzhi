package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.BusinessQuestionContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2020 10:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class BusinessQuestionPresenter extends ReworkBasePresenter<BusinessQuestionContract.Model, BusinessQuestionContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public BusinessQuestionPresenter(BusinessQuestionContract.Model model, BusinessQuestionContract.View rootView) {
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

    public void getBusinessQuestionData(boolean pullToRefresh, boolean isLoadMore) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getBusinessQuestionData(pageSize,ignoreNumber), mErrorHandler, itemBean -> {
            try {
                mRootView.setBusinessQuestionData(itemBean, isLoadMore);
                handlePaginLoadMore((itemBean == null || itemBean.getList() == null) ? 0 : itemBean.getList().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
