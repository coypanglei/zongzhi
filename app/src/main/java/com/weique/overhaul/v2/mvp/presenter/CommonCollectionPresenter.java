package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.baidu.location.BDLocation;
import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.service.localtion.LocSdkClient;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.ObjectToMapUtils;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.CommonCollectionContract;
import com.weique.overhaul.v2.mvp.model.api.service.CaseReportedService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.ProjectInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/25/2020 11:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CommonCollectionPresenter extends ReworkBasePresenter<CommonCollectionContract.Model, CommonCollectionContract.View> {
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

    BasicInformationBean.RecordsBean changeBean;

    @Inject
    public CommonCollectionPresenter(CommonCollectionContract.Model model, CommonCollectionContract.View rootView) {
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

    public void getPermission() {
        CommonPermissionUtil.getPermission(mRootView.getContext(), mErrorHandler, () -> mRootView.goToPhotoAlbum(), Constant.READ_WRITE_CAMERA);
    }


    /**
     * 获取枚举值
     */
    public void getCommonEnums(String type) {
        if ("EntBusinessTypeId".equals(type)) {
            getEnterpriseTypes(type);
        } else {
            commonGetData(mModel.getCommonEnums(type), mErrorHandler, bean -> {
                try {
                    mRootView.getCommonEnums(bean, type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }


    }

    public void createData(Map map, String path) {

        CommonNetworkRequest.commonRequestOne(map, path, mErrorHandler, mRootView, iRepositoryManager, o -> {
            EventBus.getDefault().post(new EventBusBean(path), RouterHub.APP_COMMON_COLLECTION);
            mRootView.killMyself();
        });
    }


    /**
     * 上传文件
     *
     * @param compressPaths compressPath 文件压缩路径
     */
    public void upLoadFile(String elementId, List<String> compressPaths) {
        try {
            CommonNetworkRequest.upLoadFile(elementId, compressPaths,
                    mErrorHandler, mRootView, iRepositoryManager,
                    uploadFileRsponseBeans -> {
                        if (uploadFileRsponseBeans != null && uploadFileRsponseBeans.size() > 0) {
                            UploadFileRsponseBean uploadFileRsponseBean = uploadFileRsponseBeans.get(0);
                            if (StringUtil.isNullString(uploadFileRsponseBean.getUrl())) {
                                ArmsUtils.makeText("头像地址为空");
                                return;
                            }
                            mRootView.updatePicture(uploadFileRsponseBean.getUrl());
                        } else {
                            ArmsUtils.makeText("上传失败");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 上传文件
     *
     * @param compressPaths compressPath 文件压缩路径
     */
    public void upLoadFileImgOrVideo(String elementId, List<String> compressPaths, BasicInformationBean.RecordsBean changeBean) {
        try {
            CommonNetworkRequest.upLoadFile(elementId, compressPaths,
                    mErrorHandler, mRootView, iRepositoryManager,
                    uploadFileRsponseBeans -> {
                        if (uploadFileRsponseBeans != null && uploadFileRsponseBeans.size() > 0) {
                            mRootView.updatePicture(uploadFileRsponseBeans, changeBean);
                        } else {
                            ArmsUtils.makeText("上传失败");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取行业列表
     */
    public void getEnterpriseTypes(String type) {
        commonGetData(mModel.getEnterpriseType(), mErrorHandler, bean -> {
            try {
                List<CommonTitleBean> list = new ArrayList<>();
                for (NameAndIdBean nameAndIdBean : bean) {
                    list.add(new CommonTitleBean(nameAndIdBean.getId(), nameAndIdBean.getName()));
                }
                mRootView.getCommonEnums(list, type);


            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * 获取相机权限
     *
     * @param max
     */
    public void getPermission(int max,int type) {
        CommonPermissionUtil.getPermission(mRootView.getContext(), mErrorHandler, new CommonPermissionUtil.PermissionLisenter() {
            @Override
            public void getPermissionSuccess() {
                mRootView.goToPhotoAlbum(max,type);
            }
        }, Constant.PERMISSIONS_LIST_READ_WRITE_CAMERA);
    }

    public void setChangeBean(BasicInformationBean.RecordsBean bean) {
        changeBean = bean;
    }

    public BasicInformationBean.RecordsBean getChangeBean() {
        return changeBean;
    }

    /**
     * 获取详情根据id
     */
    public void getInfoById(CommonCollectBean commonCollectBean) {
        String id = (String) commonCollectBean.getMap().get("EntInfoId");
        switch (commonCollectBean.getPath()) {
            case "/api/EcoDev/EditEntInfo":

                commonGetData(mModel.getEntInfo(id), mErrorHandler, bean -> {
                    try {
                        setEntInfo(bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "/api/EcoDev/EditMProject":
                commonGetData(mModel.getProjectInfo(id), mErrorHandler, bean -> {
                    try {
                        setEntInfo(bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;
            default:
                break;
        }


    }

    public void setEntInfo(Object entInfo) {
        try {

            Map<String, String> map = ObjectToMapUtils.str2Map(entInfo);
            for (BasicInformationBean.RecordsBean bean : mRootView.getAdapter().getData()) {
                if (bean.getName().equals("EntBusinessTypeName")) {
                    bean.setValue(map.get(bean.getName()));
                    bean.setSelectValue(map.get("EntBusinessTypeId"));
                    bean.setName("EntBusinessTypeId");
                } else {
                    //通用
                    if (map.containsKey(bean.getName())) {
                        bean.setValue(map.get(bean.getName()));
                    }
                }


            }
            mRootView.getAdapter().setNewData(mRootView.getAdapter().getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
