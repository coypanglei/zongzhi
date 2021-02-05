package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.EnterpriseIssueContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class EnterpriseIssuePresenter extends ReworkBasePresenter<EnterpriseIssueContract.Model, EnterpriseIssueContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EnterpriseIssuePresenter(EnterpriseIssueContract.Model model, EnterpriseIssueContract.View rootView) {
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
     * 获取 上报问题列表
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     */
    public void getEnterpriseIssueList(boolean pullToRefresh, boolean isLoadMore) {
        try {
            handlePaging(pullToRefresh, isLoadMore);
            commonGetData(mModel.getEnterpriseIssueList(ignoreNumber, pageSize), mErrorHandler, bean -> {
                mRootView.setData(bean.getList(), isLoadMore);
                handlePaginLoadMore((bean == null || bean.getList() == null) ? 0 : bean.getList().size());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMyEntLeaderInfo() {
        commonGetData(mModel.getMyEntLeaderInfo(), mErrorHandler, o -> {
            mRootView.setLeaderData(o);
        });
    }
}
