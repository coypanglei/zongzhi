package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.UpEventPreviewBean;

import java.util.List;

/**
 * @author  GK
 */
public class UpEventPreviewAdapter extends BaseQuickAdapter<UpEventPreviewBean, BaseViewHolder> {

    private ImageLoader mImageLoader;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public UpEventPreviewAdapter(int layoutResId, @Nullable List<UpEventPreviewBean> data) {
        super(layoutResId, data);
        AppComponent mAppComponent = ArmsUtils.obtainAppComponentFromContext(mContext);
        mImageLoader = mAppComponent.imageLoader();
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, UpEventPreviewBean item) {
        try {
            switch (item.getType()) {
                case UpEventPreviewBean.PHOTO_T:
                    helper.getView(R.id.preview).setVisibility(View.VISIBLE);
                case UpEventPreviewBean.VIDEO_T:
                    helper.getView(R.id.video_flag).setVisibility(View.VISIBLE);
                    mImageLoader.loadImage(mContext,
                            ImageConfigImpl.builder()
                                    .url(item.getPreview())
                                    .imageView(helper.getView(R.id.preview))
                                    .build());
                    break;
                case UpEventPreviewBean.RECORD_T:
                    helper.getView(R.id.record_layout).setVisibility(View.VISIBLE);
                    helper.setText(R.id.record_time, item.getTime());
                    break;

                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
