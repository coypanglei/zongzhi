package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.CommonCollectionContract;
import com.weique.overhaul.v2.mvp.model.api.service.CaseReportedService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.ProjectInfoBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/25/2020 11:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CommonCollectionModel extends BaseModel implements CommonCollectionContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CommonCollectionModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<List<CommonTitleBean>>> getCommonEnums(String type) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("type", type);

        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class)
                .getTitles(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<NameAndIdBean>>> getEnterpriseType() {
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getEnterpriseType(SignUtil.paramSign(null));
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


}