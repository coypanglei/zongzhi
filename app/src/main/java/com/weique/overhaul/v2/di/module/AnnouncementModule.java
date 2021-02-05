package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.AnnouncementContract;
import com.weique.overhaul.v2.mvp.model.AnnouncementModel;
import com.weique.overhaul.v2.mvp.ui.adapter.AnnouncementListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/10/2020 13:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class AnnouncementModule {

    @Binds
    abstract AnnouncementContract.Model bindAnnouncementModel(AnnouncementModel model);

    @FragmentScope
    @Provides
    static AnnouncementListAdapter provideAnnouncementListAdapter() {
        return new AnnouncementListAdapter();
    }

    @FragmentScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(AnnouncementContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(AnnouncementContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getActivity())
                .colorResId(R.color.gray_eee)
                .sizeResId(R.dimen.dp_1)
                .build();
    }
}