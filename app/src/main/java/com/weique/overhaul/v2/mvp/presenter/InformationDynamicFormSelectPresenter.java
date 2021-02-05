package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import androidx.annotation.Nullable;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.InformationDynamicFormSelectContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * 动态查询表单
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class InformationDynamicFormSelectPresenter extends
        ReworkBasePresenter<InformationDynamicFormSelectContract.Model, InformationDynamicFormSelectContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public InformationDynamicFormSelectPresenter(InformationDynamicFormSelectContract.Model model, InformationDynamicFormSelectContract.View rootView) {
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

    public void getDetailInfoById(String elementId, String elementTypeId) {
        commonGetData(mModel.getElementInfo(elementId, elementTypeId), mErrorHandler, o -> {
            mRootView.setData(o);
        });
    }

    public void delete(String elementId, String elementTypeId) {
        commonGetData(mModel.delete(elementId, elementTypeId), mErrorHandler, o -> {
            mRootView.deleteSuccess();
        });
    }

    /**
     * getDepartment 获取网格信息
     *
     * @param departmentId departmentId 为空时 会 自动  填写 当前登录用户的 departmentId
     */
    public void getDepartment(@Nullable String departmentId) {
        commonGetData(mModel.getGetDepartment(departmentId), mErrorHandler, gridInformationBean -> {
            mRootView.gotoMapTv(gridInformationBean);
        });
    }
}
