package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.mvp.contract.EnforceLawLawAddContract;
import com.weique.overhaul.v2.mvp.model.entity.PartiesBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class EnforceLawLawAddPresenter extends ReworkBasePresenter<EnforceLawLawAddContract.Model, EnforceLawLawAddContract.View> {
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
    public EnforceLawLawAddPresenter(EnforceLawLawAddContract.Model model, EnforceLawLawAddContract.View rootView) {
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
     * 获取事项列表
     */
    public void getMatterList(boolean pullToRefresh, boolean isLoadMore) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getMatterList(ignoreNumber, pageSize), mErrorHandler, o -> {
            mRootView.setMatterList(o, isLoadMore);
        });
    }

    /**
     * 获取第二级0事项列表
     *
     * @param elementId elementId
     */
    public void getMatterSecondList(String elementId) {
        commonGetData(mModel.getMatterSecondList(elementId), mErrorHandler, o -> {
            if (o == null || o.size() <= 0) {
                ArmsUtils.makeText("无下级信息");
                return;
            }
            mRootView.setMatterSecondList(o);
        });
    }

    /**
     * 获取案件分类
     */
    public void getLawSort() {
        commonGetData(mModel.getLawSort(), mErrorHandler, o -> {
            if (o == null || o.size() <= 0) {
                ArmsUtils.makeText("无分类信息");
                return;
            }
            mRootView.setLawSort(o);
        });
    }

    /**
     * 获取执法部门
     */
    public void getLegalOperation(boolean pullToRefresh, boolean isLoadMore) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getLegalOperation(ignoreNumber, pageSize), mErrorHandler, o -> {
            if (isLoadMore && (o == null || o.size() <= 0)) {
                ArmsUtils.makeText("无部门信息");
                return;
            }
            mRootView.setLegalOperation(o, isLoadMore);
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

    /**
     * 获取当事人列表
     *
     * @param text text
     */
    public void getPartiesList(boolean pullToRefresh, boolean isLoadMore, String text) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getPartiesList(ignoreNumber, pageSize, text), mErrorHandler, bean -> {
            try {
                mRootView.setPartiesList(bean, isLoadMore);
                handlePaginLoadMore(bean == null ? 0 : bean.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 提交 新建的当事人信息
     *
     * @param partiesBean partiesBean
     */
    public void submitPartiesInfo(PartiesBean partiesBean) {
        commonGetData(mModel.submitPartiesInfo(partiesBean), mErrorHandler, bean -> {
            try {
                partiesBean.setId(bean);
                mRootView.setPartiesInfo(partiesBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    /**
     * 提交案件信息
     *
     * @param allMap allMap
     */
    public void submitLaw(Map<String, Object> allMap) {
        commonGetData(mModel.submitLaw(allMap), mErrorHandler, bean -> {
            try {
                EventBus.getDefault().post(new EventBusBean<>(EventBusConstant.COMMON_UPDATE), RouterHub.APP_EVENTSREPORTEDACTIVITY);
                ArmsUtils.makeText("成功");
                mRootView.killMyself();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getPermission() {
        CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            try {
                mRootView.getActivity().startActivityForResult(Intent.createChooser(intent, "选择文件上传"), Constant.GET_ACCESSORY_CODE);
            } catch (android.content.ActivityNotFoundException ex) {
                ArmsUtils.makeText("请安装一个文件管理器.");
            }
        }, Constant.READ_WRITE);
    }

}
