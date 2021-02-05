package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.SearchEconoicManageMentContract;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * ================================================
 */
@ActivityScope
public class SearchEconoicManageMentPresenter extends ReworkBasePresenter<SearchEconoicManageMentContract.Model, SearchEconoicManageMentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SearchEconoicManageMentPresenter(SearchEconoicManageMentContract.Model model, SearchEconoicManageMentContract.View rootView) {
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
     * 获取项目列表
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param name          name 搜索关键字
     */
    public void getProjectList(String did, boolean pullToRefresh, boolean isLoadMore,
                               String name) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getProjectList(did, name, pageSize, ignoreNumber), mErrorHandler, bean -> {
            try {
                mRootView.setNewData(bean, isLoadMore);
                handlePaginLoadMore(bean == null ? 0 : bean.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取企业列表
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param name          name 搜索关键字
     */
    public void getEnterpriseList(String did, boolean pullToRefresh, boolean isLoadMore, String name) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getEnterpriseList(did, name, pageSize, ignoreNumber), mErrorHandler, bean -> {
            try {
                mRootView.setNewData(bean, isLoadMore);
                handlePaginLoadMore(bean == null ? 0 : bean.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取区域信息  根据 区域id
     */
    public void getAreaList(String departmentId, boolean isChild) {
        commonGetData(mModel.getAreaList(departmentId), mErrorHandler, bean -> {
            try {
                if (bean == null || bean.getDepartment() == null || bean.getDepartment().size() <= 0) {
                    ArmsUtils.makeText("暂无区域信息");
                    return;
                }
                mRootView.showAreaListPopup(bean.getDepartment(), isChild);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 删除企业信息 或者项目信息
     */
    public void delete(NameAndIdBean nameAndIdBean, int type,int position) {
        commonGetData(mModel.deleteInfo(nameAndIdBean.getId(), type), mErrorHandler, bean -> {
            try {
                ArmsUtils.makeText("删除成功");
                mRootView.deleteInfo(nameAndIdBean,position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
