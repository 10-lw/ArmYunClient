package com.armnet.player.avpkg.ffmpeg;

import com.yunarm.armyunclient.av.MediaCodecManager;

import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 * 软解视频 h264
 */
public class Player {
    private LinkedList<byte[]> frameList;
    private boolean isFinish = false;

    static {
        System.loadLibrary("decode");
    }

    public Player() {
        frameList = new LinkedList<>();
    }

    public interface EventHandler {
        void onEventPI(ByteBuffer buffer, int len);
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public boolean initDecode() {
        return init();
    }

    public void closeDecode() {
        close();
    }

    private boolean startDecode(byte[] data, int len) {
        return decode(data, len);
    }


    //将视频数据添加到缓存List
    public void addFrame(byte[] frame) {
        if (frameList != null) {
            synchronized (frameList) {
                int pos = frameList.size();
                frameList.add(pos, frame);
            }
        }
    }

    //结束线程
    public void stopThread() {
        isFinish = true;
    }

    //开始解码
    public void startDecodeThread() {
        //开启解码线程
        new DecodeThread().start();
    }

    public void setSurfaceView(Object surface) throws Exception {
        setSurface(surface);
    }

    private void onEventPI(ByteBuffer buffer, int len) throws Exception {
        if (eventHandler == null)
            throw new Exception("Must call setEventHandler first.");
        eventHandler.onEventPI(buffer, len);
    }

    private EventHandler eventHandler = null;

    private native boolean init();

    private native void close();

    private native void setSurface(Object surface);

    public native boolean decode(byte[] data, int len);

    private class DecodeThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isFinish || frameList.size() > 0) {
                if (frameList != null && frameList.size() > 0) {
                    synchronized (frameList) {
                        byte[] frame = frameList.removeFirst();
                        try {
                            decode(frame, frame.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
