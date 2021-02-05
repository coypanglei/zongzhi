package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.MyParticipateEventContract;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/17/2019 16:02
 *
 *
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MyParticipateEventModel extends BaseModel implements MyParticipateEventContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyParticipateEventModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 参与事件列表
     *
     * @param status       status
     * @param pageSize     pageSize
     * @param ignoreNumber ignoreNumber
     * @return Observable
     */
    @Override
    public Observable<BaseBean<EventsReportedBean>> getTaskList(int status, int pageSize, int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("status", status);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("PageSize", pageSize);
        return mRepositoryManager.obtainRetrofitService(EventsReportedService.class).getEventRecording(SignUtil.paramSign(map));
    }
}