package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementTableContract;
import com.weique.overhaul.v2.mvp.model.api.service.CaseReportedService;
import com.weique.overhaul.v2.mvp.model.api.service.ContradictionService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoItemBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoItemListBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2020 13:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class EconomicManagementModel extends BaseModel implements EconomicManagementTableContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EconomicManagementModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<EntInfoItemListBean>> getEntInfoItem(String id, String type) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("Type", type);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getEntInfoItem(SignUtil.paramSign(map));
    }


    @Override
    public Observable<BaseBean<List<EntInfoItemBean>>> getProjectItems(String id, String type) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("Type", type);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getProjectItem(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> createCharger(String id, String type) {
        Map map = new HashMap();
        map.put("Id", id);
        map.put("name", type);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).createCharger(SignUtil.paramSign(map));
    }

}