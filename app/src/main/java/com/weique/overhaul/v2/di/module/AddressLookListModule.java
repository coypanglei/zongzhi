package com.weique.overhaul.v2.di.module;

import androidx.fragment.app.FragmentActivity;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.mvp.contract.AddressLookListContract;
import com.weique.overhaul.v2.mvp.model.AddressLookListModel;


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
@Module
public abstract class AddressLookListModule {

    @Binds
    abstract AddressLookListContract.Model bindAddressLookListModel(AddressLookListModel model);


    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(AddressLookListContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }
}