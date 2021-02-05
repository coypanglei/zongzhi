package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.contract.LawWorksInformContract;
import com.weique.overhaul.v2.mvp.model.LawWorksInformModel;
import com.weique.overhaul.v2.mvp.ui.adapter.LawWorksInformAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.LawWorksOrderAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/22/2020 15:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class LawWorksInformModule {

    @Binds
    abstract LawWorksInformContract.Model bindLawWorksInformModel(LawWorksInformModel model);

    @FragmentScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(LawWorksInformContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @FragmentScope
    @Provides
    static LawWorksInformAdapter provideLawWorksInformAdapter() {
        return new LawWorksInformAdapter();
    }
}