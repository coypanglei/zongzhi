package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.StandardAddressContract;
import com.weique.overhaul.v2.mvp.model.api.service.StandardAddressService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * ================================================
 *
 * @author GK
 */
@ActivityScope
public class StandardAddressModel extends BaseModel implements StandardAddressContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StandardAddressModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * @return 标准地址 获取 用户上级 信息  下级列表
     */
    @Override
    public Observable<BaseBean<StandardAddressStairBean>> getUpDownRankInfo(String departmentId, int pageSize, int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put(Constant.DEPARTMENT_ID, departmentId);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager
                .obtainRetrofitService(StandardAddressService.class)
                .getUpDownRankInfo(SignUtil.paramSign(map));
    }
}