package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/201
 * 9 17:36

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 *
 * @author  GK
 */
public interface PartContentContract {
    interface View extends IView {
        /**
         * 获取fragment
         *
         * @return BaseFragment
         */
        BaseFragment getFragment();

    }

    interface Model extends IModel {
        /**
         * 获取新闻列表
         *
         * @param typeId       typeId
         * @param pageSize     pageSize
         * @param ignoreNumber ignoreNumber
         * @return Observable
         */
        Observable<BaseBean<PartyContentItemBean>> getPartyDataNews(String typeId, int pageSize, int ignoreNumber);

        /**
         * 获取 主题教育列表
         *
         * @param typeId       typeId
         * @param pageSize     pageSize
         * @param ignoreNumber ignoreNumber
         * @return Observable
         */
        Observable<BaseBean<PartyContentItemBean>> getPartyDataSubs(String typeId, int pageSize, int ignoreNumber);

        /**
         * 获取 通知消息列表
         *
         * @param typeId       typeId
         * @param pageSize     pageSize
         * @param ignoreNumber ignoreNumber
         * @return Observable
         */
        Observable<BaseBean<PartyContentItemBean>> getPartyDataNotice(String typeId, int pageSize, int ignoreNumber);

        /**
         * 获取 党建活动
         *
         * @param typeId       typeId
         * @param pageSize     pageSize
         * @param ignoreNumber ignoreNumber
         * @return Observable
         */
        Observable<BaseBean<PartyContentItemBean>> getPartyActivity(String typeId, int pageSize, int ignoreNumber);
    }
}
