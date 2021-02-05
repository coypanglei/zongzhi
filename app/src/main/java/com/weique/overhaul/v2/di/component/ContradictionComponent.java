package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ContradictionModule;
import com.weique.overhaul.v2.mvp.contract.ContradictionContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.contradiction.ContradictionActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/02/2020 17:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ContradictionModule.class, dependencies = AppComponent.class)
public interface ContradictionComponent {
    void inject(ContradictionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ContradictionComponent.Builder view(ContradictionContract.View view);

        ContradictionComponent.Builder appComponent(AppComponent appComponent);

        ContradictionComponent build();
    }
}