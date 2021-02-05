package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.TaskAgentsContract;
import com.weique.overhaul.v2.mvp.model.TaskAgentsModel;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2020 10:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class TaskAgentsModule {

    @Binds
    abstract TaskAgentsContract.Model bindTaskAgentsModel(TaskAgentsModel model);

    @FragmentScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(TaskAgentsContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getActivity())
                .colorResId(R.color.gray_eee)
                .margin(ArmsUtils.dip2px(view.getActivity(), 16), ArmsUtils.dip2px(view.getActivity(), 16))
                .sizeResId(R.dimen.dp_1)
                .build();
    }


}