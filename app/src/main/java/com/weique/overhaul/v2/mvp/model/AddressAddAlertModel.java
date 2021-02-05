package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.MapUtils;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.AddressAddAlertContract;
import com.weique.overhaul.v2.mvp.model.api.service.StandardAddressService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/22/2019 17:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AddressAddAlertModel extends BaseModel implements AddressAddAlertContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AddressAddAlertModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 提交标准地址详情
     *
     * @param standardAddressBean standardAddressBean
     * @return Observable
     */
    @Override
    public Observable<BaseBean<StandardAddressStairBean.StandardAddressStairBaseBean>> submitStandardAddressAlert(StandardAddressBean standardAddressBean) {
        Map<String, Object> map = MapUtils.entityToMap(standardAddressBean);
        return mRepositoryManager.obtainRetrofitService(StandardAddressService.class).alertStandardAddressDetail(SignUtil.paramSign(map));
    }

    /**
     * 提交标准地址详情 添加
     *
     * @param standardAddressBean standardAddressBean
     * @return Observable
     */
    @Override
    public Observable<BaseBean<StandardAddressStairBean.StandardAddressStairBaseBean>> submitStandardAddressAdd(StandardAddressBean standardAddressBean) {
        Map<String, Object> map = MapUtils.entityToMap(standardAddressBean);
        return mRepositoryManager.obtainRetrofitService(StandardAddressService.class).addStandardAddressDetail(SignUtil.paramSign(map));
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
}