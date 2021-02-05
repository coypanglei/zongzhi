package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.AnnouncementListBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.HomeMenuItemBean;
import com.weique.overhaul.v2.mvp.model.entity.SigninStatusBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public interface HomeContract {

    interface View extends IView {
        Activity getActivity();

        /**
         * 设置首页menu 数据 和 指示器
         *
         * @param allMenuData allMenuData
         * @param menuData    menuData
         * @param booleans    booleans
         */
        void setMenuData(ArrayList<HomeMenuItemBean> allMenuData, List<HomeMenuItemBean> menuData, List<Boolean> booleans);


        void startRun(List<String> strings);

        void setSignBtnStatus(SigninStatusBean b);

    }


    interface Model extends IModel {
        Observable<BaseBean<ArrayList<HomeMenuItemBean>>> getHomeModuleLabel();


        Observable<BaseBean<AnnouncementListBean>> getNotice(int pageSize, int ignoreNumber);

        Observable<BaseBean<SigninStatusBean>> getSignStatus();
    }
}
