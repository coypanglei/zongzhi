package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.NewMapModule;
import com.weique.overhaul.v2.mvp.contract.NewMapContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.map.NewMapActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/10/2020 10:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = NewMapModule.class, dependencies = AppComponent.class)
public interface NewMapComponent {
    void inject(NewMapActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NewMapComponent.Builder view(NewMapContract.View view);

        NewMapComponent.Builder appComponent(AppComponent appComponent);

        NewMapComponent build();
    }
}