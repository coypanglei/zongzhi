package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.StaffListBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/13/2020 19:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface StaffListContract {

    interface View extends IView {

        Context getContext();

        void setData(List<StaffListBean.ListBean> list, boolean isLoadMore);
    }


    interface Model extends IModel {

        /**
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<StaffListBean>> getStaffList(Map<String, Object> map);

        Observable<BaseBean<Object>> sendStaffHeat(Map<String, Object> map);
    }
}
