package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.StandardAddressLookContract;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.api.service.KnowledgeService;
import com.weique.overhaul.v2.mvp.model.api.service.StandardAddressService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.ScanResultBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/26/2019 10:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class StandardAddressLookModel extends BaseModel implements StandardAddressLookContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StandardAddressLookModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 获取标准地址详情
     *
     * @param detailId getAddressDetail
     * @return Observable
     */
    @Override
    public Observable<BaseBean<StandardAddressBean>> getAddressDetail(String detailId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", detailId);
        return mRepositoryManager.obtainRetrofitService(StandardAddressService.class)
                .getStandardAddressDetail(SignUtil.paramSign(map));
    }

    /**
     * 移除 已添加的地址信息
     *
     * @param id id
     * @return return
     */
    @Override
    public Observable<BaseBean<Object>> deleteItem(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("AddressId", id);
        return mRepositoryManager
                .obtainRetrofitService(StandardAddressService.class)
                .DelStandardAddress(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<GridInformationBean>> getGetDepartment() {
        Map<String, Object> map = new HashMap<>(8);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getDepartment(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<ScanResultBean>> getInfoByStandardId(int pageSize, int ignoreNumber, String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("Id", id);
        return mRepositoryManager
                .obtainRetrofitService(KnowledgeService.class)
                .getScanResultListData(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<InformationTypeOneSecondBean.ElementTypeListBean>>> getInformationTypeStartersList(String standardAddressId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("StandardAddressId", standardAddressId);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getElementType(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<InformationTypeOneSecondBean>> getElementTypes(String elementId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("ElementId", elementId);
        return mRepositoryManager.
                obtainRetrofitService(InformationService.class).
                getInfoCollectResource(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<String>> getGetNewGuidForApp() {
        return mRepositoryManager
                .obtainRetrofitService(InformationService.class)
                .getNewGuidForApp(SignUtil.paramSign(null));
    }
}