package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MessageDetailModule;
import com.weique.overhaul.v2.mvp.contract.MessageDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.message.MessageDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/08/2020 18:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MessageDetailModule.class, dependencies = AppComponent.class)
public interface MessageDetailComponent {
    void inject(MessageDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MessageDetailComponent.Builder view(MessageDetailContract.View view);

        MessageDetailComponent.Builder appComponent(AppComponent appComponent);

        MessageDetailComponent build();
    }
}