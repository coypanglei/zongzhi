package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.PartyCenterCollectsContract;
import com.weique.overhaul.v2.mvp.model.api.service.PartyService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;

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
@ActivityScope
public class PartyCenterCollectsModel extends BaseModel implements PartyCenterCollectsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PartyCenterCollectsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 获取收藏列表
     *
     * @param pageSize     PageSize
     * @param ignoreNumber IgnoreNumber
     * @return Observable
     */
    @Override
    public Observable<BaseBean<PartyContentItemBean>> getCollectList(int pageSize,
                                                                     int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager.obtainRetrofitService(PartyService.class).getCollectList(SignUtil.paramSign(map));
    }

    /**
     * 文章取消收藏
     *
     * @return Observable
     */
    @Override
    public Observable<BaseBean<Object>> removeCollectStatus(String infoId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("InfoId", infoId);
        return mRepositoryManager.obtainRetrofitService(PartyService.class).removeCollectStatus(SignUtil.paramSign(map));
    }
}