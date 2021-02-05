package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PartyContentArticleDetailModule;
import com.weique.overhaul.v2.mvp.contract.PartyContentArticleDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyContentArticleDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/08/2019 15:24
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PartyContentArticleDetailModule.class, dependencies = AppComponent.class)
public interface PartyContentArticleDetailComponent {
    void inject(PartyContentArticleDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartyContentArticleDetailComponent.Builder view(PartyContentArticleDetailContract.View view);

        PartyContentArticleDetailComponent.Builder appComponent(AppComponent appComponent);

        PartyContentArticleDetailComponent build();
    }
}