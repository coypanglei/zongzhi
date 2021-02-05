package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.EnterpriseInformationCollectionContract;
import com.weique.overhaul.v2.mvp.model.api.service.CaseReportedService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.collectBean;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2020 09:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class EnterpriseInformationCollectionModel extends BaseModel implements EnterpriseInformationCollectionContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EnterpriseInformationCollectionModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<Object>> createEntInfo(Map map) {
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).createEntInfo(SignUtil.paramSign(map));
    }


    @Override
    public Observable<BaseBean<List<collectBean>>> getList() {
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getList(SignUtil.paramSign(null));
    }


    @Override
    public Observable<BaseBean<List<NameAndIdBean>>> getEnterpriseType() {
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getEnterpriseType(SignUtil.paramSign(null));
    }

}