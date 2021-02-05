package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.DayEpidemicModule;
import com.weique.overhaul.v2.mvp.contract.DayEpidemicContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.DayEpidemicActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/13/2020 18:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DayEpidemicModule.class, dependencies = AppComponent.class)
public interface DayEpidemicComponent {
    void inject(DayEpidemicActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DayEpidemicComponent.Builder view(DayEpidemicContract.View view);

        DayEpidemicComponent.Builder appComponent(AppComponent appComponent);

        DayEpidemicComponent build();
    }
}