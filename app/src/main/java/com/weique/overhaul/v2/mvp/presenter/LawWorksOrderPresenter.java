package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.LawWorksOrderContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/22/2020 15:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class LawWorksOrderPresenter extends ReworkBasePresenter<LawWorksOrderContract.Model, LawWorksOrderContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LawWorksOrderPresenter(LawWorksOrderContract.Model model, LawWorksOrderContract.View rootView) {
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
     * 获取订单列表
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param type          type
     */
    public void getOrderList(boolean pullToRefresh, boolean isLoadMore, int type) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getOrderList(pageSize, ignoreNumber, type), mErrorHandler, lawWorksOrderListBeans -> {
            mRootView.setNewOrderData(lawWorksOrderListBeans,isLoadMore);
            handlePaginLoadMore(lawWorksOrderListBeans == null ? 0 : lawWorksOrderListBeans.size());
        });
    }
}
