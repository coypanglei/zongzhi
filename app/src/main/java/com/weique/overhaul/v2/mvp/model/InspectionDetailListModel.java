package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.InspectionDetailListContract;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.api.service.ResouceSearchService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchDetailItemBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2019 16:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class InspectionDetailListModel extends BaseModel implements InspectionDetailListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public InspectionDetailListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<GridInformationBean>> getGetDepartment(String departmentId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put(Constant.DEPARTMENT_ID, departmentId);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getDepartment(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<ResourceSearchDetailItemBean>> getInspectionDetailListData(int pageSize, int ignoreNumber, String resourceId, String elementTypeId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("ResourceId", resourceId);
        map.put("ElementTypeId", elementTypeId);
        return mRepositoryManager
                .obtainRetrofitService(ResouceSearchService.class)
                .getInspectionDetailListData(SignUtil.paramSign(map));
    }
}