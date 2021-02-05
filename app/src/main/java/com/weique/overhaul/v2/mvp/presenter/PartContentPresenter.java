package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.PartContentContract;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterActivity;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@FragmentScope
public class PartContentPresenter extends ReworkBasePresenter<PartContentContract.Model, PartContentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public PartContentPresenter(PartContentContract.Model model, PartContentContract.View rootView) {
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

    public void getPartyDataByType(boolean pullToRefresh, boolean isLoadMore, String infoTypeId, int type) {
        try {
            handlePaging(pullToRefresh, isLoadMore);
            Observable observable = null;
            switch (type) {
                case PartyCenterActivity.NEWS_CENTER:
                    observable = mModel.getPartyDataNews(infoTypeId, pageSize, ignoreNumber);
                    break;
                case PartyCenterActivity.SUBJECT_EDUCATION:
                    observable = mModel.getPartyDataSubs(infoTypeId, pageSize, ignoreNumber);
                    break;
                case PartyCenterActivity.NOTICE:
                    observable = mModel.getPartyDataNotice(infoTypeId, pageSize, ignoreNumber);
                    break;
                case PartyCenterActivity.PARTY_ACTIVITY:
                    observable = mModel.getPartyActivity(infoTypeId, pageSize, ignoreNumber);
                    break;
                default:
            }
            commonGetData(observable, mErrorHandler, (ProgressMonitorHandle<PartyContentItemBean>) itemBean -> {
                if (isLoadMore) {
                    mRootView.getFragment().addMoreData(itemBean.getList());
                } else {
                    mRootView.getFragment().setData(itemBean.getList());
                }
                handlePaginLoadMore((itemBean == null || itemBean.getList() == null) ? 0 : itemBean.getList().size());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
