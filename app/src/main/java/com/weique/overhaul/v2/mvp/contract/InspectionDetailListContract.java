package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchDetailItemBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2019 16:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface InspectionDetailListContract {

    interface View extends IView {
        void gotoMapTv(GridInformationBean gridInformationBean );
        void setInspectionDetailListData(ResourceSearchDetailItemBean data, boolean isLoadMore);

    }


    interface Model extends IModel {
        Observable<BaseBean<GridInformationBean>> getGetDepartment(String departmentId);
        Observable<BaseBean<ResourceSearchDetailItemBean>> getInspectionDetailListData(int pageSize, int ignoreNumber, String resourceId, String elementTypeId);
    }
}
