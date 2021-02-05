package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.TaskListContract;
import com.weique.overhaul.v2.mvp.contract.TaskListHomeContract;
import com.weique.overhaul.v2.mvp.model.TaskListHomeModel;
import com.weique.overhaul.v2.mvp.ui.adapter.TaskListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2020 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 * @author GreatKing
 */
@Module
public abstract class TaskListHomeModule {

    @Binds
    abstract TaskListHomeContract.Model bindTaskListHomeModel(TaskListHomeModel model);

    @Provides
    static LinearLayoutManager provideLinearLayoutManager(TaskListHomeContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @FragmentScope
    @Provides
    static TaskListAdapter provideTaskListAdapter() {
        return new TaskListAdapter();
    }

    @FragmentScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(TaskListHomeContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.theme_background)
                .sizeResId(R.dimen.dp_10)
                .build();
    }
}