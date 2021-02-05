package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PmModule;
import com.weique.overhaul.v2.mvp.contract.PmContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.economicmanagement.PmActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2020 11:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PmModule.class, dependencies = AppComponent.class)
public interface PmComponent {
    void inject(PmActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PmComponent.Builder view(PmContract.View view);

        PmComponent.Builder appComponent(AppComponent appComponent);

        PmComponent build();
    }
}