package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.WorkRecordModule;
import com.weique.overhaul.v2.mvp.contract.WorkRecordContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.WorkRecordFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/17/2019 09:11
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = WorkRecordModule.class, dependencies = AppComponent.class)
public interface WorkRecordComponent {
    void inject(WorkRecordFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WorkRecordComponent.Builder view(WorkRecordContract.View view);

        WorkRecordComponent.Builder appComponent(AppComponent appComponent);

        WorkRecordComponent build();
    }
}