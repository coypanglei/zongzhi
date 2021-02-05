package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ContradictionAddSearchModule;
import com.weique.overhaul.v2.mvp.contract.ContradictionAddSearchContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.contradiction.ContradictionAddSearchActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2020 17:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ContradictionAddSearchModule.class, dependencies = AppComponent.class)
public interface ContradictionAddSearchComponent {
    void inject(ContradictionAddSearchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ContradictionAddSearchComponent.Builder view(ContradictionAddSearchContract.View view);

        ContradictionAddSearchComponent.Builder appComponent(AppComponent appComponent);

        ContradictionAddSearchComponent build();
    }
}