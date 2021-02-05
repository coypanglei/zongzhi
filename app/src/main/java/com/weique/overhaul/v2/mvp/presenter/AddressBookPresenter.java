package com.weique.overhaul.v2.mvp.presenter;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.mvp.contract.AddressBookContract;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import static com.jess.arms.utils.ArmsUtils.startActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2019 09:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AddressBookPresenter extends ReworkBasePresenter<AddressBookContract.Model, AddressBookContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    RxPermissions mRxPermissions;

    @Inject
    public AddressBookPresenter(AddressBookContract.Model model, AddressBookContract.View rootView) {
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

    public void getDepartmentInfoListData(boolean pullToRefresh, boolean isLoadMore, String departmentId) {
        try {
            handlePaging(pullToRefresh, isLoadMore);
            commonGetData(mModel.getDepartmentInfoListData(pageSize, ignoreNumber, departmentId), mErrorHandler, itemBean -> {
                mRootView.setDepartmentInfoListData(itemBean, isLoadMore);
                handlePaginLoadMore((itemBean == null || itemBean.getList() == null) ? 0 : itemBean.getList().size());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestCalls(String content) {
        PermissionUtil.requestPermission(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + content);
                    intent.setData(data);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {

            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {

            }
        }, mRxPermissions, mErrorHandler, Manifest.permission.CALL_PHONE);
    }

    public void setChatList(List<String> alreadyList, String toString) {
        commonGetData(mModel.setChatList(pageSize, ignoreNumber, alreadyList, toString), mErrorHandler, itemBean -> {
            mRootView.getChatResult(itemBean);
        });
    }

    public void getPermission() {
        CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () ->
                mRootView.isOpen(), Constant.PERMISSIONS_LIST_READ_WRITE_CAMERA);
    }
}
