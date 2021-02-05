package com.weique.overhaul.v2.mvp.presenter;

import android.app.Activity;
import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.mvp.contract.MatterDetailContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 11:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MatterDetailPresenter extends ReworkBasePresenter<MatterDetailContract.Model, MatterDetailContract.View> {
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
    public MatterDetailPresenter(MatterDetailContract.Model model, MatterDetailContract.View rootView) {
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

    public void accept(String id, int status) {
        commonGetData(mModel.accept(id, status), mErrorHandler, o -> {
            mRootView.setAccept();
        });
    }

    public void getPermission(int max) {
        CommonPermissionUtil.getPermission((Activity) mRootView.getActivity(), mErrorHandler, () ->
                mRootView.goToPhotoAlbum(max), Constant.PERMISSIONS_LIST_READ_WRITE_CAMERA);
    }

    public void upLoadFile(String elementId, List<String> compressPaths) {
        try {
            CommonNetworkRequest.upLoadFile(elementId, compressPaths,
                    mErrorHandler, mRootView, iRepositoryManager,
                    uploadFileRsponseBeans -> {
                        try {
                            mRootView.setFiles(uploadFileRsponseBeans);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dispose(String id, String issues, String toString, List<String> urls) {
        commonGetData(mModel.dispose(id, issues, toString, urls), mErrorHandler, o -> {
            mRootView.setDispose();
        });
    }

    public void returnOrder(String id, int status, String content) {
        commonGetData(mModel.returnOrder(id, status, content), mErrorHandler, o -> {
            mRootView.returnOrderSuccess();
        });
    }

    public void getUserInfoForCoop(String toString) {
        commonGetData(mModel.getUserInfoForCoop(toString), mErrorHandler, o -> {
            mRootView.setUserInfoForCoop(o);
        });
    }

    public void requestSynergy(String synergyMap, String eventRecordId) {
        commonGetData(mModel.requestSynergy(synergyMap, eventRecordId), mErrorHandler, o -> {
            mRootView.setRequestSynergy(o);
        });
    }

    /**
     * 同意or 拒绝 协助 接口
     *
     * @param enumEventProceedStatus enumEventProceedStatus 2 同意  | 3 拒绝
     * @param id                     id
     * @param Content                Content
     */
    public void consentHelp(int enumEventProceedStatus, String id, String Content) {
        Map<String, Object> map = new HashMap<>();
        map.put("Id", id);
        map.put("EnumEventProceedStatus", enumEventProceedStatus);
        map.put("RefuseReason", Content);
        commonGetData(mModel.consentHelp(map), mErrorHandler, o -> {
            mRootView.setConsentHelp(o);
        });
    }

    public void getHelperList(String recordEventId) {
        commonGetData(mModel.getHelperList(recordEventId), mErrorHandler, o -> {
            mRootView.setHelperList(o);
        });
    }
}
