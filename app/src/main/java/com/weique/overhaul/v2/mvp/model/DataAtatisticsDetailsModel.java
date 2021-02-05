package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.DataAtatisticsDetailsContract;
import com.weique.overhaul.v2.mvp.model.api.service.StatisticalReviewService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.DataReportInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.GridDataBean;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class DataAtatisticsDetailsModel extends BaseModel implements DataAtatisticsDetailsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DataAtatisticsDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    /**
     * 事件上报详情
     *
     * @param map
     * @return
     */
    @Override
    public Observable<BaseBean<DataReportInfoBean>> getDataReportInfoList(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(StatisticalReviewService.class).getdataReportList(SignUtil.paramSign(map));
    }

    /**
     * 数据采集
     * @param map
     * @return
     */
    @Override
    public Observable<BaseBean<List<GridDataBean>>> getDataAcquisition(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(StatisticalReviewService.class).getDataStatistics(SignUtil.paramSign(map));
    }

}