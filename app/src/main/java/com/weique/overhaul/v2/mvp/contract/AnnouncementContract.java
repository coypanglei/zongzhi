package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.AnnouncementListBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/10/2020 13:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AnnouncementContract {

    interface View extends IView {

        /**
         * 设置心数据
         *
         * @param list       list
         * @param isLoadMore isLoadMore
         */
        void setNewData(List<AnnouncementListBean.ListBean> list, boolean isLoadMore);

        Activity getActivity();

        void setWebData(AnnouncementListBean.ListBean listBean);
    }


    interface Model extends IModel {

        Observable<BaseBean<AnnouncementListBean>> getNotice(int pageSize, int ignoreNumber);
        Observable<BaseBean<AnnouncementListBean.ListBean>> getNoticeInfo(String id);
    }
}
