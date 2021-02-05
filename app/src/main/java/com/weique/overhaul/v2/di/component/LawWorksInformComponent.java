package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.LawWorksInformModule;
import com.weique.overhaul.v2.mvp.contract.LawWorksInformContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.lawworks.LawWorksInformFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/22/2020 15:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = LawWorksInformModule.class, dependencies = AppComponent.class)
public interface LawWorksInformComponent {
    void inject(LawWorksInformFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LawWorksInformComponent.Builder view(LawWorksInformContract.View view);

        LawWorksInformComponent.Builder appComponent(AppComponent appComponent);

        LawWorksInformComponent build();
    }
}