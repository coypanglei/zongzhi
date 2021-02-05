package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.weique.overhaul.v2.mvp.contract.PartyContentArticleDetailContract;
import com.weique.overhaul.v2.mvp.model.PartyContentArticleDetailModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/08/2019 15:24
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PartyContentArticleDetailModule {

    @Binds
    abstract PartyContentArticleDetailContract.Model bindPartyContentArticleDetailModel(PartyContentArticleDetailModel model);
}