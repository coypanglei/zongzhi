package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MatterAddModule;
import com.weique.overhaul.v2.mvp.contract.MatterAddContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.matter.MatterAddActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 11:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MatterAddModule.class, dependencies = AppComponent.class)
public interface MatterAddComponent {
    void inject(MatterAddActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MatterAddComponent.Builder view(MatterAddContract.View view);

        MatterAddComponent.Builder appComponent(AppComponent appComponent);

        MatterAddComponent build();
    }
}