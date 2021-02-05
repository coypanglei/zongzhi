package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/25/2020 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface SearchCollectionContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * 获取 Context
         *
         * @return Context
         */
        Context getContext();


        /**
         * 树形数据
         *
         * @param dataTrees dataTrees
         */
        void setDataTree(List<InformationTypeOneSecondBean.ElementTypeListBean> dataTrees, boolean needAdd);
        /**
         * 获取全部数据
         *
         * @param dataTrees dataTrees
         */
        void setAllDataTree(List<InformationTypeOneSecondBean.ElementTypeListBean> dataTrees);


    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取 树形  信息采集类型
         *
         * @param id id
         * @return Observable
         */
        Observable<BaseBean<InformationTypeOneSecondBean>> getElementTypesByName(String id);
    }
}
