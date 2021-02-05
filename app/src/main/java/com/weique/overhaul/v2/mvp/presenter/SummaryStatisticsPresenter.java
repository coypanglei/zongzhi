package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.SummaryStatisticsContract;
import com.weique.overhaul.v2.mvp.model.entity.GridDataBean;
import com.weique.overhaul.v2.mvp.model.entity.IncidentReportingBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2020 09:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class SummaryStatisticsPresenter extends ReworkBasePresenter<SummaryStatisticsContract.Model, SummaryStatisticsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SummaryStatisticsPresenter(SummaryStatisticsContract.Model model, SummaryStatisticsContract.View rootView) {
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
     * 搜索签到信息
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     */
    public void GetSignIn(String startDate, String endDate) {
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
        commonGetData(mModel.GetSignIn(map), mErrorHandler, t -> mRootView.setLineData(t));
    }


    /**
     * 搜索签到信息
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     */
    public void GetContradictionAdjustment(String startDate, String endDate) {
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
        commonGetData(mModel.getContradictionAdjustmentCount(map), mErrorHandler, t -> mRootView.setContradictionAdjustmentLineData(t));
    }



    /**
     * 搜索网格走访
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     */
    public void getVisitList(String startDate, String endDate) {
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
        commonGetData(mModel.getGridDataBean(map), mErrorHandler, (List<GridDataBean> t) -> mRootView.setVisitList(t));
    }



    /**
     * 搜索网格巡检
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     */
    public void getPatrolnspectionList(String startDate, String endDate) {
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
        commonGetData(mModel.getPatrolInspectionList(map), mErrorHandler, (List<GridDataBean> t) -> mRootView.setPatrolInspectionList(t));
    }



    /**
     * 搜索事件上报
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     */
    public void getIncidentReportingList(String startDate, String endDate) {
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
        commonGetData(mModel.getIncidentReportingList(map), mErrorHandler, (IncidentReportingBean t) -> {
            mRootView.getIncidentReportingList(t);
        });
    }

    /**
     * 数据采集集合
     */
    public void getList(String startDate, String endDate) {
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

        commonGetData(mModel.getDataAcquisition(map), mErrorHandler, beans -> {
            try {
                mRootView.setData(beans);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
