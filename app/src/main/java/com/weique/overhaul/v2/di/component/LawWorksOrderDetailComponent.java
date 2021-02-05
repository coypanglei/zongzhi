package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.LawWorksOrderDetailModule;
import com.weique.overhaul.v2.mvp.contract.LawWorksOrderDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.lawworks.LawWorksOrderDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/23/2020 13:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LawWorksOrderDetailModule.class, dependencies = AppComponent.class)
public interface LawWorksOrderDetailComponent {
    void inject(LawWorksOrderDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LawWorksOrderDetailComponent.Builder view(LawWorksOrderDetailContract.View view);

        LawWorksOrderDetailComponent.Builder appComponent(AppComponent appComponent);

        LawWorksOrderDetailComponent build();
    }
}