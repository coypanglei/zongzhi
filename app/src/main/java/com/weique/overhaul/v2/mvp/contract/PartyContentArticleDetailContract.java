package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyDetailBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
public interface PartyContentArticleDetailContract {
    interface View extends IView {
        /**
         * 获取context
         *
         * @return Activity
         */
        Activity getActivity();

        /**
         * 控制界面显示内容
         *
         * @param bean bean
         */
        void show(PartyDetailBean bean);

        /**
         * 设置收藏
         *
         * @param isCollect 判断是否是收藏
         */
        void setCollect(int isCollect);
    }

    interface Model extends IModel {

        /**
         * 获取文章详情
         *
         * @param id id
         * @return Observable
         */
        Observable<BaseBean<PartyDetailBean>> getGetNewsDetail(String id);

        /**
         * 主题教育详情
         *
         * @param id id
         * @return Observable
         */
        Observable<BaseBean<PartyDetailBean>> getGetSubeDetail(String id);

        /**
         * 通知公告详情
         *
         * @param id id
         * @return Observable
         */
        Observable<BaseBean<PartyDetailBean>> getGetNotificationDetail(String id);

        /**
         * 通知党建活动
         *
         * @param id id
         * @return Observable
         */
        Observable<BaseBean<PartyDetailBean>> GetPartyMeetingById(String id);

        /**
         * 文章查询 添加收藏  已添加会有data字段区分
         *
         * @param infoId id
         * @param status true是查询新增 false是只查询
         * @return Observable
         */
        Observable<BaseBean<Integer>> getCollectStatus(String infoId, boolean status);

        /**
         * 文章取消收藏
         *
         * @param infoId      id
         * @return Observable
         */
        Observable<BaseBean<Object>> removeCollectStatus(String infoId);
    }
}
