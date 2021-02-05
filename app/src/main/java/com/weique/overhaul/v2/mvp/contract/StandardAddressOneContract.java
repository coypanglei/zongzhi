package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
public interface StandardAddressOneContract {
    interface View extends IView {

        /**
         * 设置新参数
         *
         * @param standardAddress StandardAddressBean
         * @param isLoadMore      isLoadMore
         */
        void setNewData(StandardAddressStairBean standardAddress, boolean isLoadMore);

        /**
         * 移除要删除的item
         */
        void removeItem();
    }

    interface Model extends IModel {

        /**
         * 获取 4项 5项信息
         *
         * @param upClassId    upClassId
         * @param name         name
         * @param pageSize     pageSize
         * @param ignoreNumber ignoreNumber
         * @return 标准地址 获取 用户上级 信息  下级列表
         */
        Observable<BaseBean<StandardAddressStairBean>> getGriddingFourRace(String upClassId, String gridId, String name, int pageSize, int ignoreNumber);


    }
}
