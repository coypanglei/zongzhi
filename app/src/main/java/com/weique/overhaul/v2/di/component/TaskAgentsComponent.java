package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.TaskAgentsModule;
import com.weique.overhaul.v2.mvp.contract.TaskAgentsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.TaskAgentsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2020 10:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = TaskAgentsModule.class, dependencies = AppComponent.class)
public interface TaskAgentsComponent {
    void inject(TaskAgentsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TaskAgentsComponent.Builder view(TaskAgentsContract.View view);

        TaskAgentsComponent.Builder appComponent(AppComponent appComponent);

        TaskAgentsComponent build();
    }
}