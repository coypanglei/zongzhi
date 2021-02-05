package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MessageListModule;
import com.weique.overhaul.v2.mvp.contract.MessageListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.message.MessageListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/08/2020 15:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MessageListModule.class, dependencies = AppComponent.class)
public interface MessageListComponent {
    void inject(MessageListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MessageListComponent.Builder view(MessageListContract.View view);

        MessageListComponent.Builder appComponent(AppComponent appComponent);

        MessageListComponent build();
    }
}