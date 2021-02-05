package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.BusinessQuestionDetailContract;
import com.weique.overhaul.v2.mvp.model.api.service.EnterpiseService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EnterpriseIssueDetailBean;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class BusinessQuestionDetailModel extends BaseModel implements BusinessQuestionDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public BusinessQuestionDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 获取问题详情
     *
     * @param map map
     * @return Observable
     */
    @Override
    public Observable<BaseBean<EnterpriseIssueDetailBean>> getQuestionDetail(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(EnterpiseService.class).getIssueDetail(SignUtil.paramSign(map));
    }

    /**
     * i提交回复
     *
     * @param map map
     * @return Observable
     */
    @Override
    public Observable<BaseBean<Object>> submitFeedback(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(EnterpiseService.class).submitFeedback(SignUtil.paramSign(map));
    }
}