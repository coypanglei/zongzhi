package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.SignInContract;
import com.weique.overhaul.v2.mvp.model.api.service.AddressLookService;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.SigninStatusBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/23/2019 15:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SignInModel extends BaseModel implements SignInContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SignInModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<String>> setSign(String pointsInJson,
                                                String address, boolean isInGrid, int signActive) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("EnumCheckInStatus", signActive);
        map.put("PointsInJson", pointsInJson);
        map.put("Address", address);
        map.put("IsInGrid", isInGrid);
        return mRepositoryManager
                .obtainRetrofitService(AddressLookService.class)
                .setSign(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<SigninStatusBean>> getSignStatus() {
        return mRepositoryManager
                .obtainRetrofitService(AddressLookService.class)
                .getIsMyCheck(SignUtil.paramSign(null));
    }

    @Override
    public Observable<BaseBean<GridInformationBean>> getGetDepartment() {
        Map<String, Object> map = new HashMap<>(8);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getDepartment(SignUtil.paramSign(map));
    }
}