package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.EventsReportedLookContract;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EventProceedRecordBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class EventsReportedLookModel extends BaseModel implements EventsReportedLookContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EventsReportedLookModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<Object>> getEventRecordInfo(String id, String custId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("RecordId", id);
        map.put("CustId", custId);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).getEventRecordInfo(SignUtil.paramSign(map));
    }


    @Override
    public Observable<BaseBean<Object>> createEvaluation(String evaluation, String recordId,String images) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Evaluation", evaluation);
        map.put("RecordId", recordId);
        if(StringUtil.isNotNullString(images)) {
            map.put("Urls",images);
        }
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).createEvaluation(SignUtil.paramSign(map));
    }

    /**
     * 归档
     *
     * @param custId custId
     * @return Observable
     */
    @Override
    public Observable<BaseBean<List<EventProceedRecordBean>>> getEventProceedRecord(String custId, String eventRId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("CustId", custId);
        map.put("EventRecordId", eventRId);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).getEventProceedRecord(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<GridInformationBean>> getGetDepartment(String departmentId) {

        Map<String, Object> map = new HashMap<>(8);
        map.put(Constant.DEPARTMENT_ID, departmentId);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getDepartment(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> invalid(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("RecordId", id);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).invalid(SignUtil.paramSign(map));
    }
}