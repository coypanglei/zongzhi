package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.AreaDetailSecondModule;
import com.weique.overhaul.v2.mvp.contract.AreaDetailSecondContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.datastatistics.AreaDetailSecondActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2020 15:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AreaDetailSecondModule.class, dependencies = AppComponent.class)
public interface AreaDetailSecondComponent {
    void inject(AreaDetailSecondActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AreaDetailSecondComponent.Builder view(AreaDetailSecondContract.View view);

        AreaDetailSecondComponent.Builder appComponent(AppComponent appComponent);

        AreaDetailSecondComponent build();
    }
}