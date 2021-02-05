package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PatrolsModule;
import com.weique.overhaul.v2.mvp.contract.PatrolsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.PatrolsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2020 14:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PatrolsModule.class, dependencies = AppComponent.class)
public interface PatrolsComponent {
    void inject(PatrolsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PatrolsComponent.Builder view(PatrolsContract.View view);

        PatrolsComponent.Builder appComponent(AppComponent appComponent);

        PatrolsComponent build();
    }
}