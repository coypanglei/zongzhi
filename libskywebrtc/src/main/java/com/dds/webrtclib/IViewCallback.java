package com.dds.webrtclib;

import org.webrtc.MediaStream;

/**
 * Created by dds on 2017/10/23.
 */

public interface IViewCallback {

    void onSetLocalStream(MediaStream stream, String socketId,String name, String headUrl);

    void onAddRemoteStream(MediaStream stream, String socketId, String userName, String headUrl);

    void onCloseWithId(String socketId);

}
