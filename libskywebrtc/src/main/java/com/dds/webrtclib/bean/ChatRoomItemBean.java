package com.dds.webrtclib.bean;

import com.dds.webrtclib.ProxyVideoSink;

import org.webrtc.MediaStream;

/**
 * @author GK
 * @description:
 * @date :2020/12/23 10:25
 */
public class ChatRoomItemBean {
    /**
     * 用户id
     */
    private String id;
    /**
     * 媒体流
     */
    private MediaStream mediaStream;
    private ProxyVideoSink sink;
    /**
     * 姓名
     */
    private String name;
    /**
     * 用户头像
     */
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MediaStream getMediaStream() {
        return mediaStream;
    }

    public void setMediaStream(MediaStream mediaStream) {
        this.mediaStream = mediaStream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProxyVideoSink getSink() {
        return sink;
    }

    public void setSink(ProxyVideoSink sink) {
        this.sink = sink;
    }
}
