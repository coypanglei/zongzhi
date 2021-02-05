package com.weique.overhaul.v2.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementTableContract;
import com.weique.overhaul.v2.mvp.model.EconomicManagementModel;
import com.weique.overhaul.v2.mvp.ui.adapter.CommentCurrencyAdapter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2020 13:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class EconomicManagementModule {

    @Binds
    abstract EconomicManagementTableContract.Model bindEconomicManagementModel(EconomicManagementModel model);

}