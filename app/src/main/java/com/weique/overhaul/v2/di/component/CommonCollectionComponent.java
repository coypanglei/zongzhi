package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.CommonCollectionModule;
import com.weique.overhaul.v2.mvp.contract.CommonCollectionContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.CommonCollectionActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/25/2020 11:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CommonCollectionModule.class, dependencies = AppComponent.class)
public interface CommonCollectionComponent {
    void inject(CommonCollectionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommonCollectionComponent.Builder view(CommonCollectionContract.View view);

        CommonCollectionComponent.Builder appComponent(AppComponent appComponent);

        CommonCollectionComponent build();
    }
}