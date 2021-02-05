package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.AddressLookListSearchContract;
import com.weique.overhaul.v2.mvp.model.api.service.AddressLookService;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookListBean;
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
 * Created by MVPArmsTemplate on 12/20/2019 15:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AddressLookListSearchModel extends BaseModel implements AddressLookListSearchContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AddressLookListSearchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<AddressBookListBean>> getAddressBookListData(int pageSize, int ignoreNumber, String departmentId, String name) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("Id", departmentId);
        map.put("Name", name);
        return mRepositoryManager
                .obtainRetrofitService(AddressLookService.class)
                .getAddressBookListData(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<AddressBookListBean>> getAllAddressBookListData(int pageSize, int ignoreNumber, String name) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("Name", name);
        return mRepositoryManager
                .obtainRetrofitService(AddressLookService.class)
                .getAllAddressBookListData(SignUtil.paramSign(map));
    }
    @Override
    public Observable<BaseBean<String>> setChatList(int pageSize, int ignoreNumber, List<String> alreadyList, String toString) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Ids", alreadyList);
        map.put("RoomId", toString);
        return mRepositoryManager
                .obtainRetrofitService(AddressLookService.class)
                .setChatList(SignUtil.paramSign(map));
    }
}