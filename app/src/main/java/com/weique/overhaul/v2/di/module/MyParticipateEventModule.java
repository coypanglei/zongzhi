package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.MyParticipateEventContract;
import com.weique.overhaul.v2.mvp.model.MyParticipateEventModel;
import com.weique.overhaul.v2.mvp.ui.adapter.TaskAgentsAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/17/2019 16:02
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class MyParticipateEventModule {

    @Binds
    abstract MyParticipateEventContract.Model bindMyParticipateEventModel(MyParticipateEventModel model);
    @FragmentScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(MyParticipateEventContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getActivity())
                .colorResId(R.color.gray_eee)
                .sizeResId(R.dimen.dp_1)
                .build();
    }
    @FragmentScope
    @Provides
    static BaseQuickAdapter provideHomeBacklogListAdapter() {
        return new TaskAgentsAdapter();
    }
    @FragmentScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(MyParticipateEventContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }
}