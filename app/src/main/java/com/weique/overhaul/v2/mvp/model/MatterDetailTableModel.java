package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.MatterDetailTableContract;
import com.weique.overhaul.v2.mvp.model.api.service.CaseReportedService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterDetailsBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterDetailsFlowBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/01/2020 13:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MatterDetailTableModel extends BaseModel implements MatterDetailTableContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MatterDetailTableModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<MatterDetailsBean>> getDetailById(String eventRecordId, String id, int flag) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("id", eventRecordId);
        map.put("type", flag);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getDetailById(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<MatterDetailsFlowBean>>> getProgress(String eventRecordId, String id, int flag) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("id", eventRecordId);
        map.put("type", flag);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getProgress(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> getDuBan(String eventRecordId, String id, int flag) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("id", eventRecordId);
        map.put("type", flag);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getDuBan(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> getCuiBan(String eventRecordId, String id, int flag) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("id", eventRecordId);
        map.put("type", flag);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getCuiBan(SignUtil.paramSign(map));
    }
}