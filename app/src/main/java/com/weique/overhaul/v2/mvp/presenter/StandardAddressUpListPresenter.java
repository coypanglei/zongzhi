package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.StandardAddressUpListContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@ActivityScope
public class StandardAddressUpListPresenter extends ReworkBasePresenter<StandardAddressUpListContract.Model, StandardAddressUpListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public StandardAddressUpListPresenter(StandardAddressUpListContract.Model model, StandardAddressUpListContract.View rootView) {
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

    public void getAddressUpList(boolean pullToRefresh, boolean isLoadMore, String departmentId, String addressTypeId, String name) {
        try {
            handlePaging(pullToRefresh, isLoadMore);
            commonGetData(mModel.getAddressUpList(departmentId, addressTypeId, pageSize, ignoreNumber, name), mErrorHandler, standardAddressUpItemBean -> {
                try {
                    mRootView.setNewData(standardAddressUpItemBean.getData(), isLoadMore);
                    handlePaginLoadMore((standardAddressUpItemBean == null ||
                            standardAddressUpItemBean.getData() == null) ? 0 :
                            standardAddressUpItemBean.getData().size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
