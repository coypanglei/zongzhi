package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.ResourceAuditListContract;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceAuditBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author Administrator
 */
@ActivityScope
public class ResourceAuditListPresenter extends ReworkBasePresenter<ResourceAuditListContract.Model, ResourceAuditListContract.View> {
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
    public ResourceAuditListPresenter(ResourceAuditListContract.Model model, ResourceAuditListContract.View rootView) {
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

    public void getList(boolean pullToRefresh, boolean isLoadMore, Object state) {
        handlePaging(pullToRefresh, isLoadMore);
        Map<String, Object> map = new HashMap<>();
        map.put("status", state);
        map.put("pageSize", pageSize);
        map.put("ignoreNumber", ignoreNumber);
        Observable<BaseBean<ResourceAuditBean>> observable = iRepositoryManager.
                obtainRetrofitService(InformationService.class).
                getAuditList(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler, o -> {
            mRootView.setList(o.getList(),isLoadMore);
            handlePaginLoadMore(o.getTotalCount());
        });
    }
}
