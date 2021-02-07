package com.dds.webrtclib.ws;

import com.dds.webrtclib.bean.ConnectionInfoBean;

import org.webrtc.IceCandidate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 2019/1/3.
 * android_shuai@163.com
 */
public interface ISignalingEvents {

    // webSocket连接成功
    void onWebSocketOpen();

    // webSocket连接失败
    void onWebSocketOpenFailed(String msg);

    // 进入房间
    void onJoinToRoom(List<ConnectionInfoBean> connections, String myId, String name, String headUrl);

    // 有新人进入房间
    void onRemoteJoinToRoom(String socketId,String userName,String headUrl);

    void onRemoteIceCandidate(String socketId, IceCandidate iceCandidate);

    void onRemoteIceCandidateRemove(String socketId, List<IceCandidate> iceCandidates);


    void onRemoteOutRoom(String socketId);

    void onReceiveOffer(String socketId, String sdp);

    void onReceiverAnswer(String socketId, String sdp);

}
