package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.weique.overhaul.v2.mvp.contract.EnterpriseInformationCollectionContract;
import com.weique.overhaul.v2.mvp.model.EnterpriseInformationCollectionModel;
import com.weique.overhaul.v2.mvp.ui.adapter.CommentCurrencyAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.EconomicManagementTitleAdapter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2020 09:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class EnterpriseInformationCollectionModule {

    @Binds
    abstract EnterpriseInformationCollectionContract.Model bindEnterpriseInformationCollectionModel(EnterpriseInformationCollectionModel model);

    @ActivityScope
    @Provides
    static CommentCurrencyAdapter provideCommentCurrencyAdapter() {
        return new CommentCurrencyAdapter();
    }
}