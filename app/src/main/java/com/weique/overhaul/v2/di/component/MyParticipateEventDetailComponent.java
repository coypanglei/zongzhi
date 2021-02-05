package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MyParticipateEventDetailModule;
import com.weique.overhaul.v2.mvp.contract.MyParticipateEventDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.MyParticipateEventDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2019 16:07
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyParticipateEventDetailModule.class, dependencies = AppComponent.class)
public interface MyParticipateEventDetailComponent {
    void inject(MyParticipateEventDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyParticipateEventDetailComponent.Builder view(MyParticipateEventDetailContract.View view);

        MyParticipateEventDetailComponent.Builder appComponent(AppComponent appComponent);

        MyParticipateEventDetailComponent build();
    }
}