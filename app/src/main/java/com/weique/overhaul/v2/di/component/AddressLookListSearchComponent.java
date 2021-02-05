package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.AddressLookListSearchModule;
import com.weique.overhaul.v2.mvp.contract.AddressLookListSearchContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.addressbook.AddressLookListSearchActivity;


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
@ActivityScope
@Component(modules = AddressLookListSearchModule.class, dependencies = AppComponent.class)
public interface AddressLookListSearchComponent {
    void inject(AddressLookListSearchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddressLookListSearchComponent.Builder view(AddressLookListSearchContract.View view);

        AddressLookListSearchComponent.Builder appComponent(AppComponent appComponent);

        AddressLookListSearchComponent build();
    }
}