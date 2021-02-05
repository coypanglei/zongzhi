package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.AddressAddAlertModule;
import com.weique.overhaul.v2.mvp.contract.AddressAddAlertContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.standardaddress.StandardAddressAddAlertActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/22/2019 17:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddressAddAlertModule.class, dependencies = AppComponent.class)
public interface AddressAddAlertComponent {
    void inject(StandardAddressAddAlertActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddressAddAlertComponent.Builder view(AddressAddAlertContract.View view);

        AddressAddAlertComponent.Builder appComponent(AppComponent appComponent);

        AddressAddAlertComponent build();
    }
}