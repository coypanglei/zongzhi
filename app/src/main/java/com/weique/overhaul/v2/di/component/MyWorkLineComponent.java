package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MyWorkLineModule;
import com.weique.overhaul.v2.mvp.contract.MyWorkLineContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.workline.MyWorkLineActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/06/2020 16:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyWorkLineModule.class, dependencies = AppComponent.class)
public interface MyWorkLineComponent {
    void inject(MyWorkLineActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyWorkLineComponent.Builder view(MyWorkLineContract.View view);

        MyWorkLineComponent.Builder appComponent(AppComponent appComponent);

        MyWorkLineComponent build();
    }
}