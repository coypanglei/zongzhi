package com.weique.overhaul.v2.di.module;

import androidx.fragment.app.FragmentActivity;

import com.jess.arms.di.scope.ActivityScope;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.mvp.contract.AddressBookContract;
import com.weique.overhaul.v2.mvp.model.AddressBookModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


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
@Module
public abstract class AddressBookModule {

    @Binds
    abstract AddressBookContract.Model bindAddressBookModel(AddressBookModel model);

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(AddressBookContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }
}