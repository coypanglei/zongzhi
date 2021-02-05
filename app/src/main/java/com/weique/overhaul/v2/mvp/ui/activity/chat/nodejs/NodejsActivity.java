package com.weique.overhaul.v2.mvp.ui.activity.chat.nodejs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.weique.overhaul.v2.R;


/**
 *
 * @author dds
 * @date 2018/11/7
 */
@SuppressLint("Registered")
public class NodejsActivity extends AppCompatActivity {
    private EditText et_signal;
    private EditText et_room;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodejs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initVar();

    }

    private void initView() {
        et_signal = findViewById(R.id.et_signal);
        et_room = findViewById(R.id.et_room);
    }

    private void initVar() {
//        et_signal.setText("ws://192.168.20.80:3000/");
        try {
            et_signal.setText("wss://47.93.186.97/wss");
            et_room.setText("112233");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*-------------------------- nodejs版本服务器测试--------------------------------------------*/
    public void JoinRoomSingleVideo(View view) {
        WebrtcUtil.callSingle(this,
                et_signal.getText().toString(),
                et_room.getText().toString().trim(),
                true);
    }

    public void JoinRoomSingleAudio(View view) {
        try {
            WebrtcUtil.callSingle(this,
                    et_signal.getText().toString(),
                    et_room.getText().toString().trim(),
                    false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void JoinRoom(View view) {
        try {
            WebrtcUtil.call(this, et_signal.getText().toString(), et_room.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
