package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.TaskListHomeModule;
import com.weique.overhaul.v2.mvp.contract.TaskListHomeContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.TaskListHomeFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2020 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = TaskListHomeModule.class, dependencies = AppComponent.class)
public interface TaskListHomeComponent {
    void inject(TaskListHomeFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TaskListHomeComponent.Builder view(TaskListHomeContract.View view);

        TaskListHomeComponent.Builder appComponent(AppComponent appComponent);

        TaskListHomeComponent build();
    }
}