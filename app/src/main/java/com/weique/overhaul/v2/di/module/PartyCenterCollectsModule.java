package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.contract.PartyCenterCollectsContract;
import com.weique.overhaul.v2.mvp.model.PartyCenterCollectsModel;
import com.weique.overhaul.v2.mvp.ui.adapter.PartyCollectsItemAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Module
public abstract class PartyCenterCollectsModule {

    @Binds
    abstract PartyCenterCollectsContract.Model bindPartyCenterCollectsModel(PartyCenterCollectsModel model);

    @ActivityScope
    @Provides
    static PartyCollectsItemAdapter providePartyCollectListAdapter() {
        return new PartyCollectsItemAdapter();
    }
}