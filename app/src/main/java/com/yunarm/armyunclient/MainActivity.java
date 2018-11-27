package com.yunarm.armyunclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.yunarm.armyunclient.scoket.SocketManager;

public class MainActivity extends AppCompatActivity {


    private SurfaceView surface;
    private SocketManager socketManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surface = findViewById(R.id.surface_view);
        SurfaceHolder mholder = surface.getHolder();
        mholder.setFixedSize(1280, 720);
        mholder.setKeepScreenOn(true);
        socketManager = new SocketManager();


        mholder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                new Thread(() -> {
                    socketManager.startLoginSocket(holder);
                }).start();

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketManager.endSocket();
    }
}
