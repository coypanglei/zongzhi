package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedSortBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
public interface EventsReportedSortContract {
    interface View extends IView {

        /**
         * 获取 Context
         *
         * @return Context
         */
        Context getContext();

        /**
         * 设置心数据
         *
         * @param eventFormType eventFormType
         * @param needAdd       needAdd
         */
        void setData(List<EventsReportedSortBean.ListBean> eventFormType, boolean needAdd);
    }

    interface Model extends IModel {

        /**
         * 获取 事件类型列表
         *
         * @param typeId       typeId
         * @return Observable
         */
        Observable<BaseBean<EventsReportedSortBean>> getEvents(String typeId,String keyword, int limit);
    }
}
