package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ResourceSearchDetailModule;
import com.weique.overhaul.v2.mvp.contract.ResourceSearchDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.visit.ResourceSearchDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/04/2019 11:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ResourceSearchDetailModule.class, dependencies = AppComponent.class)
public interface ResourceSearchDetailComponent {
    void inject(ResourceSearchDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ResourceSearchDetailComponent.Builder view(ResourceSearchDetailContract.View view);

        ResourceSearchDetailComponent.Builder appComponent(AppComponent appComponent);

        ResourceSearchDetailComponent build();
    }
}