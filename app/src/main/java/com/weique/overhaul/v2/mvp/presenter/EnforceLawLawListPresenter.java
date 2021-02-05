package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.EnforceLawLawListContract;


/**
 * ================================================
 * Description:
 * ================================================
 *
 * @author GreatKing
 */
@FragmentScope
public class EnforceLawLawListPresenter extends ReworkBasePresenter<EnforceLawLawListContract.Model, EnforceLawLawListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EnforceLawLawListPresenter(EnforceLawLawListContract.Model model, EnforceLawLawListContract.View rootView) {
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
     * @param pullToRefresh
     * @param isLoadMore
     * @param name          keyword
     * @param type          事件来源
     * @param sortType      状态
     */
    public void getCases(boolean pullToRefresh, boolean isLoadMore,
                         String name, String type, String sortType) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getCaseList(name, type, sortType, pageSize, ignoreNumber), mErrorHandler, bean -> {
            try {
                mRootView.setNewData(bean, isLoadMore);
                handlePaginLoadMore((bean == null || bean == null) ? 0 : bean.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取枚举值
     */
    public void getCommonEnums(String type) {
        commonGetData(mModel.getCommonEnums(type), mErrorHandler, bean -> {
            try {
                mRootView.getCommonEnums(bean, type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
