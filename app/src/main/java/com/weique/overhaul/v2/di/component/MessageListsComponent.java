package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MessageListsModule;
import com.weique.overhaul.v2.mvp.contract.MessageListsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.message.MessageListsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/10/2020 11:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MessageListsModule.class, dependencies = AppComponent.class)
public interface MessageListsComponent {
    void inject(MessageListsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MessageListsComponent.Builder view(MessageListsContract.View view);

        MessageListsComponent.Builder appComponent(AppComponent appComponent);

        MessageListsComponent build();
    }
}