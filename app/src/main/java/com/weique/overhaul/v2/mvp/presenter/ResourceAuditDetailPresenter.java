package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.ResourceAuditDetailContract;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.api.service.StandardAddressService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceAuditDetailBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * @author Administrator
 */
@ActivityScope
public class ResourceAuditDetailPresenter extends ReworkBasePresenter<ResourceAuditDetailContract.Model, ResourceAuditDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    IRepositoryManager iRepositoryManager;

    @Inject
    public ResourceAuditDetailPresenter(ResourceAuditDetailContract.Model model, ResourceAuditDetailContract.View rootView) {
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
     * 获取详情数据
     *
     * @param id id
     */
    public void getDetailById(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("Id", id);
        Observable<BaseBean<ResourceAuditDetailBean>> observable = iRepositoryManager.
                obtainRetrofitService(InformationService.class).
                getDetailById(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler, o -> {
            mRootView.setDetail(o);
        });
    }

    public void getDynamicFormByElementId(String elementId) {
        Map<String, Object> map = new HashMap<>();
        map.put("ElementId", elementId);
        Observable<BaseBean<String>> observable = iRepositoryManager.obtainRetrofitService(StandardAddressService.class)
                .getDataStructureInJson(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler, s -> {
            mRootView.setDynamicForm(s);
        });
    }

    /**
     * @param id 处理点击
     */
    public void handleInfo(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("Id", id);
        Observable<BaseBean<Object>> observable = iRepositoryManager.obtainRetrofitService(InformationService.class)
                .handle(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler, s -> {
            mRootView.setHandleInfo(s);
        });
    }

    /**
     * 拒绝
     *
     * @param id       id
     * @param contents contents
     */
    public void reject(String id, String contents) {
        Map<String, Object> map = new HashMap<>();
        map.put("Id", id);
        map.put("Reason", contents);
        Observable<BaseBean<Object>> observable =
                iRepositoryManager
                        .obtainRetrofitService(InformationService.class)
                        .reject(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler, s -> {
            mRootView.setHandleInfo(s);
        });
    }

    /**
     * @param id
     * @param contents
     */
    public void upTell(String id, String contents) {
        Map<String, Object> map = new HashMap<>();
        map.put("Id", id);
        map.put("Description", contents);
        Observable<BaseBean<Object>> observable = iRepositoryManager.obtainRetrofitService(InformationService.class)
                .submitCheckElementRecord(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler, s -> {
            mRootView.setHandleInfo(s);
        });
    }
}
