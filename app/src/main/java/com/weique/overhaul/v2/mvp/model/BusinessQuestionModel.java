package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.BusinessQuestionContract;
import com.weique.overhaul.v2.mvp.model.api.service.EnterpiseService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.BusinessQuestion;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2020 10:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class BusinessQuestionModel extends BaseModel implements BusinessQuestionContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public BusinessQuestionModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<BusinessQuestion>> getBusinessQuestionData(int pageSize, int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager
                .obtainRetrofitService(EnterpiseService.class)
                .getBusinessQuestionData(SignUtil.paramSign(map));
    }
}