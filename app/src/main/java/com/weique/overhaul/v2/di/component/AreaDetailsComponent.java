package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.AreaDetailsModule;
import com.weique.overhaul.v2.mvp.contract.AreaDetailsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.datastatistics.AreaDetailsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2020 09:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = AreaDetailsModule.class, dependencies = AppComponent.class)
public interface AreaDetailsComponent {
    void inject(AreaDetailsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AreaDetailsComponent.Builder view(AreaDetailsContract.View view);

        AreaDetailsComponent.Builder appComponent(AppComponent appComponent);

        AreaDetailsComponent build();
    }
}