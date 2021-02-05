package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MatterListModule;
import com.weique.overhaul.v2.mvp.contract.MatterListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.matter.MatterListActivity;
import com.weique.overhaul.v2.mvp.ui.fragment.matter.MatterListFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 11:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MatterListModule.class, dependencies = AppComponent.class)
public interface MatterListComponent {
    void inject(MatterListActivity activity);
    void inject(MatterListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MatterListComponent.Builder view(MatterListContract.View view);

        MatterListComponent.Builder appComponent(AppComponent appComponent);

        MatterListComponent build();
    }
}