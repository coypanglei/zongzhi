package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.SummaryStatisticsContract;
import com.weique.overhaul.v2.mvp.model.api.service.StatisticalReviewService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridDataBean;
import com.weique.overhaul.v2.mvp.model.entity.IncidentReportingBean;
import com.weique.overhaul.v2.mvp.model.entity.StatisticalReviewLineBean;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
@FragmentScope
public class SummaryStatisticsModel extends BaseModel implements SummaryStatisticsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SummaryStatisticsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 搜索签到信息
     *
     * @param map map
     * @return Observable
     */
    @Override
    public Observable<BaseBean<StatisticalReviewLineBean>> GetSignIn(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(StatisticalReviewService.class).getSignInCount(SignUtil.paramSign(map));
    }

    /**
     * 搜索矛盾调节信息
     *
     * @param map map
     * @return Observable
     */
    @Override
    public Observable<BaseBean<StatisticalReviewLineBean>> getContradictionAdjustmentCount(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(StatisticalReviewService.class).getContradictionAdjustmentCount(SignUtil.paramSign(map));
    }

    /**
     * 网格走访
     *
     * @param map map
     * @return
     */
    @Override
    public Observable<BaseBean<List<GridDataBean>>> getGridDataBean(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(StatisticalReviewService.class).getVisitList(SignUtil.paramSign(map));
    }

    /**
     * 网络巡检
     *
     * @param map map
     * @return
     */
    @Override
    public Observable<BaseBean<List<GridDataBean>>> getPatrolInspectionList(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(StatisticalReviewService.class).getPatrolInspectionList(SignUtil.paramSign(map));
    }

    /**
     * 事件上报
     *
     * @param map
     * @return
     */
    @Override
    public Observable<BaseBean<IncidentReportingBean>> getIncidentReportingList(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(StatisticalReviewService.class).getIncidentReportingList(SignUtil.paramSign(map));
    }


    @Override
    public Observable<BaseBean<List<GridDataBean>>> getDataAcquisition(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(StatisticalReviewService.class).getDataStatistics(SignUtil.paramSign(map));
    }

}