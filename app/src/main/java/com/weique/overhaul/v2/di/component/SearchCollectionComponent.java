package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.SearchCollectionModule;
import com.weique.overhaul.v2.mvp.contract.SearchCollectionContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.SearchCollectionActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/25/2020 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SearchCollectionModule.class, dependencies = AppComponent.class)
public interface SearchCollectionComponent {
    void inject(SearchCollectionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchCollectionComponent.Builder view(SearchCollectionContract.View view);

        SearchCollectionComponent.Builder appComponent(AppComponent appComponent);

        SearchCollectionComponent build();
    }
}