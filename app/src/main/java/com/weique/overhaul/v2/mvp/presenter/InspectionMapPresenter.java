package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.InspectionMapContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2019 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class InspectionMapPresenter extends ReworkBasePresenter<InspectionMapContract.Model, InspectionMapContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public InspectionMapPresenter(InspectionMapContract.Model model, InspectionMapContract.View rootView) {
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

    public void getLatLng(boolean pullToRefresh, boolean isLoadMore, String id) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getLatLng(id), mErrorHandler, itemBean -> {
            try {
                mRootView.setLatLng(itemBean,isLoadMore);
                handlePaginLoadMore((itemBean == null || itemBean.getInspectionWithEvents() == null) ? 0 : itemBean.getInspectionWithEvents().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void setIsInspectionComplete(String taskId, String inspectionRecordId) {
        commonGetData(mModel.setIsInspectionComplete(taskId,inspectionRecordId), mErrorHandler, itemBean -> {
            mRootView.setIsInspectionComplete(itemBean);
        });
    }
}
