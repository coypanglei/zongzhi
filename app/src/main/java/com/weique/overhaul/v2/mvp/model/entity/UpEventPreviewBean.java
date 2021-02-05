package com.weique.overhaul.v2.mvp.model.entity;


/**
 * @author  GK
 */
public class UpEventPreviewBean {
    /**
     * 图片
     */
    public static final int PHOTO_T = 1;
    /**
     * 录音
     */
    public static final int RECORD_T = 2;
    /**
     * 视频
     */
    public static final int VIDEO_T = 3;
    /**
     * 判断资源类型
     */
    int type;
    /**
     * 图片 /  视频 的 预览图
     */
    String preview;
    /**
     * 图片 / 录音 / 视频 文件地址
     */
    String resource_url;

    /**
     * 时长 - 视频/录音
     */
    String time;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getResource_url() {
        return resource_url;
    }

    public void setResource_url(String resource_url) {
        this.resource_url = resource_url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
