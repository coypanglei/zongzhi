package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.StandardAddressLookContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationDynamicFormCrudActivity;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/26/2019 10:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class StandardAddressLookPresenter extends ReworkBasePresenter<StandardAddressLookContract.Model, StandardAddressLookContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    Gson gson;

    @Inject
    public StandardAddressLookPresenter(StandardAddressLookContract.Model model, StandardAddressLookContract.View rootView) {
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
     * 获取详情
     *
     * @param detailId detailId
     */
    public void getAddressDetail(String detailId) {
        commonGetData(mModel.getAddressDetail(detailId), mErrorHandler, standardAddressBean -> mRootView.setData(standardAddressBean));
    }

    public void deleteItem(String id) {
        commonGetData(mModel.deleteItem(id), mErrorHandler, o -> {
            try {
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.DELETE), RouterHub.APP_STANDARDADDRESSONENEWACTIVITY);
                mRootView.killMyself();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getDepartment() {
        commonGetData(mModel.getGetDepartment(), mErrorHandler, gridInformationBean -> {
            try {
                mRootView.gotoMapTv(gridInformationBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getInfoByStandardId(boolean pullToRefresh, boolean isLoadMore, String id) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getInfoByStandardId(pageSize, ignoreNumber, id), mErrorHandler, itemBean -> {
            mRootView.setInfoData(itemBean);
        });
    }

    /**
     * 获取信息类型一级列表
     */
    public void getInformationTypeStartersList(String standardAddressId, boolean showPopup) {
        commonGetData(mModel.getInformationTypeStartersList(standardAddressId), mErrorHandler, beans -> {
            try {
                mRootView.setInformationTypeStarters(beans, showPopup);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getElementTypes(String elementId) {
        commonGetData(mModel.getElementTypes(elementId), mErrorHandler, beans -> {
            try {
                mRootView.setElementList(beans);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getGetNewGuidForApp(String departmentId, String elementId,
                                    StandardAddressBean mStandardAddressBean,
                                    String dataStructureInJson, String elementName) {
        commonGetData(mModel.getGetNewGuidForApp(), mErrorHandler, id -> {
            try {
                InformationDetailBean bean = new InformationDetailBean();
                bean.setDepartmentId(departmentId);
                bean.setUserId(UserInfoUtil.getUserInfo().getUid());
                bean.setElementTypeId(elementId);
                bean.setStandardAddressId(mStandardAddressBean.getId());
                bean.setStandardAddressFullPath(mStandardAddressBean.getName());
                bean.setPointsInJson(mStandardAddressBean.getGisLocations());
                bean.setId(id);
                bean.setElementId(id);
                InformationDynamicFormSelectBean informationDynamicFormSelectBean = gson.fromJson(dataStructureInJson, InformationDynamicFormSelectBean.class);
                ARouter.getInstance().build(RouterHub.APP_INFORMATIONDYNAMICFORMAAACTIVITY)
                        .withParcelable(InformationDynamicFormCrudActivity.LIST, informationDynamicFormSelectBean)
                        .withInt(InformationDynamicFormCrudActivity.TYPE_KEY, EventBusConstant.ADD)
                        .withString(ARouerConstant.TITLE, "新增")
                        .withString(ARouerConstant.DEPARTMENT_ID, departmentId)
                        .withParcelable(InformationDynamicFormCrudActivity.INFORMATIONDETAILBEAN, bean)
                        .withString(ARouerConstant.SOURCE, RouterHub.APP_STANDARDADDRESSLOOKACTIVITY)
                        .navigation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
