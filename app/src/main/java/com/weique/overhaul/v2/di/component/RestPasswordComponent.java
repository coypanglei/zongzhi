package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.RestPasswordModule;
import com.weique.overhaul.v2.mvp.contract.RestPasswordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.RestPasswordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/15/2020 14:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RestPasswordModule.class, dependencies = AppComponent.class)
public interface RestPasswordComponent {
    void inject(RestPasswordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RestPasswordComponent.Builder view(RestPasswordContract.View view);

        RestPasswordComponent.Builder appComponent(AppComponent appComponent);

        RestPasswordComponent build();
    }
}