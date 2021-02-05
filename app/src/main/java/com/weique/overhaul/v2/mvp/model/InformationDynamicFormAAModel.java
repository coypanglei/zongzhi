package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.MapUtils;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.InformationDynamicFormACrudContract;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.api.service.StandardAddressService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
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
 */
@ActivityScope
public class InformationDynamicFormAAModel extends BaseModel implements InformationDynamicFormACrudContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public InformationDynamicFormAAModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 获取网格信息
     */
    @Override
    public Observable<BaseBean<GridInformationBean>> getGetDepartment(String departmentId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put(Constant.DEPARTMENT_ID, departmentId);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getDepartment(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<InformationTypeOneSecondBean.ElementListBean>> createElement(InformationDetailBean informationDetailBean) {
        Map<String, Object> map = MapUtils.entityToMap(informationDetailBean);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).createElement(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<InformationTypeOneSecondBean.ElementListBean>> editElement(InformationDetailBean informationDetailBean) {
        Map<String, Object> map = MapUtils.entityToMap(informationDetailBean);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).editElement(SignUtil.paramSign(map));
    }


    /**
     * 动态表单 关系映射
     *
     * @param map map
     * @return Observable
     */
    @Override
    public Observable<BaseBean<String>> getDynamicFormOrm(Map<String, Object> map) {
        return mRepositoryManager.
                obtainRetrofitService(MainService.class).
                getDynamicFormOrm(SignUtil.paramSign(map));
    }

    /**
     * 获取详情信息
     *
     * @param map map
     * @return Observable
     */
    @Override
    public Observable<BaseBean<String>> getFormInfoBySearching(Map<String, Object> map) {
        return mRepositoryManager.
                obtainRetrofitService(MainService.class).
                getFormInfoBySearching(SignUtil.paramSign(map));
    }

    /**
     * 根据元素id 获取其  动态表单
     *
     * @param elementTypeId elementTypeId
     * @return Observable
     */
    @Override
    public Observable<BaseBean<String>> getDataStructureInJson(String elementTypeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("ElementId", elementTypeId);
        return mRepositoryManager.obtainRetrofitService(StandardAddressService.class)
                .getDataStructureInJson(SignUtil.paramSign(map));
    }
}