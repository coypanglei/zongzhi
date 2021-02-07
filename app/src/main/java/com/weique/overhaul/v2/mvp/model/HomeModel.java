package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.HomeContract;
import com.weique.overhaul.v2.mvp.model.api.service.AddressLookService;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.AnnouncementListBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.HomeMenuItemBean;
import com.weique.overhaul.v2.mvp.model.entity.SigninStatusBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<ArrayList<HomeMenuItemBean>>> getHomeModuleLabel(String url) {
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .getHomeModuleLabel(url, SignUtil.paramSign(null));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<AnnouncementListBean>> getNotice(int pageSize, int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager.obtainRetrofitService(MainService.class).getNotice(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<SigninStatusBean>> getSignStatus() {
        return mRepositoryManager
                .obtainRetrofitService(AddressLookService.class)
                .getIsMyCheck(SignUtil.paramSign(null));
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