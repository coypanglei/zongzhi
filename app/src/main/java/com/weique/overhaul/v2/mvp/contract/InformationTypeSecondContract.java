package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseSearchPopupBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.UserGideBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/27/2019 14:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface InformationTypeSecondContract {

    interface View extends IView {

        Context getContext();

        void setData(List<InformationTypeOneSecondBean.ElementListBean> informationTypeSecondBeans, boolean isLoadMore, int count, int type);

        void setGotoNewGuid(String s);

        void showPopup(List<? extends BaseSearchPopupBean> list, boolean isLoadMore);
    }


    interface Model extends IModel {

        Observable<BaseBean<InformationTypeOneSecondBean>> getGridResource(String id, String query, String departmentId, String standardAddressId, int pageSize, int ignoreNumber, Map map);

        Observable<BaseBean<String>> getGetNewGuidForApp();

        Observable<BaseBean<UserGideBean>> getGridList(int pageSize, int ignoreNumber, String keyword);

        /**
         * @param pageSize
         * @param ignoreNumber
         * @param departmentId
         * @param keyWord
         * @return
         */
        Observable<BaseBean<StandardAddressStairBean>> getStandardAddressByGridId(int pageSize, int ignoreNumber, String departmentId, String keyWord);
    }
}
