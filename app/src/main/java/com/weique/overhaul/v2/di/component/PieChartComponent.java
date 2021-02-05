package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PieChartModule;
import com.weique.overhaul.v2.mvp.contract.PieChartContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.statistics.PieChartActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/14/2020 09:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PieChartModule.class, dependencies = AppComponent.class)
public interface PieChartComponent {
    void inject(PieChartActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PieChartComponent.Builder view(PieChartContract.View view);

        PieChartComponent.Builder appComponent(AppComponent appComponent);

        PieChartComponent build();
    }
}