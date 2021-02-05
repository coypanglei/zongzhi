package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.StaffTemperatureContract;
import com.weique.overhaul.v2.mvp.model.entity.StaffListBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/13/2020 21:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class StaffTemperaturePresenter extends ReworkBasePresenter<StaffTemperatureContract.Model, StaffTemperatureContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public StaffTemperaturePresenter(StaffTemperatureContract.Model model, StaffTemperatureContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 获取员工列表
     */
    public void getStaffTemperatureList() {
        Map<String, Object> map = new HashMap<>();
        commonGetData(mModel.getStaffTemperatureList(map), mErrorHandler, staffListBean -> {
            try {
                List<StaffListBean.ListBean> list = staffListBean.getList();
                mRootView.setNewData(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void createReportEmpRecord(Map<String, Object> map) {
        commonGetData(mModel.createReportEmpRecord(map), mErrorHandler, o -> {
            mRootView.killMyself();
        });
    }

    public void GetReportEmpRecordHistory(String yearMonth) {
        Map<String, Object> map = new HashMap<>();
        map.put("date", yearMonth);
        commonGetData(mModel.GetReportEmpRecordHistory(map), mErrorHandler, staffTemperatureBeans -> {
            mRootView.setNewHistoryData(staffTemperatureBeans);
        });
    }
}
