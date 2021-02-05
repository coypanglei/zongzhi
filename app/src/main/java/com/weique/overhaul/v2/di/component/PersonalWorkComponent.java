package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PersonalWorkModule;
import com.weique.overhaul.v2.mvp.contract.PersonalWorkContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.PersonalWorkActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/05/2021 15:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PersonalWorkModule.class, dependencies = AppComponent.class)
public interface PersonalWorkComponent {
    void inject(PersonalWorkActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PersonalWorkComponent.Builder view(PersonalWorkContract.View view);

        PersonalWorkComponent.Builder appComponent(AppComponent appComponent);

        PersonalWorkComponent build();
    }
}