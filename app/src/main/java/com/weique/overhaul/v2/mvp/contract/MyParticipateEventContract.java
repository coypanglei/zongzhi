package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/17/2019 16:02
 *
 *
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MyParticipateEventContract {

    interface View extends IView {

        Activity getActivity();

        /**
         * 设置信息数据
         *
         * @param list       list
         * @param isLoadMore isLoadMore
         */
        void setNewData(List<EventsReportedBean.ListBean> list, boolean isLoadMore);
    }


    interface Model extends IModel {


        /**
         * 参与事件列表
         *
         * @param status       status
         * @param pageSize     pageSize
         * @param ignoreNumber ignoreNumber
         * @return Observable
         */
        Observable<BaseBean<EventsReportedBean>> getTaskList(int status, int pageSize, int ignoreNumber);
    }
}
