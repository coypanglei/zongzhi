package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MyParticipateEventModule;
import com.weique.overhaul.v2.mvp.contract.MyParticipateEventContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.MyParticipateEventFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/17/2019 16:02
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MyParticipateEventModule.class, dependencies = AppComponent.class)
public interface MyParticipateEventComponent {
    void inject(MyParticipateEventFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyParticipateEventComponent.Builder view(MyParticipateEventContract.View view);

        MyParticipateEventComponent.Builder appComponent(AppComponent appComponent);

        MyParticipateEventComponent build();
    }
}