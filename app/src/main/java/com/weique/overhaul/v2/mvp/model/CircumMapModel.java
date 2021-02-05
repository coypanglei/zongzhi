package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.CircumMapContract;
import com.weique.overhaul.v2.mvp.model.api.service.AddressLookService;
import com.weique.overhaul.v2.mvp.model.api.service.StandardAddressService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PointsBean;
import com.weique.overhaul.v2.mvp.model.entity.PointsListBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/27/2020 17:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CircumMapModel extends BaseModel implements CircumMapContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CircumMapModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<PointsListBean>> getAllAddressPoints(Map<String, Object> map) {

        return mRepositoryManager
                .obtainRetrofitService(AddressLookService.class)
                .getPoints(SignUtil.paramSign(map));

    }

    @Override
    public Observable<BaseBean<List<PointsBean>>> getDepartmentsPoints(Map<String, Object> map) {
        return mRepositoryManager
                .obtainRetrofitService(AddressLookService.class)
                .getDepartmentsPoints(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<String>> getDataStructureInJson(Map<String, Object> paramSign) {
        return mRepositoryManager
                .obtainRetrofitService(StandardAddressService.class)
                .getDataStructureInJson(SignUtil.paramSign(paramSign));
    }
}