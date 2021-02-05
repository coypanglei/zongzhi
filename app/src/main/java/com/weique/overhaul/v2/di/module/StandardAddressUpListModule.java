package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.contract.StandardAddressUpListContract;
import com.weique.overhaul.v2.mvp.model.StandardAddressUpListModel;
import com.weique.overhaul.v2.mvp.ui.adapter.StandardAddressUpListAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/23/2019 12:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class StandardAddressUpListModule {

    @Binds
    abstract StandardAddressUpListContract.Model bindStandardAddressUpListModel(StandardAddressUpListModel model);

    @ActivityScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(StandardAddressUpListContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }


    @ActivityScope
    @Provides
    static StandardAddressUpListAdapter provideStandardAddressUpListAdapter() {
        return new StandardAddressUpListAdapter();
    }
}