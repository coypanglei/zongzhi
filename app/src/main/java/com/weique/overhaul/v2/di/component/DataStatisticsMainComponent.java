package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.DataStatisticsMainModule;
import com.weique.overhaul.v2.mvp.contract.DataStatisticsMainContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.datastatistics.DataStatisticsMainActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/01/2020 17:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DataStatisticsMainModule.class, dependencies = AppComponent.class)
public interface DataStatisticsMainComponent {
    void inject(DataStatisticsMainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DataStatisticsMainComponent.Builder view(DataStatisticsMainContract.View view);

        DataStatisticsMainComponent.Builder appComponent(AppComponent appComponent);

        DataStatisticsMainComponent build();
    }
}