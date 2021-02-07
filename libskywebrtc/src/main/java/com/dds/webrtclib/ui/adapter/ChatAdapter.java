package com.dds.webrtclib.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dds.webrtclib.ProxyVideoSink;
import com.dds.webrtclib.R;
import com.dds.webrtclib.bean.ChatRoomItemBean;

import org.webrtc.EglBase;
import org.webrtc.RendererCommon;
import org.webrtc.SurfaceViewRenderer;

import java.util.Iterator;
import java.util.List;

/**
 * @author GK
 * @description:
 * @date :2020/12/24 10:36
 */
public class ChatAdapter extends BaseQuickAdapter<ChatRoomItemBean, BaseViewHolder> {
    private EglBase rootEglBase;
    private boolean videoEnable;

    public ChatAdapter(EglBase rootEglBase, boolean videoEnable) {
        super(R.layout.surface_view_and_name);
        this.rootEglBase = rootEglBase;
        this.videoEnable = videoEnable;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ChatRoomItemBean item) {
        try {
            SurfaceViewRenderer renderer = helper.getView(R.id.svr);
            ImageView imagePhoto = helper.getView(R.id.image_photo);
            TextView name = helper.getView(R.id.name);
            if (item.getName() != null && item.getName().length() > 0) {
                name.setVisibility(View.VISIBLE);
                name.setText(item.getName());
            } else {
                name.setVisibility(View.GONE);
            }
            if (videoEnable) {
                renderer.setVisibility(View.VISIBLE);
                imagePhoto.setVisibility(View.GONE);
                renderer.init(rootEglBase.getEglBaseContext(), null);
                renderer.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
                renderer.setMirror(true);
                // set render
                if (item.getSink() == null) {
                    ProxyVideoSink sink = new ProxyVideoSink();
                    sink.setTarget(renderer);
                    item.setSink(sink);
                    if (item.getMediaStream().videoTracks.size() > 0) {
                        item.getMediaStream().videoTracks.get(0).addSink(sink);
                    }
                }
            } else {
                renderer.setVisibility(View.GONE);
                imagePhoto.setVisibility(View.VISIBLE);
                if (item.getImageUrl() != null) {
                    Glide.with(mContext).load(item.getImageUrl()).into(imagePhoto);
                }else{
                    imagePhoto.setImageResource(R.drawable.bay_p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setRemoveItem(String userId) {
        List<ChatRoomItemBean> data = getData();
        Iterator<ChatRoomItemBean> iterator = data.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            ChatRoomItemBean next = iterator.next();
            if (next.getId().equals(userId)) {
                iterator.remove();
            }
        }
        notifyItemRemoved(i);
    }
}
