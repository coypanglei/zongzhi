package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.BusinessQuestionDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2020 10:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class BusinessQuestionDetailPresenter extends ReworkBasePresenter<BusinessQuestionDetailContract.Model, BusinessQuestionDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public BusinessQuestionDetailPresenter(BusinessQuestionDetailContract.Model model, BusinessQuestionDetailContract.View rootView) {
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
     * @param id 获取问题详情  去回复
     */
    public void getQuestionDetail(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("id", id);
        commonGetData(mModel.getQuestionDetail(map), mErrorHandler, o -> {
            mRootView.setIssueData(o);
        });
    }

    /**
     * 提交回复
     *
     * @param issueId issueId
     * @param content content
     */
    public void submitFeedback(String issueId, String content) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("EntTroubleId", issueId);
        map.put("Desc", content);
        map.put("UserInfoId", UserInfoUtil.getUserInfo().getUid());
        map.put("CreateUserId", UserInfoUtil.getUserInfo().getUid());
        map.put("UpdateUserId", UserInfoUtil.getUserInfo().getUid());
        commonGetData(mModel.submitFeedback(map), mErrorHandler, o -> {
            mRootView.killMyself();
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE), RouterHub.APP_BUSINESSQUESTIONACTIVITY);
        });
    }
}
