package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookItemBean;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookListBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.SignStaffListCommonBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/21/2020 14:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface SignStaffListContract {

    interface View extends IView {

        /**
         * @param list    list
         * @param needAdd needAdd 需要加载子view
         */
        void setDepartmentInfoListData(List<? extends SignStaffListCommonBean> list, boolean needAdd);
    }


    interface Model extends IModel {
        Observable<BaseBean<AddressBookItemBean>> getDepartmentInfoListData(int pageSize, int ignoreNumber, String departmentId);

        Observable<BaseBean<AddressBookListBean>> getSignStaffList(String departmentId);
    }
}
