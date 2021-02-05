package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementBaseInfoContract;
import com.weique.overhaul.v2.mvp.model.api.service.CaseReportedService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterDetailsBean;
import com.weique.overhaul.v2.mvp.model.entity.ProjectInfoBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2020 11:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class EconomicManagementBaseInfoModel extends BaseModel implements EconomicManagementBaseInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EconomicManagementBaseInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<ProjectInfoBean>> getProjectInfo(String id) {
        Map map = new HashMap();
        map.put("Id", id);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getProjectInfo(SignUtil.paramSign(map));
    }


    @Override
    public Observable<BaseBean<EntInfoBean>> getEntInfo(String id) {

        Map map = new HashMap();
        map.put("Id", id);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getEntInfo(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<MatterDetailsBean>> getMatterInfo(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getMatterInfo(SignUtil.paramSign(map));
    }


}