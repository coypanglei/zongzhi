package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.TaskListModule;
import com.weique.overhaul.v2.di.module.TourVisitModule;
import com.weique.overhaul.v2.mvp.contract.TaskListContract;
import com.weique.overhaul.v2.mvp.ui.activity.task.TaskListActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/03/2019 16:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TaskListModule.class, dependencies = AppComponent.class)
public interface TaskListComponent {
    void inject(TaskListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TaskListComponent.Builder view(TaskListContract.View view);

        TaskListComponent.Builder appComponent(AppComponent appComponent);

        TaskListComponent build();
    }
}