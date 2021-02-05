package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.InformationCollectionContract;
import com.weique.overhaul.v2.mvp.model.InformationCollectionModel;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationCollectionAdapter;

import java.util.ArrayList;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 09:39
 *
 *
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class InformationCollectionModule {

    @Binds
    abstract InformationCollectionContract.Model bindInformationCollectionModel(InformationCollectionModel model);

    @ActivityScope
    @Provides
    static GridLayoutManager provideGridLayoutManager(InformationCollectionContract.View view) {
        return new GridLayoutManager(view.getActivity(), 2, RecyclerView.VERTICAL, false);
    }

    @ActivityScope
    @Provides
    static InformationCollectionAdapter provideInformationCollectionAdapter() {
        return new InformationCollectionAdapter(R.layout.activity_information_collection_item, new ArrayList<>());
    }
}