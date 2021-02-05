package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.EnterpriseIssueSeeContract;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:  企业上报问题 查看详情
 * <p>
 * ================================================
 */
@ActivityScope
public class EnterpriseIssueSeePresenter extends ReworkBasePresenter<EnterpriseIssueSeeContract.Model, EnterpriseIssueSeeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EnterpriseIssueSeePresenter(EnterpriseIssueSeeContract.Model model, EnterpriseIssueSeeContract.View rootView) {
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
     * 企业获取上报问题详情
     */
    public void getIssueDetail(String id) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            commonGetData(mModel.getIssueDetail(map), mErrorHandler, o -> {
                mRootView.setIssueData(o);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getIssueReplyDetail(String id) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            commonGetData(mModel.getIssueReplyDetail(map), mErrorHandler, o -> {
                mRootView.setIssueReplyData(o);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
