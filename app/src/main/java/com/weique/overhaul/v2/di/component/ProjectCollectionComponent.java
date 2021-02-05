package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ProjectCollectionModule;
import com.weique.overhaul.v2.mvp.contract.ProjectCollectionContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.economicmanagement.ProjectCollectionActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2020 15:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ProjectCollectionModule.class, dependencies = AppComponent.class)
public interface ProjectCollectionComponent {
    void inject(ProjectCollectionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ProjectCollectionComponent.Builder view(ProjectCollectionContract.View view);

        ProjectCollectionComponent.Builder appComponent(AppComponent appComponent);

        ProjectCollectionComponent build();
    }
}