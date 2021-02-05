package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.StaffTemperatureContract;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.StaffListBean;
import com.weique.overhaul.v2.mvp.model.entity.StaffTemperatureBean;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/13/2020 21:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class StaffTemperatureModel extends BaseModel implements StaffTemperatureContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StaffTemperatureModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<StaffListBean>> getStaffTemperatureList(Map<String, Object> map) {
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .getStaffList(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> createReportEmpRecord(Map<String, Object> map) {
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .getStaffTemperatureList(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<StaffTemperatureBean>>> GetReportEmpRecordHistory(Map<String, Object> map) {
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .GetReportEmpRecordHistory(SignUtil.paramSign(map));
    }
}