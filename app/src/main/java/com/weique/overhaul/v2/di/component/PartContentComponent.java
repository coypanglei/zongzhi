package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.di.module.PartContentModule;
import com.weique.overhaul.v2.mvp.contract.PartContentContract;
import com.weique.overhaul.v2.mvp.ui.fragment.party.PartContentFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2019 17:36
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PartContentModule.class, dependencies = AppComponent.class)
public interface PartContentComponent {
    void inject(PartContentFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartContentComponent.Builder view(PartContentContract.View view);

        PartContentComponent.Builder appComponent(AppComponent appComponent);

        PartContentComponent build();
    }
}