package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.StandardAddressContract;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * @author GK
 */
@ActivityScope
public class StandardAddressPresenter extends ReworkBasePresenter<StandardAddressContract.Model, StandardAddressContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject

    AppManager mAppManager;


    @Inject
    public StandardAddressPresenter(StandardAddressContract.Model model, StandardAddressContract.View rootView) {
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
     * 获取用户 的上下级信息
     */
    public void getUpDownRankInfo(boolean pullToRefresh, boolean isLoadMore, String departmentId) {
        try {
            handlePaging(pullToRefresh, isLoadMore);
            commonGetData(mModel.getUpDownRankInfo(departmentId, pageSize, ignoreNumber), mErrorHandler, standardAddressStairBean -> {
                try {
                    mRootView.newData(standardAddressStairBean, isLoadMore);
                    //上级层级 是网格 取值 getAddressType   否则取值 getDepartment
                    List<StandardAddressStairBean.StandardAddressStairBaseBean> beans;
                    if (standardAddressStairBean.getDepartmentUp().getLevel() == StandardAddressStairBean.GRIDDING) {
                        beans = new ArrayList<>(standardAddressStairBean.getAddressType());
                    } else {
                        beans = new ArrayList<>(standardAddressStairBean.getDepartment());
                    }
                    handlePaginLoadMore(beans.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
