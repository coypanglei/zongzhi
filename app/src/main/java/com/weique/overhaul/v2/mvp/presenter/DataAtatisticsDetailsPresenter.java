package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.mvp.contract.DataAtatisticsDetailsContract;
import com.weique.overhaul.v2.mvp.model.entity.DataReportInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.GridDataBean;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2020 13:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class DataAtatisticsDetailsPresenter extends ReworkBasePresenter<DataAtatisticsDetailsContract.Model, DataAtatisticsDetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public DataAtatisticsDetailsPresenter(DataAtatisticsDetailsContract.Model model, DataAtatisticsDetailsContract.View rootView) {
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
     * 搜索事件上报 详情
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @param Id        事件类型Id 资源类型Id
     * @param Type      1.信息采集 2.走访 3.巡检 4.事件
     */
    public void getIncidentReportingList(String startDate, String endDate, String Type, String Id, String departmentId) {
        Map<String, Object> map = new HashMap<>();
        int compareTo = startDate.compareTo(endDate);
        if (compareTo > 0) {
            //"date1 大于 date2";
            map.put("StartDate", endDate);
            map.put("EndDate", startDate);
        } else if (compareTo == 0) {
            map.put("StartDate", startDate);
            map.put("EndDate", endDate);
        } else if (compareTo < 0) {
            map.put("StartDate", startDate);
            map.put("EndDate", endDate);
        }
        map.put("Type", Type);
        map.put("Id", Id);
        if (!ArmsUtils.isEmpty(departmentId)) {
            map.put(Constant.DEPARTMENT_ID, departmentId);
        }

        commonGetData(mModel.getDataReportInfoList(map), mErrorHandler, (DataReportInfoBean t) -> {
            mRootView.getDataReportInfoList(t);
        });
    }


    /**
     * 搜索数据采集 详情
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @param Id        事件类型Id 资源类型Id
     * @param Type      1.信息采集 2.走访 3.巡检 4.事件
     */
    public void getIncidentReportingListByData(String startDate, String endDate, String Type, String Id, String departmentId) {
        Map<String, Object> map = new HashMap<>();
        int compareTo = startDate.compareTo(endDate);
        if (compareTo > 0) {
            //"date1 大于 date2";
            map.put("StartDate", endDate);
            map.put("EndDate", startDate);
        } else if (compareTo == 0) {
            map.put("StartDate", startDate);
            map.put("EndDate", endDate);
        } else if (compareTo < 0) {
            map.put("StartDate", startDate);
            map.put("EndDate", endDate);
        }
        map.put("Type", Type);
        map.put("ElementTypeId", Id);
        if (!ArmsUtils.isEmpty(departmentId)) {
            map.put(Constant.DEPARTMENT_ID, departmentId);
        }
        commonGetData(mModel.getDataAcquisition(map), mErrorHandler, (List<GridDataBean> t) -> {
            mRootView.getDataAcquisition(t);
        });
    }


}
