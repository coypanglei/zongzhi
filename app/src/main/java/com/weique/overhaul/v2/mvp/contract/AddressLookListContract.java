package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookListBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2019 11:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AddressLookListContract {

    interface View extends IView {
        void setAddressBookListData(AddressBookListBean data, boolean isLoadMore);

        Activity getActivity();
    }


    interface Model extends IModel {
        Observable<BaseBean<AddressBookListBean>> getAddressBookListData(int pageSize,int ignoreNumber, String  DepartmentId,String name);

    }
}
