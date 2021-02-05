package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.weique.overhaul.v2.mvp.contract.EconomicManagementBaseInfoContract;
import com.weique.overhaul.v2.mvp.model.EconomicManagementBaseInfoModel;
import com.weique.overhaul.v2.mvp.ui.adapter.CommentCurrencyAdapter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2020 11:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class EconomicManagementBaseInfoModule {

    @Binds
    abstract EconomicManagementBaseInfoContract.Model bindEconomicManagementBaseInfoModel(EconomicManagementBaseInfoModel model);


    @FragmentScope
    @Provides
    static CommentCurrencyAdapter provideCommentCurrencyAdapter() {
        return new CommentCurrencyAdapter();
    }
}