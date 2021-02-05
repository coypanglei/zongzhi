package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.SignStaffListContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@ActivityScope
public class SignStaffListPresenter extends ReworkBasePresenter<SignStaffListContract.Model, SignStaffListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SignStaffListPresenter(SignStaffListContract.Model model, SignStaffListContract.View rootView) {
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
     * 获取 网格及支上 用户 列表
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param departmentId
     */
    public void getDepartmentInfoListData(boolean pullToRefresh, boolean isLoadMore, String departmentId, boolean needAdd) {
        try {
            handlePaging(pullToRefresh, isLoadMore);
            //这里套用  通讯录接口 因为不需要分页 暂时写死 200  / 一页
            commonGetData(mModel.getDepartmentInfoListData(200, ignoreNumber, departmentId), mErrorHandler, addressBookListBean -> {
                if (addressBookListBean == null || addressBookListBean.getList() == null || addressBookListBean.getList().size() <= 0) {
                    ArmsUtils.makeText("未发现资源");
                    return;
                }
                mRootView.setDepartmentInfoListData(addressBookListBean.getList(), needAdd);
                handlePaginLoadMore((addressBookListBean == null ||
                        addressBookListBean.getList() == null) ? 0 : addressBookListBean.getList().size());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 网格及支上 用户 列表
     *
     * @param departmentId
     */
    public void getSignStaffList(String departmentId, boolean needAdd) {
        try {
            commonGetData(mModel.getSignStaffList(departmentId), mErrorHandler, addressBookListBean -> {
                mRootView.setDepartmentInfoListData(addressBookListBean.getList(), needAdd);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
