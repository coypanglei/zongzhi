package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ResourceSearchModule;
import com.weique.overhaul.v2.mvp.contract.ResourceSearchContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.visit.ResourceSearchActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/03/2019 17:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ResourceSearchModule.class, dependencies = AppComponent.class)
public interface ResourceSearchComponent {
    void inject(ResourceSearchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ResourceSearchComponent.Builder view(ResourceSearchContract.View view);

        ResourceSearchComponent.Builder appComponent(AppComponent appComponent);

        ResourceSearchComponent build();
    }
}