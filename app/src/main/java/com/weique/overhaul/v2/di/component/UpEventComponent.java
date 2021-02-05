package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.UpEventModule;
import com.weique.overhaul.v2.mvp.contract.UpEventContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.UpEventActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 15:12
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UpEventModule.class, dependencies = AppComponent.class)
public interface UpEventComponent {
    void inject(UpEventActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UpEventComponent.Builder view(UpEventContract.View view);

        UpEventComponent.Builder appComponent(AppComponent appComponent);

        UpEventComponent build();
    }
}