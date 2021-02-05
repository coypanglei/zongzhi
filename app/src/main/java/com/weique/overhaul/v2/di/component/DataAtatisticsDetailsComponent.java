package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.DataAtatisticsDetailsModule;
import com.weique.overhaul.v2.mvp.contract.DataAtatisticsDetailsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.datastatistics.DataAtatisticsDetailsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2020 13:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DataAtatisticsDetailsModule.class, dependencies = AppComponent.class)
public interface DataAtatisticsDetailsComponent {
    void inject(DataAtatisticsDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DataAtatisticsDetailsComponent.Builder view(DataAtatisticsDetailsContract.View view);

        DataAtatisticsDetailsComponent.Builder appComponent(AppComponent appComponent);

        DataAtatisticsDetailsComponent build();
    }
}