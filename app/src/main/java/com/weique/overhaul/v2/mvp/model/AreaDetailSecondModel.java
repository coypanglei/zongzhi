package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.AreaDetailSecondContract;
import com.weique.overhaul.v2.mvp.model.api.service.KnowledgeService;
import com.weique.overhaul.v2.mvp.model.entity.AreaDetailsListBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2020 15:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AreaDetailSecondModel extends BaseModel implements AreaDetailSecondContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AreaDetailSecondModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<List<AreaDetailsListBean>>> getAreaDetailsListData(int pageSize, int ignoreNumber, String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("DepartmentId", id);

        return mRepositoryManager
                .obtainRetrofitService(KnowledgeService.class)
                .getAreaDetailsListData(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<AreaDetailsListBean>>> getAreaDetailsSecondListData(String id, String startTime, String endTime,String type) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("DepartmentId", id);
        map.put("StartDate", startTime);
        map.put("EndDate", endTime);
        map.put("Type",type);

        return mRepositoryManager
                .obtainRetrofitService(KnowledgeService.class)
                .getAreaDetailsSecondListData(SignUtil.paramSign(map));
    }
}