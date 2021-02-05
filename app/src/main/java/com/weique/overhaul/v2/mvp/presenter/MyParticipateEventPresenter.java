package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.MyParticipateEventContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/17/2019 16:02
 *
 *
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MyParticipateEventPresenter extends ReworkBasePresenter<MyParticipateEventContract.Model, MyParticipateEventContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyParticipateEventPresenter(MyParticipateEventContract.Model model, MyParticipateEventContract.View rootView) {
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

    public void getTaskList(int status, boolean pullToRefresh, boolean isLoadMore) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getTaskList(status, pageSize, ignoreNumber), mErrorHandler, eventsReportedBean -> {
            mRootView.setNewData(eventsReportedBean.getList(), isLoadMore);
            handlePaginLoadMore((eventsReportedBean == null || eventsReportedBean.getList() == null) ? 0 : eventsReportedBean.getList().size());
        });
    }
}
