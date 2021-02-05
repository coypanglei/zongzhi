package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.StandardAddressModule;
import com.weique.overhaul.v2.mvp.contract.StandardAddressContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.standardaddress.StandardAddressActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 16:45
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = StandardAddressModule.class, dependencies = AppComponent.class)
public interface StandardAddressComponent {
    void inject(StandardAddressActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StandardAddressComponent.Builder view(StandardAddressContract.View view);

        StandardAddressComponent.Builder appComponent(AppComponent appComponent);

        StandardAddressComponent build();
    }
}