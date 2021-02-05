package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.EnforceLawEventContract;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2020 16:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class EnforceLawEventModel extends BaseModel implements EnforceLawEventContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EnforceLawEventModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }



    @Override
    public Observable<BaseBean<String>> getGetNewGuidForApp() {
        return mRepositoryManager
                .obtainRetrofitService(InformationService.class)
                .getNewGuidForApp(SignUtil.paramSign(null));
    }

    @Override
    public Observable<BaseBean<GridInformationBean>> gridOperatorInformation() {
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getDepartment(SignUtil.paramSign(null));
    }

    @Override
    public Observable<BaseBean<Boolean>> getIsInGrid() {
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).getIsInGrid(SignUtil.paramSign(null));
    }
}