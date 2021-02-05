package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EnterpriseIssueDetailBean;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 16:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface EnterpriseIssueSeeContract {

    interface View extends IView {

        /**
         * 设置问题详情
         *
         * @param bean objects
         */
        void setIssueData(EnterpriseIssueDetailBean bean);
        /**
         * 设置问题回复详情
         *
         * @param o objects
         */
        void setIssueReplyData(String o);
    }


    interface Model extends IModel {

        /**
         * 获取问题详情
         *
         * @return Observable
         */
        Observable<BaseBean<EnterpriseIssueDetailBean>> getIssueDetail(Map<String, Object> map);

        /**
         * 获取问题回复详情
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<String>> getIssueReplyDetail(Map<String, Object> map);
    }
}
