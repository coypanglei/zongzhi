package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.common.EnumConstant;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.TaskListContract;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.api.service.StandardAddressService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.TaskListBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class TaskListModel extends BaseModel implements TaskListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TaskListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<TaskListBean>> getTaskList(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(MainService.class)
                .getTaskList(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<GridInformationBean>> getGetDepartment() {
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getDepartment(SignUtil.paramSign(null));
    }
    @Override
    public Observable<BaseBean<String>> getGetNewGuidForApp() {
        return mRepositoryManager
                .obtainRetrofitService(InformationService.class)
                .getNewGuidForApp(SignUtil.paramSign(null));
    }

    @Override
    public Observable<BaseBean<String>> getDataStructureInJson(String elementTypeId, @EnumConstant.DynamicBeanByType int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("ElementId", elementTypeId);
        map.put("type", type);
        return mRepositoryManager.obtainRetrofitService(StandardAddressService.class)
                .getDataStructureInJson(SignUtil.paramSign(map));
    }
}
