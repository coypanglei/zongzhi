package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.StandardAddressOneContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/22/2019 09:42
 * ================================================
 */
@ActivityScope
public class StandardAddressOnePresenter extends ReworkBasePresenter<StandardAddressOneContract.Model, StandardAddressOneContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public StandardAddressOnePresenter(StandardAddressOneContract.Model model, StandardAddressOneContract.View rootView) {
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
     * 获取网格员 某 项信息  单元/ 门牌  楼宇  小区  等
     *
     * @param pullToRefresh pullToRefresh
     * @param name          要搜索的文字
     * @param isLoadMore    isLoadMore
     * @param upClassId     upClassId
     */
    public void getGriddingFourRace(boolean pullToRefresh, boolean isLoadMore, String upClassId, String gridId, String name) {
        try {
            handlePaging(pullToRefresh, isLoadMore);
            commonGetData(mModel.getGriddingFourRace(upClassId, gridId, name, pageSize, ignoreNumber), mErrorHandler, standardAddressStairBean -> {
                try {
                    mRootView.setNewData(standardAddressStairBean, isLoadMore);
                    handlePaginLoadMore((standardAddressStairBean == null || standardAddressStairBean.getStandardAddress() == null)
                            ? 0 : standardAddressStairBean.getStandardAddress().size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
