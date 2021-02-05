package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.contract.PartySearchContract;
import com.weique.overhaul.v2.mvp.model.PartySearchModel;
import com.weique.overhaul.v2.mvp.ui.adapter.PartyContentFragmentAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/14/2019 10:54
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PartySearchModule {

    @Binds
    abstract PartySearchContract.Model bindPartySearchModel(PartySearchModel model);

    @ActivityScope
    @Provides
    static PartyContentFragmentAdapter providePartyCollectListAdapter() {
        return new PartyContentFragmentAdapter();
    }
}