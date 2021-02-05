package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author  GK
 */
public interface PartyCenterCollectsContract {

    interface View extends IView {

        /**
         * 下拉加载更多
         *
         * @param favoritesBeans partyContentItemBeans
         */
        void setMoreData(List<PartyContentItemBean.ListBean> favoritesBeans);

        /**
         * 更新数据
         *
         * @param favoritesBeans partyContentItemBeans
         */
        void setNewData(List<PartyContentItemBean.ListBean> favoritesBeans);

        /**
         * 移除item
         *
         * @param pos 位置
         */
        void removeItem(int pos);
    }


    interface Model extends IModel {

        /**
         * 获取收藏列表
         *
         * @param pageSize     pageSize
         * @param ignoreNumber ignoreNumber
         * @return Observable
         */
        Observable<BaseBean<PartyContentItemBean>> getCollectList(int pageSize,
                                                              int ignoreNumber);

        /**
         * 文章取消收藏
         *
         * @param userInfoUid userId
         * @param infoId      id
         * @return Observable
         */
        Observable<BaseBean<Object>> removeCollectStatus(String infoId);
    }
}
