package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.StandardAddressLookModule;
import com.weique.overhaul.v2.mvp.contract.StandardAddressLookContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.standardaddress.StandardAddressLookActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/26/2019 10:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = StandardAddressLookModule.class, dependencies = AppComponent.class)
public interface StandardAddressLookComponent {
    void inject(StandardAddressLookActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StandardAddressLookComponent.Builder view(StandardAddressLookContract.View view);

        StandardAddressLookComponent.Builder appComponent(AppComponent appComponent);

        StandardAddressLookComponent build();
    }
}