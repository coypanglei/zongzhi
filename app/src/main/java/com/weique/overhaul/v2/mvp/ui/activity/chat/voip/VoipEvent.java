package com.weique.overhaul.v2.mvp.ui.activity.chat.voip;

import android.media.AudioManager;
import android.net.Uri;

import com.dds.skywebrtc.inter.ISkyEvent;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.ui.activity.chat.socket.SocketManager;

/**
 * Created by dds on 2019/8/25.
 * android_shuai@163.com
 */
public class VoipEvent implements ISkyEvent {
    private AsyncPlayer ringPlayer;

    public VoipEvent() {
        ringPlayer = new AsyncPlayer(null);
    }

    @Override
    public void createRoom(String room, int roomSize) {
        SocketManager.getInstance().createRoom(room, roomSize);
    }

    @Override
    public void sendInvite(String room, String users, boolean audioOnly) {
        SocketManager.getInstance().sendInvite(room, users, audioOnly);
    }

    @Override
    public void sendMeetingInvite(String userList) {
        SocketManager.getInstance().sendMeetingInvite(userList);

    }

    @Override
    public void sendRefuse(String inviteId, int refuseType) {
        SocketManager.getInstance().sendRefuse(inviteId, refuseType);
    }

    @Override
    public void sendCancel(String toId) {
        SocketManager.getInstance().sendCancel(toId);
    }

    @Override
    public void sendJoin(String room) {
        SocketManager.getInstance().sendJoin(room);
    }

    @Override
    public void sendRingBack(String targetId) {
        SocketManager.getInstance().sendRingBack(targetId);
    }

    @Override
    public void sendLeave(String room, String userId) {
        SocketManager.getInstance().sendLeave(room,userId);
    }


    @Override
    public void sendOffer(String userId, String sdp) {
        SocketManager.getInstance().sendOffer(userId, sdp);
    }

    @Override
    public void sendAnswer(String userId, String sdp) {
        SocketManager.getInstance().sendAnswer(userId, sdp);

    }

    @Override
    public void sendIceCandidate(String userId, String id, int label, String candidate) {
        SocketManager.getInstance().sendIceCandidate(userId, id, label, candidate);
    }


    //==============================================================================
    @Override
    public void shouldStartRing(boolean isComing) {
        if (isComing) {
            Uri uri = Uri.parse("android.resource://" + AppManager.getAppManager().getmApplication().getPackageName() + "/" + R.raw.incoming_call_ring);
            ringPlayer.play(AppManager.getAppManager().getmApplication(), uri, true, AudioManager.STREAM_RING);
        } else {
            Uri uri = Uri.parse("android.resource://" + AppManager.getAppManager().getmApplication().getPackageName() + "/" + R.raw.outgoing_call_ring);
            ringPlayer.play(AppManager.getAppManager().getmApplication(), uri, true, AudioManager.STREAM_RING);
        }
    }

    @Override
    public void shouldStopRing() {
        ringPlayer.stop();
    }
}
