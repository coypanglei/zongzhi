package com.weique.overhaul.v2.mvp.model.entity;

/**
 * @author GreatKing
 */
public class InformationItemPictureBean {

    public static final int IS_URL = 0;
    public static final int IS_DRAWABLE = 1;
    public static final int IS_VIS_DELETE = 2;

    public InformationItemPictureBean(int drawableIntRes) {
        this.type = IS_DRAWABLE;
        this.drawableIntRes = drawableIntRes;
    }

    public InformationItemPictureBean(String imageUrl) {
        this.imageUrl = imageUrl;
        this.type = IS_URL;
    }

    public InformationItemPictureBean(String imageUrl,int isVis) {
        this.imageUrl = imageUrl;
        this.type = isVis;
    }


    private String imageUrl;
    private int type;
    private int drawableIntRes;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDrawableIntRes() {
        return drawableIntRes;
    }

    public void setDrawableIntRes(int drawableIntRes) {
        this.drawableIntRes = drawableIntRes;
    }

    @Override
    public String toString() {
        return "InformationItemPictureBean{" +
                "imageUrl='" + imageUrl + '\'' +
                ", type=" + type +
                ", drawableIntRes=" + drawableIntRes +
                '}';
    }
}
