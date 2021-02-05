package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.InspectionMapContract;
import com.weique.overhaul.v2.mvp.model.api.service.ResouceSearchService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.InspectionLatLng;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2019 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class InspectionMapModel extends BaseModel implements InspectionMapContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public InspectionMapModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<InspectionLatLng>> getLatLng(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        return mRepositoryManager.obtainRetrofitService(ResouceSearchService.class)
                .getLatLng(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<String>> setIsInspectionComplete(String taskId, String inspectionRecordId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("TaskId", taskId);
        map.put("InspectionRecordId", inspectionRecordId);
        return mRepositoryManager.obtainRetrofitService(ResouceSearchService.class)
                .setIsInspectionComplete(SignUtil.paramSign(map));


    }
}