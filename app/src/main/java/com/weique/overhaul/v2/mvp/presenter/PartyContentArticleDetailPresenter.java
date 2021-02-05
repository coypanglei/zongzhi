package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.PartyContentArticleDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.PartyDetailBean;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterActivity;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyContentArticleDetailActivity;

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
@ActivityScope
public class PartyContentArticleDetailPresenter extends ReworkBasePresenter<PartyContentArticleDetailContract.Model, PartyContentArticleDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PartyContentArticleDetailPresenter(PartyContentArticleDetailContract.Model model, PartyContentArticleDetailContract.View rootView) {
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
     * 获取文章详情
     *
     * @param type type
     * @param id   id
     */
    public void getGetArticleDetail(int type, String id) {
        try {
            Observable observable = null;
            switch (type) {
                case PartyCenterActivity.NEWS_CENTER:
                    observable = mModel.getGetNewsDetail(id);
                    break;
                case PartyCenterActivity.SUBJECT_EDUCATION:
                    observable = mModel.getGetSubeDetail(id);
                    break;
                case PartyCenterActivity.NOTICE:
                    observable = mModel.getGetNotificationDetail(id);
                    break;
                case PartyCenterActivity.PARTY_ACTIVITY:
                    observable = mModel.GetPartyMeetingById(id);
                    break;
                default:
            }
            commonGetData(observable, mErrorHandler, (ProgressMonitorHandle<PartyDetailBean>) bean -> mRootView.show(bean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加 查询  收藏
     *
     * @param isSelect 区分查询还是添加  true  是查询   false 是添加 接口相反 所以 - !isSelect
     * @param infoId   InfoId
     */
    public void addOrSelectCollectStatus(boolean isSelect, String infoId) {
        commonGetData(mModel.getCollectStatus(infoId, !isSelect), mErrorHandler, integer -> {
            if (isSelect) {
                mRootView.setCollect(integer);
            } else {
                mRootView.setCollect(PartyContentArticleDetailActivity.IS_COLLECT);
            }
        });
    }

    /**
     * 删除收藏
     *
     * @param infoId id
     */
    public void removeCollectStatus(String infoId) {
        commonGetData(mModel.removeCollectStatus(infoId), mErrorHandler, bean ->
                mRootView.setCollect(PartyContentArticleDetailActivity.NOT_COLLECT)
        );
    }
}
