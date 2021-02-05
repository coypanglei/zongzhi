package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PatrolsDetailModule;
import com.weique.overhaul.v2.mvp.contract.PatrolsDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.PatrolsDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2020 16:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PatrolsDetailModule.class, dependencies = AppComponent.class)
public interface PatrolsDetailComponent {
    void inject(PatrolsDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PatrolsDetailComponent.Builder view(PatrolsDetailContract.View view);

        PatrolsDetailComponent.Builder appComponent(AppComponent appComponent);

        PatrolsDetailComponent build();
    }
}