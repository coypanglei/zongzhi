package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.PartySearchModule;
import com.weique.overhaul.v2.mvp.contract.PartySearchContract;
import com.weique.overhaul.v2.mvp.ui.activity.SearchActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/14/2019 10:54
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PartySearchModule.class, dependencies = AppComponent.class)
public interface PartySearchComponent {
    void inject(SearchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartySearchComponent.Builder view(PartySearchContract.View view);

        PartySearchComponent.Builder appComponent(AppComponent appComponent);

        PartySearchComponent build();
    }
}