package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ContradictionAddModule;
import com.weique.overhaul.v2.mvp.contract.ContradictionAddContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.contradiction.ContradictionAddActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2020 10:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ContradictionAddModule.class, dependencies = AppComponent.class)
public interface ContradictionAddComponent {
    void inject(ContradictionAddActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ContradictionAddComponent.Builder view(ContradictionAddContract.View view);

        ContradictionAddComponent.Builder appComponent(AppComponent appComponent);

        ContradictionAddComponent build();
    }
}