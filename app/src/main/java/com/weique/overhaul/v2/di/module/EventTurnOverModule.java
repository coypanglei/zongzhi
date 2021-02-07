package com.weique.overhaul.v2.di.module;

import com.weique.overhaul.v2.mvp.contract.EventTurnOverContract;
import com.weique.overhaul.v2.mvp.model.EventTurnOverModel;

import dagger.Binds;
import dagger.Module;

/**
 * @author GK
 * @description:
 * @date :2021/1/26 15:54
 */
@Module
public abstract class EventTurnOverModule {
    @Binds
    abstract EventTurnOverContract.Model bindEventTurnOverModel(EventTurnOverModel model);
}
