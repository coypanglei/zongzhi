package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.mvp.contract.LawWorksInformContract;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksInformBean;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@FragmentScope
public class LawWorksInformPresenter extends ReworkBasePresenter<LawWorksInformContract.Model, LawWorksInformContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LawWorksInformPresenter(LawWorksInformContract.Model model, LawWorksInformContract.View rootView) {
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
     * 获取通知列表
     *
     * @param pullToRefresh
     * @param isLoadMore
     */
    public void getInformList(boolean pullToRefresh, boolean isLoadMore) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getInformList(pageSize, ignoreNumber), mErrorHandler, itemBean -> {
            try {
                mRootView.setNewData(itemBean, isLoadMore);
                handlePaginLoadMore((itemBean == null) ? 0 : itemBean.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 通知详情
     *
     * @param id
     */
    public void getInformDetail(String id) {
        commonGetData(mModel.getInformDetail(id), mErrorHandler, itemBean -> {
            try {
                ARouter.getInstance().build(RouterHub.APP_COMMONWEBVIEWACTIVITY)
                        .withString(ARouerConstant.TITLE, itemBean.getTitle())
                        .withString(ARouerConstant.WEB_CONTENT, itemBean.getContent())
                        .withString(ARouerConstant.SOURCE, RouterHub.APP_LAWWORKSINFORMFRAGMENT)
                        .navigation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
