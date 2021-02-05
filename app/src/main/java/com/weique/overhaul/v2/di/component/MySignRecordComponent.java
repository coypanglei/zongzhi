package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MySignRecordModule;
import com.weique.overhaul.v2.mvp.contract.MySignRecordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.sign.MySignRecordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/24/2019 14:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MySignRecordModule.class, dependencies = AppComponent.class)
public interface MySignRecordComponent {
    void inject(MySignRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MySignRecordComponent.Builder view(MySignRecordContract.View view);

        MySignRecordComponent.Builder appComponent(AppComponent appComponent);

        MySignRecordComponent build();
    }
}