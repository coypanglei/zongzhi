package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MatterDetailModule;
import com.weique.overhaul.v2.mvp.contract.MatterDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.matter.MatterDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 11:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MatterDetailModule.class, dependencies = AppComponent.class)
public interface MatterDetailComponent {
    void inject(MatterDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MatterDetailComponent.Builder view(MatterDetailContract.View view);

        MatterDetailComponent.Builder appComponent(AppComponent appComponent);

        MatterDetailComponent build();
    }
}