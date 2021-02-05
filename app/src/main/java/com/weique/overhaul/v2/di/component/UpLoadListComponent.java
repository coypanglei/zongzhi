package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.UpLoadListModule;
import com.weique.overhaul.v2.mvp.contract.UpLoadListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.UpLoadListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/15/2019 17:24
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UpLoadListModule.class, dependencies = AppComponent.class)
public interface UpLoadListComponent {
    void inject(UpLoadListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UpLoadListComponent.Builder view(UpLoadListContract.View view);

        UpLoadListComponent.Builder appComponent(AppComponent appComponent);

        UpLoadListComponent build();
    }
}