package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.StandardAddressOneNewContract;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.api.service.StandardAddressService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.UserGideBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/25/2020 13:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class StandardAddressOneNewModel extends BaseModel implements StandardAddressOneNewContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StandardAddressOneNewModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * @return
     */
    @Override
    public Observable<BaseBean<UserGideBean>> getGridList(int pageSize, int ignoreNumber, String keyWord) {
        Map<String, Object> map = new HashMap<>();
        map.put("Keyword", keyWord);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getGridList(SignUtil.paramSign(map));
    }

    /**
     * @return
     */
    @Override
    public Observable<BaseBean<List<SearchProjectBean.DepartmentsBean>>> getDepartments(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.DEPARTMENT_ID, id);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getDepartments(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<StandardAddressStairBean>> getDepartmenDownInfo(String departmentId, int pageSize, int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put(Constant.DEPARTMENT_ID, departmentId);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager
                .obtainRetrofitService(StandardAddressService.class)
                .getUpDownRankInfo(SignUtil.paramSign(map));
    }

    /**
     * @param upClassId    typeid
     * @param name         搜索 关键字
     * @param pageSize     pageSize
     * @param ignoreNumber ignoreNumber
     * @return 标准地址 获取 用户上级 信息  下级列表
     */
    @Override
    public Observable<BaseBean<StandardAddressStairBean>> getGriddingFourRace(String upClassId, String gridId, String name, int pageSize, int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("TypeId", upClassId);
        map.put("GridId", gridId);
        map.put("Name", name);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager
                .obtainRetrofitService(StandardAddressService.class)
                .getGridingFourRace(SignUtil.paramSign(map));
    }
}