package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.MapUtils;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.EventsReportedCrudContract;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedLookBean;
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
 *
 * @author GreatKing
 */
@ActivityScope
public class EventsReportedCuedModel extends BaseModel implements EventsReportedCrudContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EventsReportedCuedModel(IRepositoryManager repositoryManager) {
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
    public Observable<BaseBean<Object>> createEventRecord(EventsReportedLookBean eventsReportedLookBean) {
        Map<String, Object> map = MapUtils.entityToMap(eventsReportedLookBean);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).createEventRecord(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> editEventRecord(EventsReportedLookBean eventsReportedLookBean) {
        Map<String, Object> map = MapUtils.entityToMap(eventsReportedLookBean);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).editEventRecord(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> createEventRecordSnap(EventsReportedLookBean eventsReportedLookBean) {
        Map<String, Object> map = MapUtils.entityToMap(eventsReportedLookBean);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).createEventRecordSnap(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> delEventRecord(String custId, String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("CustId", custId);
        map.put("EventTypeId", id);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).delEventRecord(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> invalid(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("RecordId", id);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).invalid(SignUtil.paramSign(map));
    }


}