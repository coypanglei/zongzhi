package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.GuideModule;
import com.weique.overhaul.v2.mvp.contract.GuideContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.GuideActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/11/2019 14:18
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GuideModule.class, dependencies = AppComponent.class)
public interface GuideComponent {
    void inject(GuideActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GuideComponent.Builder view(GuideContract.View view);

        GuideComponent.Builder appComponent(AppComponent appComponent);

        GuideComponent build();
    }
}