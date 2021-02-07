package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookItemBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2019 09:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AddressBookContract {

    interface View extends IView {
        void setDepartmentInfoListData(AddressBookItemBean data, boolean isLoadMore);

        Activity getActivity();

        void getChatResult(String itemBean);

        void isOpen();
    }


    interface Model extends IModel {
        Observable<BaseBean<AddressBookItemBean>> getDepartmentInfoListData(int pageSize, int ignoreNumber, String DepartmentId);
        Observable<BaseBean<String>> setChatList(int pageSize, int ignoreNumber, List<String> alreadyList, String toString, boolean videoEnable);
    }
}
