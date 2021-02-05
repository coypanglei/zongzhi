package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.PartyCenterTopTableContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author  GK
 */
@FragmentScope
public class PartyCenterTopTablePresenter extends ReworkBasePresenter<PartyCenterTopTableContract.Model, PartyCenterTopTableContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PartyCenterTopTablePresenter(PartyCenterTopTableContract.Model model, PartyCenterTopTableContract.View rootView) {
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
     * 获取党建中心首页数据
     *
     * @param pullToRefresh 是否是下拉刷新
     * @param isLoadMore    是否是加载更多
     */
    public void getPartyMainData(boolean pullToRefresh, boolean isLoadMore, int type) {
        try {
            commonGetData(mModel.getPartyTableData(type), mErrorHandler, itemBean -> mRootView.getFragment().setData(itemBean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
