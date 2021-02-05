package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.UpEventContract;
import com.weique.overhaul.v2.mvp.model.UpEventModel;
import com.weique.overhaul.v2.mvp.ui.adapter.UpEventPreviewAdapter;

import java.util.ArrayList;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 15:12
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class UpEventModule {

    @Binds
    abstract UpEventContract.Model bindUpEventModel(UpEventModel model);

    @ActivityScope
    @Provides
    static UpEventPreviewAdapter providesUpEventPreviewAdapter(UpEventContract.View view) {
        return new UpEventPreviewAdapter(R.layout.activity_up_event_resource_item, new ArrayList<>());
    }
}