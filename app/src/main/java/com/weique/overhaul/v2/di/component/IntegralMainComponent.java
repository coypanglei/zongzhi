package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.IntegralMainModule;
import com.weique.overhaul.v2.mvp.contract.IntegralMainContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.IntegralMainActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/19/2020 11:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = IntegralMainModule.class, dependencies = AppComponent.class)
public interface IntegralMainComponent {
    void inject(IntegralMainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        IntegralMainComponent.Builder view(IntegralMainContract.View view);

        IntegralMainComponent.Builder appComponent(AppComponent appComponent);

        IntegralMainComponent build();
    }
}