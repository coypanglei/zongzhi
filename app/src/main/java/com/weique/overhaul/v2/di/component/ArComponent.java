package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ArModule;
import com.weique.overhaul.v2.mvp.contract.ArContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.ar.ArActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/22/2020 11:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ArModule.class, dependencies = AppComponent.class)
public interface ArComponent {
    void inject(ArActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ArComponent.Builder view(ArContract.View view);

        ArComponent.Builder appComponent(AppComponent appComponent);

        ArComponent build();
    }
}