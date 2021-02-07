package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookListBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2019 15:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AddressLookListSearchContract {

    interface View extends IView {
        void setAddressBookListData(AddressBookListBean data, boolean isLoadMore);

        void setAllAddressBookListData(AddressBookListBean data, boolean isLoadMore);

        Activity getActivity();

        void getChatResult(String itemBean);

        void isOpen();
    }


    interface Model extends IModel {
        Observable<BaseBean<AddressBookListBean>> getAddressBookListData(int pageSize, int ignoreNumber, String DepartmentId, String name);

        Observable<BaseBean<AddressBookListBean>> getAllAddressBookListData(int pageSize, int ignoreNumber, String name);

        Observable<BaseBean<String>> setChatList(int pageSize, int ignoreNumber, List<String> list, String roomId, boolean videoEnable);
    }
}
