package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.PatrolsDetailItemBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2020 16:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface PatrolsDetailContract {

    interface View extends IView {
        void gotoMapTv(GridInformationBean gridInformationBean );
        void setPatrolsDetailListData(PatrolsDetailItemBean itemBean, boolean isLoadMore);
    }


    interface Model extends IModel {
        Observable<BaseBean<GridInformationBean>> getGetDepartment();
        Observable<BaseBean<PatrolsDetailItemBean>> getPatrolsDetailListData(int pageSize, int ignoreNumber, String id);
    }
}
