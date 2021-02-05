package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.CircumMapModule;
import com.weique.overhaul.v2.mvp.contract.CircumMapContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.map.CircumMapActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/27/2020 17:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CircumMapModule.class, dependencies = AppComponent.class)
public interface CircumMapComponent {
    void inject(CircumMapActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CircumMapComponent.Builder view(CircumMapContract.View view);

        CircumMapComponent.Builder appComponent(AppComponent appComponent);

        CircumMapComponent build();
    }
}