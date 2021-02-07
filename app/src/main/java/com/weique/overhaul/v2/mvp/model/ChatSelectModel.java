package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.ChatSelectContract;
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
 * Created by MVPArmsTemplate on 02/21/2020 10:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ChatSelectModel extends BaseModel implements ChatSelectContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ChatSelectModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<AddressBookListBean>> getAllAddressBookListData(int pageSize, int ignoreNumber, String name, String departmentId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("Name", name);
        map.put("DepartmentId", departmentId);
        return mRepositoryManager
                .obtainRetrofitService(AddressLookService.class)
                .getAllAddressBookListData(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<String>> setChatList(int pageSize, int ignoreNumber,
                                                    List<String> alreadyList, String toString, boolean videoEnable) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("videoEnable", videoEnable);
        map.put("Ids", alreadyList);
        map.put("RoomId", toString);
        return mRepositoryManager
                .obtainRetrofitService(AddressLookService.class)
                .setChatList(SignUtil.paramSign(map));
    }
}