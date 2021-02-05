package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.PatrolsDetailContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2020 16:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class PatrolsDetailPresenter extends ReworkBasePresenter<PatrolsDetailContract.Model, PatrolsDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PatrolsDetailPresenter(PatrolsDetailContract.Model model, PatrolsDetailContract.View rootView) {
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

    public void getPatrolsDetailListData(boolean pullToRefresh, boolean isLoadMore, String id) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getPatrolsDetailListData(pageSize,ignoreNumber, id), mErrorHandler, itemBean -> {
            try {
                mRootView.setPatrolsDetailListData(itemBean, isLoadMore);
                handlePaginLoadMore((itemBean == null || itemBean.getElements() == null) ? 0 : itemBean.getElements().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void GetDepartment() {
        commonGetData(mModel.getGetDepartment(), mErrorHandler, gridInformationBean -> {
            mRootView.gotoMapTv(gridInformationBean);
        });
    }
}
