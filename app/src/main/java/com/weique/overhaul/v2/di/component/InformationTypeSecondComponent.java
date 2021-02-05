package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.InformationTypeSecondModule;
import com.weique.overhaul.v2.mvp.contract.InformationTypeSecondContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationTypeSecondActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/27/2019 14:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = InformationTypeSecondModule.class, dependencies = AppComponent.class)
public interface InformationTypeSecondComponent {
    void inject(InformationTypeSecondActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InformationTypeSecondComponent.Builder view(InformationTypeSecondContract.View view);

        InformationTypeSecondComponent.Builder appComponent(AppComponent appComponent);

        InformationTypeSecondComponent build();
    }
}