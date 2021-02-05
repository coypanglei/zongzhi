package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.PartyCenterCollectsContract;

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
public class PartyCenterCollectsPresenter extends ReworkBasePresenter<PartyCenterCollectsContract.Model, PartyCenterCollectsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public PartyCenterCollectsPresenter(PartyCenterCollectsContract.Model model, PartyCenterCollectsContract.View rootView) {
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
     * 获取 收藏列表
     *
     * @param pullToRefresh 是不是下拉刷新
     * @param isLoadMore    是不是上拉加载
     */
    public void getCollectList(boolean pullToRefresh, boolean isLoadMore) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getCollectList(pageSize, ignoreNumber), mErrorHandler, itemBean -> {
            try {
                if (isLoadMore) {
                    mRootView.setMoreData(itemBean.getList());
                } else {
                    mRootView.setNewData(itemBean.getList());
                }
                handlePaginLoadMore((itemBean == null || itemBean.getList() == null) ? 0 : itemBean.getList().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 删除收藏
     *
     * @param infoId id
     */
    public void removeCollectStatus(String infoId, int pos) {
        commonGetData(mModel.removeCollectStatus(infoId), mErrorHandler, bean ->
                mRootView.removeItem(pos)
        );
    }
}
