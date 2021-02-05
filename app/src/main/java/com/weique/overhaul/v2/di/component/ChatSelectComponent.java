package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ChatSelectModule;
import com.weique.overhaul.v2.mvp.contract.ChatSelectContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.chat.voip.ChatSelectActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/21/2020 10:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ChatSelectModule.class, dependencies = AppComponent.class)
public interface ChatSelectComponent {
    void inject(ChatSelectActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChatSelectComponent.Builder view(ChatSelectContract.View view);

        ChatSelectComponent.Builder appComponent(AppComponent appComponent);

        ChatSelectComponent build();
    }
}