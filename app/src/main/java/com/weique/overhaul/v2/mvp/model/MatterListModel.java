package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.MatterListContract;
import com.weique.overhaul.v2.mvp.model.api.service.CaseReportedService;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonEnumBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterListBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 11:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MatterListModel extends BaseModel implements MatterListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MatterListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<MatterListBean>> getList(int pageSize, int ignoreNumber,
                                                        int flag, int orderStatus, int dataSource, String keyword) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("EnumSource", dataSource);
        map.put("EnumOrderStatus", orderStatus);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("Keyword", keyword);
        map.put("EnumEventRecordTransitType", flag);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).getList(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<CommonEnumBean>>> getEnum(String enumType) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("type", enumType);

        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class)
                .getCommonEnumBean(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<MatterListBean>> getMyCooperListByReceiveUserId(int pageSize,
                                                                               int ignoreNumber,
                                                                               int flag, int orderStatus,
                                                                               int dataSource,
                                                                               String keyword) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("EnumSource", dataSource);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("Keyword", keyword);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                .getMyCooperListByReceiveUserId(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<MatterListBean>> getMyProceedListByUserId(int pageSize, int ignoreNumber,
                                                                         int flag, int orderStatus,
                                                                         int dataSource, String keyword) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("EnumSource", dataSource);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("Keyword", keyword);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                .getMyProceedListByUserId(SignUtil.paramSign(map));
    }
}