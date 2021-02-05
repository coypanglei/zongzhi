package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MatterDetailTableModule;
import com.weique.overhaul.v2.mvp.contract.MatterDetailTableContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.matter.MatterDetailTableFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/01/2020 13:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MatterDetailTableModule.class, dependencies = AppComponent.class)
public interface MatterDetailTableComponent {
    void inject(MatterDetailTableFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MatterDetailTableComponent.Builder view(MatterDetailTableContract.View view);

        MatterDetailTableComponent.Builder appComponent(AppComponent appComponent);

        MatterDetailTableComponent build();
    }
}