package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.di.module.AnnouncementModule;
import com.weique.overhaul.v2.mvp.contract.AnnouncementContract;
import com.weique.overhaul.v2.mvp.ui.fragment.message.AnnouncementFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/10/2020 13:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = AnnouncementModule.class, dependencies = AppComponent.class)
public interface AnnouncementComponent {
    void inject(AnnouncementFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AnnouncementComponent.Builder view(AnnouncementContract.View view);

        AnnouncementComponent.Builder appComponent(AppComponent appComponent);

        AnnouncementComponent build();
    }
}