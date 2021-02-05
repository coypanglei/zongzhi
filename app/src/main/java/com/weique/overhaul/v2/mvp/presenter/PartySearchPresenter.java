package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.PartySearchContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * 党建搜索
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class PartySearchPresenter extends ReworkBasePresenter<PartySearchContract.Model, PartySearchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PartySearchPresenter(PartySearchContract.Model model, PartySearchContract.View rootView) {
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
     * 获取 关键字列表
     *
     * @param keyword keyword
     */
    public void getKeywordList(boolean isLoadMore, String keyword) {
        handlePaging(false, isLoadMore);
        commonGetData(mModel.getListByKeyword("", keyword, pageSize, ignoreNumber), mErrorHandler, itemBean -> {
            try {
                if (isLoadMore) {
                    mRootView.setMoreData(itemBean.getList());
                } else {
                    mRootView.setNewData(itemBean.getList());
                }
                handlePaginLoadMore((itemBean == null ||
                        itemBean.getList() == null) ? 0 :
                        itemBean.getList().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
