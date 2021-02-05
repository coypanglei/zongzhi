package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.StaffListBean;
import com.weique.overhaul.v2.mvp.model.entity.StaffTemperatureBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


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
public interface StaffTemperatureContract {

    interface View extends IView {

        Context getContext();

        void setNewData(List<StaffListBean.ListBean> list);

        void setNewHistoryData(List<StaffTemperatureBean> staffTemperatureBeans);
    }


    interface Model extends IModel {

        Observable<BaseBean<StaffListBean>> getStaffTemperatureList(Map<String, Object> map);

        Observable<BaseBean<Object>> createReportEmpRecord(Map<String, Object> map);

        Observable<BaseBean<List<StaffTemperatureBean>>> GetReportEmpRecordHistory(Map<String, Object> map);
    }
}
