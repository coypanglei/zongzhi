package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.mvp.contract.EnforceLawLawDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * ================================================
 */
@ActivityScope
public class EnforceLawLawDetailPresenter extends ReworkBasePresenter<EnforceLawLawDetailContract.Model, EnforceLawLawDetailContract.View> {
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
    public EnforceLawLawDetailPresenter(EnforceLawLawDetailContract.Model model, EnforceLawLawDetailContract.View rootView) {
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

    public void getLawDetailById(String id) {
        commonGetData(mModel.getLawDetailById(id), mErrorHandler, o -> {
            mRootView.setLawDetail(o);
        });
    }

    /**
     * 获取执法部门
     */
    public void getLegalOperation(boolean pullToRefresh, boolean isLoadMore) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getLegalOperation(ignoreNumber, pageSize), mErrorHandler, o -> {
            if (pullToRefresh && (o == null || o.size() <= 0)) {
                ArmsUtils.makeText("无部门信息");
                return;
            }
            mRootView.setLegalOperation(o, isLoadMore);
        });
    }

    public void getAuditorList(String departId, boolean pullToRefresh, boolean isLoadMore) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getAuditorList(departId, ignoreNumber, pageSize), mErrorHandler, bean -> {
            try {
                if (bean == null || bean.size() <= 0) {
                    ArmsUtils.makeText("无审核人员信息");
                    return;
                }
                mRootView.setAuditorList(bean, isLoadMore);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void submitManageInfo(Map<String, Object> map) {
        commonGetData(mModel.submitManageInfo(map), mErrorHandler, o -> {
            EventBus.getDefault().post(new EventBusBean<>(EventBusConstant.COMMON_UPDATE), RouterHub.APP_EVENTSREPORTEDACTIVITY);
            ArmsUtils.makeText("成功");
            mRootView.killMyself();
        });
    }

    public void upLoadFile(File file) {
        try {
            CommonNetworkRequest.upLoadFile("", file,
                    mErrorHandler, mRootView, iRepositoryManager,
                    uploadFileRsponseBeans -> {
                        if (uploadFileRsponseBeans == null || uploadFileRsponseBeans.size() <= 0) {
                            ArmsUtils.makeText("文件上传失败");
                            return;
                        }
                        uploadFileRsponseBeans.get(0).setSourceFilePath(file.getAbsolutePath());
                        mRootView.updatePicture(uploadFileRsponseBeans);
                        ArmsUtils.makeText("文件上传成功");
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
