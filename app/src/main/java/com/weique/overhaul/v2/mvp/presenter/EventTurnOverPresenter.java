package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.EventTurnOverContract;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.DepartsBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventsReportedLookActivity;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * @author GK
 * @description:
 * @date :2021/1/26 15:50
 */
@ActivityScope
public class EventTurnOverPresenter extends ReworkBasePresenter<EventTurnOverContract.Model, EventTurnOverContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    IRepositoryManager mRepositoryManager;

    @Inject
    public EventTurnOverPresenter(EventTurnOverContract.Model model, EventTurnOverContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getDeparts(String url, String id) {
        try {
            Map<String, Object> map = new HashMap<>(8);
            map.put("Id", id);
            Observable<BaseBean<DepartsBean>> observable = mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                    .setDeparts(url, SignUtil.paramSign(map));
            commonGetData(observable, mErrorHandler, o -> {
                mRootView.setDeparts(o.getItems());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void transitOrder(String id, Object departId, String returnReason) {
        try {
            Map<String, Object> map = new HashMap<>(8);
            map.put("Id", id);
            map.put("DepartId", departId);
            map.put("ReturnReason", returnReason);
            Observable<BaseBean<Object>> observable = mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                    .transitOrder(SignUtil.paramSign(map));
            commonGetData(observable, mErrorHandler, o -> {
                mAppManager.killActivity(EventsReportedLookActivity.class);
                mRootView.killMyself();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void coopRequestCreate(String id, String departIds, String returnReason, String source) {
        try {
            Map<String, Object> map = new HashMap<>(8);
            map.put("Id", id);
            map.put("DepartIds", departIds);
            map.put("ReturnReason", returnReason);
            Observable<BaseBean<Object>> observable = mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                    .coopRequestCreate(SignUtil.paramSign(map));
            commonGetData(observable, mErrorHandler, o -> {
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE), source);
                mRootView.killMyself();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 延期操作
     *
     * @param id
     * @param delayDate
     * @param returnReason
     * @param source
     */
    public void eventDelayApply(String url, String id, String delayDate, String returnReason, String source) {
        try {
            Map<String, Object> map = new HashMap<>(8);
            map.put("Id", id);
            map.put("DelayDate", delayDate);
            map.put("ReturnReason", returnReason);
            Observable<BaseBean<Object>> observable = mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                    .eventDelayApply(url, SignUtil.paramSign(map));
            commonGetData(observable, mErrorHandler, o -> {
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE), source);
                mRootView.killMyself();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
