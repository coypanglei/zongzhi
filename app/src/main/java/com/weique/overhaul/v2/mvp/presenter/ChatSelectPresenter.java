package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.mvp.contract.ChatSelectContract;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/21/2020 10:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ChatSelectPresenter extends ReworkBasePresenter<ChatSelectContract.Model, ChatSelectContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ChatSelectPresenter(ChatSelectContract.Model model, ChatSelectContract.View rootView) {
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

    public void getAllAddressBookListData(boolean pullToRefresh, boolean isLoadMore, String name, String departmentId) {
        try {
            handlePaging(pullToRefresh, isLoadMore);
            commonGetData(mModel.getAllAddressBookListData(pageSize, ignoreNumber, name, departmentId), mErrorHandler, itemBean -> {
                mRootView.setAllAddressBookListData(itemBean, isLoadMore);
                handlePaginLoadMore((itemBean == null || itemBean.getList() == null) ? 0 : itemBean.getList().size());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setChatList(List<String> alreadyList, String toString,boolean videoEnable) {
        commonGetData(mModel.setChatList(pageSize, ignoreNumber, alreadyList, toString,videoEnable), mErrorHandler, itemBean -> {
            mRootView.getChatResult(itemBean);
        });
    }

    /**
     * 获取相机权限
     *
     */
    public void getPermission() {
        CommonPermissionUtil.getPermission(mRootView.getContext(), mErrorHandler, () ->
                mRootView.isOpen(), Constant.PERMISSIONS_LIST_READ_WRITE_CAMERA);
    }


}
