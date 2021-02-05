package com.weique.overhaul.v2.mvp.model.entity;

public class HasDefaultPhotoBean {
    private String url;
    private int drawable;
    boolean isUrl = true;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public boolean isUrl() {
        return isUrl;
    }

    public void setUrl(boolean url) {
        isUrl = url;
    }
}
