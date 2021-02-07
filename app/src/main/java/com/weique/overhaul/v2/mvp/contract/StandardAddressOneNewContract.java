package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.SearchProjectBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseSearchPopupBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.UserGideBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/25/2020 13:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface StandardAddressOneNewContract {

    interface View extends IView {

        Context getContext();

        void showPopup(List<? extends BaseSearchPopupBean> listBeans, boolean isMore);

        void setNewData(StandardAddressStairBean standardAddressStairBean, boolean isLoadMore, int count);

        void showTreePopup(List<SearchProjectBean.DepartmentsBean> departmentsBeans, boolean addChild);
    }

    /**
     *
     */
    interface Model extends IModel {

        /**
         * @return
         */
        Observable<BaseBean<UserGideBean>> getGridList(int pageSize, int ignoreNumber, String keyWord);

        Observable<BaseBean<List<SearchProjectBean.DepartmentsBean>>> getDepartments(String id);

        Observable<BaseBean<StandardAddressStairBean>> getDepartmenDownInfo(String departmentId, int pageSize, int ignoreNumber);

        /**
         * 获取 4项 5项信息
         *
         * @param upClassId    upClassId
         * @param name         name
         * @param pageSize     pageSize
         * @param ignoreNumber ignoreNumber
         * @return 标准地址 获取 用户上级 信息  下级列表
         */
        Observable<BaseBean<StandardAddressStairBean>> getGriddingFourRace(String upClassId, String gridId, String name, int pageSize, int ignoreNumber);
    }
}
