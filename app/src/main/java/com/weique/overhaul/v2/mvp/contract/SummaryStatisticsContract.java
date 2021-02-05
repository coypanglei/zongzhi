package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridDataBean;
import com.weique.overhaul.v2.mvp.model.entity.IncidentReportingBean;
import com.weique.overhaul.v2.mvp.model.entity.StatisticalReviewLineBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


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
public interface SummaryStatisticsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Context getContext();

        void initLineChart();

        void initTime();
        //圆形图
        void initPieChart();

        //获取折线图数据
        void setLineData(StatisticalReviewLineBean statisticalReviewLineBean);

        //获取矛盾调节折线图数据
        void setContradictionAdjustmentLineData(StatisticalReviewLineBean statisticalReviewLineBean);


        //获取走访柱状图数据
        void setVisitList(List<GridDataBean> vistList);


        //获取走访柱状图数据
        void setPatrolInspectionList(List<GridDataBean> patrolInspectionList);

        //获取事件上报数据
        void getIncidentReportingList(IncidentReportingBean ncidentReportingBean);

        void setData(List<GridDataBean> beans);


    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取签到信息
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<StatisticalReviewLineBean>> GetSignIn(Map<String, Object> map);


        /**
         * 获取矛调信息
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<StatisticalReviewLineBean>> getContradictionAdjustmentCount(Map<String, Object> map);

        /**
         * 获取走访信息
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<List<GridDataBean>>> getGridDataBean(Map<String, Object> map);

        /**
         * 获取巡检信息
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<List<GridDataBean>>> getPatrolInspectionList(Map<String, Object> map);

        /**
         * 获取事件上报
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<IncidentReportingBean>> getIncidentReportingList(Map<String, Object> map);

        Observable<BaseBean<List<GridDataBean>>> getDataAcquisition(Map<String, Object> map);

    }
}
