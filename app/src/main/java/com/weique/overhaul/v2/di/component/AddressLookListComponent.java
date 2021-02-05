package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.AddressLookListModule;
import com.weique.overhaul.v2.mvp.contract.AddressLookListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.addressbook.AddressLookListActivity;


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
@ActivityScope
@Component(modules = AddressLookListModule.class, dependencies = AppComponent.class)
public interface AddressLookListComponent {
    void inject(AddressLookListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddressLookListComponent.Builder view(AddressLookListContract.View view);

        AddressLookListComponent.Builder appComponent(AppComponent appComponent);

        AddressLookListComponent build();
    }
}