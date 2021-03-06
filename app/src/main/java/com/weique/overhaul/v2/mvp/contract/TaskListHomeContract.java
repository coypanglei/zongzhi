package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.app.common.EnumConstant;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.TaskListBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2020 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface TaskListHomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getContext();

        void setNewData(List<TaskListBean.ListBean> list, boolean isLoadMore);

        void gotoMapTv(GridInformationBean gridInformationBean);

        /**
         * 设置 guid
         *
         * @param id                    id
         * @param missionConditionsBean missionConditionsBean
         */
        void setGotoNewGuid(String id, TaskListBean.ListBean.MissionConditionsBean missionConditionsBean);

        /**
         * 设置动态表单数据
         *
         * @param s                     s
         * @param informationDetailBean
         */
        void setDynamicForm(String s, InformationDetailBean informationDetailBean);

        /**
         * 设置动态表单数据
         *
         * @param s                     s
         * @param missionConditionsBean missionConditionsBean
         * @param type                  type
         */
        void setDynamicForm(String s, TaskListBean.ListBean.MissionConditionsBean missionConditionsBean, @EnumConstant.DynamicBeanByType int type);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<TaskListBean>> getTaskList(Map<String, Object> map);

        Observable<BaseBean<GridInformationBean>> getGetDepartment();

        Observable<BaseBean<String>> getGetNewGuidForApp();

        Observable<BaseBean<String>> getDataStructureInJson(String elementTypeId, @EnumConstant.DynamicBeanByType int type);
    }
}
