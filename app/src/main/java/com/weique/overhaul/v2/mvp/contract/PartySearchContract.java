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
 * Created by MVPArmsTemplate on 11/14/2019 10:54
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface PartySearchContract {
    interface View extends IView {

        /**
         * 设置更多数据
         *
         * @param list list
         */
        void setMoreData(List<PartyContentItemBean.ListBean> list);

        /**
         * 设置新数据
         *
         * @param list list
         */
        void setNewData(List<PartyContentItemBean.ListBean> list);
    }

    interface Model extends IModel {

        /**
         * 获取新闻列表
         *
         * @param typeId       typeId
         * @param keyWord      keyWord
         * @param pageSize     pageSize
         * @param ignoreNumber ignoreNumber
         * @return Observable
         */
        Observable<BaseBean<PartyContentItemBean>> getListByKeyword(String typeId, String keyWord, int pageSize, int ignoreNumber);

    }
}
