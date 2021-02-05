package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.BusinessInterviewDetailModule;
import com.weique.overhaul.v2.mvp.contract.BusinessInterviewDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.BusinessInterviewDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2020 10:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BusinessInterviewDetailModule.class, dependencies = AppComponent.class)
public interface BusinessInterviewDetailComponent {
    void inject(BusinessInterviewDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BusinessInterviewDetailComponent.Builder view(BusinessInterviewDetailContract.View view);

        BusinessInterviewDetailComponent.Builder appComponent(AppComponent appComponent);

        BusinessInterviewDetailComponent build();
    }
}