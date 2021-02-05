package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ThirdPartySplashModule;
import com.weique.overhaul.v2.mvp.contract.ThirdPartySplashContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.ThirdPartySplashActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2020 11:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ThirdPartySplashModule.class, dependencies = AppComponent.class)
public interface ThirdPartySplashComponent {
    void inject(ThirdPartySplashActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ThirdPartySplashComponent.Builder view(ThirdPartySplashContract.View view);

        ThirdPartySplashComponent.Builder appComponent(AppComponent appComponent);

        ThirdPartySplashComponent build();
    }
}