package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.MatterDetailContract;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.HelperListItemBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 11:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MatterDetailModel extends BaseModel implements MatterDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MatterDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<Object>> accept(String id, int status) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        map.put("EnumEventProceedStatus", status);
        return mRepositoryManager
                .obtainRetrofitService(EventsReportedService.class)
                .accept(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> dispose(String id, String issues, String toString, List<String> urls) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        map.put("CoopDescs", issues);
        map.put("Result", toString);
        map.put("FileUrls", urls);
        return mRepositoryManager
                .obtainRetrofitService(EventsReportedService.class)
                .proceedingOrder(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> returnOrder(String id, int status, String content) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        map.put("EnumEventProceedStatus", status);
        map.put("ReturnReason", content);
        return mRepositoryManager
                .obtainRetrofitService(EventsReportedService.class)
                .accept(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<NameAndIdBean>>> getUserInfoForCoop(String toString) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("q", toString);
        return mRepositoryManager
                .obtainRetrofitService(EventsReportedService.class)
                .getUserInfoForCoop(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> requestSynergy(String synergyMap, String eventRecordId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("result", synergyMap);
        map.put("eventRecordId", eventRecordId);
        return mRepositoryManager
                .obtainRetrofitService(EventsReportedService.class)
                .requestSynergy(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> consentHelp(Map<String, Object> map) {
        return mRepositoryManager
                .obtainRetrofitService(EventsReportedService.class)
                .consentHelp(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<HelperListItemBean>>> getHelperList(String recordEventId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("RecordEventId", recordEventId);
        return mRepositoryManager
                .obtainRetrofitService(EventsReportedService.class)
                .getHelperList(SignUtil.paramSign(map));
    }
}