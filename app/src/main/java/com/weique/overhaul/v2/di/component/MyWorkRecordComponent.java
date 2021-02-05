package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MyWorkRecordModule;
import com.weique.overhaul.v2.mvp.contract.MyWorkRecordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.MyWorkRecordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/16/2019 13:31
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyWorkRecordModule.class, dependencies = AppComponent.class)
public interface MyWorkRecordComponent {
    void inject(MyWorkRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyWorkRecordComponent.Builder view(MyWorkRecordContract.View view);

        MyWorkRecordComponent.Builder appComponent(AppComponent appComponent);

        MyWorkRecordComponent build();
    }
}