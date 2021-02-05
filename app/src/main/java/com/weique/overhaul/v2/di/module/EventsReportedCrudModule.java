package com.weique.overhaul.v2.di.module;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.EventsReportedCrudContract;
import com.weique.overhaul.v2.mvp.model.EventsReportedCuedModel;
import com.weique.overhaul.v2.mvp.ui.adapter.NarrowDynamicFormAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Module
public abstract class EventsReportedCrudModule {

    @Binds
    abstract EventsReportedCrudContract.Model bindEventsReportedCuedModel(EventsReportedCuedModel model);

    @ActivityScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(EventsReportedCrudContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    static NarrowDynamicFormAdapter provideEventsReportedCrudAdapter() {
        return new NarrowDynamicFormAdapter(null);
    }

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(EventsReportedCrudContract.View view) {
        return new RxPermissions((FragmentActivity) view.getContext());
    }

    @ActivityScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(EventsReportedCrudContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.gray_eee)
                .margin(ArmsUtils.dip2px(view.getContext(),16), ArmsUtils.dip2px(view.getContext(),16))
                .sizeResId(R.dimen.dp_1)
                .build();
    }
}