package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionItemBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/02/2020 17:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface ContradictionContract {

    interface View extends IView {
        void setContradictionListData(ContradictionItemBean data, boolean isLoadMore);

        void gotoMapTv(GridInformationBean gridInformationBean);

    }


    interface Model extends IModel {
        Observable<BaseBean<ContradictionItemBean>> getContradictionListData(int pageSize,int ignoreNumber, String StandardAddress, String status);

        Observable<BaseBean<GridInformationBean>> getGetDepartment(String departmentId);

    }
}
