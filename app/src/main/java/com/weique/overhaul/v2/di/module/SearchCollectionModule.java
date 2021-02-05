package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.contract.SearchCollectionContract;
import com.weique.overhaul.v2.mvp.model.SearchCollectionModel;
import com.weique.overhaul.v2.mvp.ui.adapter.TreeListAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/25/2020 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SearchCollectionModule {

    @Binds
    abstract SearchCollectionContract.Model bindSearchCollectionModel(SearchCollectionModel model);


    @ActivityScope
    @Provides
    static TreeListAdapter provideTreeListAdapter() {
        return new TreeListAdapter(null);
    }

    @ActivityScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(SearchCollectionContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

}