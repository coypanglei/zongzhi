package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.EnforceLawEventListContract;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2020 09:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class EnforceLawEventListModel extends BaseModel implements EnforceLawEventListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EnforceLawEventListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    /**
     * 获取事件上报订单列表
     *
     * @param pageSize     pageSize
     * @param ignoreNumber ignoreNumber
     * @return Observable
     */
    @Override
    public Observable<BaseBean<EventsReportedBean>> getEventRecord(String name, int type,
                                                                   int status,
                                                                   String sortType,
                                                                   int pageSize,
                                                                   int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Name", name);
        map.put("type", type);
        map.put("SortType", sortType);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("status", status);
        map.put("PageSize", pageSize);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                .getEventRecord(SignUtil.paramSign(map));
    }

    /**
     * 获取事件上报订单列表
     *
     * @param pageSize     pageSize
     * @param ignoreNumber ignoreNumber
     * @return Observable
     */
    @Override
    public Observable<BaseBean<EventsReportedBean>> getEventRecord(String name, int type,
                                                                   String sortType,
                                                                   int pageSize,
                                                                   int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Name", name);
        map.put("type", type);
        map.put("SortType", sortType);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("PageSize", pageSize);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                .getEventRecord(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<String>> getGetNewGuidForApp() {
        return mRepositoryManager
                .obtainRetrofitService(InformationService.class)
                .getNewGuidForApp(SignUtil.paramSign(null));
    }

    @Override
    public Observable<BaseBean<GridInformationBean>> gridOperatorInformation() {
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getDepartment(SignUtil.paramSign(null));
    }

    @Override
    public Observable<BaseBean<Boolean>> getIsInGrid() {
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).getIsInGrid(SignUtil.paramSign(null));
    }
}