package com.weique.overhaul.v2.mvp.model.entity;

public class PushMessageBean {
    private String RoomId;
    private String who;
    private String HeadUrl;
    private boolean videoEnable;

    public String getHeadUrl() {
        return HeadUrl;
    }

    public void setHeadUrl(String headUrl) {
        HeadUrl = headUrl;
    }

    public String getRoomId() {
        return RoomId;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public boolean getVideoEnable() {
        return videoEnable;
    }

    public void setVideoEnable(boolean videoEnable) {
        this.videoEnable = videoEnable;
    }
}
