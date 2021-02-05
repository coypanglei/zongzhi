package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.KnowledgeBaseModule;
import com.weique.overhaul.v2.mvp.contract.KnowledgeBaseContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.knowledge.KnowledgeBaseActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2019 09:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = KnowledgeBaseModule.class, dependencies = AppComponent.class)
public interface KnowledgeBaseComponent {
    void inject(KnowledgeBaseActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        KnowledgeBaseComponent.Builder view(KnowledgeBaseContract.View view);

        KnowledgeBaseComponent.Builder appComponent(AppComponent appComponent);

        KnowledgeBaseComponent build();
    }
}