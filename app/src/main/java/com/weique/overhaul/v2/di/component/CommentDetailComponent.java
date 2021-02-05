package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.CommentDetailModule;
import com.weique.overhaul.v2.mvp.contract.CommentDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.CommentDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2019 11:43
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CommentDetailModule.class, dependencies = AppComponent.class)
public interface CommentDetailComponent {
    void inject(CommentDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommentDetailComponent.Builder view(CommentDetailContract.View view);

        CommentDetailComponent.Builder appComponent(AppComponent appComponent);

        CommentDetailComponent build();
    }
}