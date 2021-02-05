package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.SummaryStatisticsModule;
import com.weique.overhaul.v2.mvp.contract.SummaryStatisticsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.datastatistics.SummaryStatisticsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2020 09:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SummaryStatisticsModule.class, dependencies = AppComponent.class)
public interface SummaryStatisticsComponent {
    void inject(SummaryStatisticsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SummaryStatisticsComponent.Builder view(SummaryStatisticsContract.View view);

        SummaryStatisticsComponent.Builder appComponent(AppComponent appComponent);

        SummaryStatisticsComponent build();
    }
}