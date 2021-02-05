package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.MySignListBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/24/2019 17:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface SignSearchContract {

    interface View extends IView {
        void setSignRecordListData(List<MySignListBean.ListBean> data);

        /**
         * 按天设置 签到记录
         *
         * @param mySignListBean mySignListBean
         */
        void setSignRecordListByDay(List<MySignListBean.ListBean> mySignListBean);
    }


    interface Model extends IModel {
        /**
         * @param tid   tid
         * @param mouth mouth
         * @param day   day
         * @return Observable
         */
        Observable<BaseBean<List<MySignListBean.ListBean>>> getSignRecordListData(String tid, String mouth, String day);

    }
}
