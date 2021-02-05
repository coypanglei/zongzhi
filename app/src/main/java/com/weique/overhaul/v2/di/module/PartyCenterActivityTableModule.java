package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.weique.overhaul.v2.mvp.contract.PartyCenterActivityTableContract;
import com.weique.overhaul.v2.mvp.model.PartyCenterActivityTableModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2019 13:31
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PartyCenterActivityTableModule {

    @Binds
    abstract PartyCenterActivityTableContract.Model bindPartyCenterActivityTableModel(PartyCenterActivityTableModel model);
}