package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.IntegratedWithModule;
import com.weique.overhaul.v2.mvp.contract.IntegratedWithContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.IntegratedWithActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 09:48
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = IntegratedWithModule.class, dependencies = AppComponent.class)
public interface IntegratedWithComponent {
    void inject(IntegratedWithActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        IntegratedWithComponent.Builder view(IntegratedWithContract.View view);

        IntegratedWithComponent.Builder appComponent(AppComponent appComponent);

        IntegratedWithComponent build();
    }
}