package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.InspectionListContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2019 11:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class InspectionListPresenter extends ReworkBasePresenter<InspectionListContract.Model, InspectionListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public InspectionListPresenter(InspectionListContract.Model model, InspectionListContract.View rootView) {
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

    public void GetDepartment() {
        commonGetData(mModel.getGetDepartment(), mErrorHandler, gridInformationBean -> {
            mRootView.gotoMapTv(gridInformationBean);
        });
    }

    public void getInspectionListData(boolean pullToRefresh, boolean isLoadMore) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getInspectionListData(pageSize,ignoreNumber), mErrorHandler, itemBean -> {
            try {
                mRootView.setInspectionListData(itemBean, isLoadMore);
                handlePaginLoadMore((itemBean == null || itemBean.getInspectionRecord() == null) ? 0 : itemBean.getInspectionRecord().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
