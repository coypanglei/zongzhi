package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.MessageListsContract;
import com.weique.overhaul.v2.mvp.model.MessageListsModel;
import com.weique.overhaul.v2.mvp.ui.adapter.MessageListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/10/2020 11:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class MessageListsModule {

    @Binds
    abstract MessageListsContract.Model bindMessageListsModel(MessageListsModel model);

    @FragmentScope
    @Provides
    static MessageListAdapter provideMessageListAdapter() {
        return new MessageListAdapter();
    }

    @FragmentScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(MessageListsContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(MessageListsContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getActivity())
                .colorResId(R.color.gray_eee)
                .sizeResId(R.dimen.dp_1)
                .build();
    }
}