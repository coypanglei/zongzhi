package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.CollectDynamicModule;
import com.weique.overhaul.v2.mvp.contract.CollectDynamicContract;
import com.weique.overhaul.v2.mvp.ui.activity.CollectDynamicActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2019 10:53
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CollectDynamicModule.class, dependencies = AppComponent.class)
public interface CollectDynamicComponent {
    void inject(CollectDynamicActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CollectDynamicComponent.Builder view(CollectDynamicContract.View view);

        CollectDynamicComponent.Builder appComponent(AppComponent appComponent);

        CollectDynamicComponent build();
    }
}