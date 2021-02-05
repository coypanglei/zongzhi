package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressUpItemBean;

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
public interface StandardAddressUpListContract {
    interface View extends IView {

        Activity getActivity();

        /**
         * 跟新数据
         *
         * @param data       standardAddressUpItemBean
         * @param isLoadMore isLoadMore
         */
        void setNewData(List<StandardAddressUpItemBean.DataBean> data, boolean isLoadMore);
    }

    interface Model extends IModel {

        /**
         * @param departmentId  departmentId
         * @param addressTypeId addressTypeId
         * @param pageSize      pageSize
         * @param ignoreNumber  ignoreNumber
         * @return Observable
         */
        Observable<BaseBean<StandardAddressUpItemBean>> getAddressUpList(String departmentId, String addressTypeId, int pageSize, int ignoreNumber,String name);
    }
}
