package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.ContradictionAddSearchContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2020 17:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ContradictionAddSearchPresenter extends ReworkBasePresenter<ContradictionAddSearchContract.Model, ContradictionAddSearchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ContradictionAddSearchPresenter(ContradictionAddSearchContract.Model model, ContradictionAddSearchContract.View rootView) {
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

    public void getContradictionAddListData(boolean pullToRefresh, boolean isLoadMore, String departmentId, String name) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getContradictionAddListData(pageSize, ignoreNumber, departmentId, name), mErrorHandler, itemBean -> {
            try {
                mRootView.setContradictionAddListData(itemBean, isLoadMore);
                handlePaginLoadMore((itemBean == null || itemBean.getDepartment() == null) ? 0 : itemBean.getDepartment().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
