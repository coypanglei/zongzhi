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
 * Created by MVPArmsTemplate on 10/24/2019 16:45
 * ================================================
 *
 * @author GK
 */
public interface StandardAddressContract {

    interface View extends IView {

        /**
         * 设置数据
         *
         * @param standardAddressStairBean standardAddressStairBean
         */
        void newData(StandardAddressStairBean standardAddressStairBean,boolean isLoadMore);
    }


    interface Model extends IModel {

        /**
         * @return 标准地址 获取 用户上级 信息  下级列表
         */
        Observable<BaseBean<StandardAddressStairBean>> getUpDownRankInfo(String departmentId,int pageSize,int ignoreNumber);
    }
}
