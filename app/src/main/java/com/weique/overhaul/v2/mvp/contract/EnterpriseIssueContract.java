package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EnterpriseIssueListBean;
import com.weique.overhaul.v2.mvp.model.entity.LeaderBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public interface EnterpriseIssueContract {
    interface View extends IView {

        Context getContext();

        void setData(List<EnterpriseIssueListBean.ListBean> o, boolean isLoadMore);

        /**
         * 设置领导人信息
         *
         * @param leaderData 领导人信息
         */
        void setLeaderData(LeaderBean leaderData);
    }

    interface Model extends IModel {

        /**
         * 企业端 获取 企业问题 列表
         *
         * @param ignoreNumber ignoreNumber
         * @param pageSize     pageSize
         * @return Observable
         */
        Observable<BaseBean<EnterpriseIssueListBean>> getEnterpriseIssueList(int ignoreNumber, int pageSize);

        Observable<BaseBean<LeaderBean>> getMyEntLeaderInfo();
    }
}
