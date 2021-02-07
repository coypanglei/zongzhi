package com.dds.webrtclib.bean;

/**
 * @author GK
 * @description:
 * @date :2021/1/11 12:02
 */
public class ConnectionInfoBean {
    String socketId;
    String userName;
    String headUrl;


    public ConnectionInfoBean(String socketId, String userName, String headUrl) {
        this.socketId = socketId;
        this.userName = userName;
        this.headUrl = headUrl;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
