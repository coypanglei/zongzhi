package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.AnnouncementContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/10/2020 13:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class AnnouncementPresenter extends ReworkBasePresenter<AnnouncementContract.Model, AnnouncementContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AnnouncementPresenter(AnnouncementContract.Model model, AnnouncementContract.View rootView) {
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

    public void getNotice(boolean pullToRefresh, boolean isLoadMore) {
        try {
            handlePaging(pullToRefresh, isLoadMore);
            //跑马灯使用之后去5条
            commonGetData(mModel.getNotice(pageSize, ignoreNumber), mErrorHandler, announcementListBean -> {
                mRootView.setNewData(announcementListBean.getList(), isLoadMore);
                handlePaginLoadMore((announcementListBean == null || announcementListBean.getList() == null) ? 0 : announcementListBean.getList().size());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getNoticeInfo(String id) {
        commonGetData(mModel.getNoticeInfo(id), mErrorHandler, listBean -> {
            mRootView.setWebData(listBean);
        });
    }
}
