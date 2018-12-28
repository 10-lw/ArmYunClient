package com.yunarm.armyunclient.av;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Created by SZQ on 2018/11/22 15:31
 * @describe xxxxxx
 */

public class AudioCodecManager {

    public static ConcurrentLinkedQueue<byte[]> writeQueue = new ConcurrentLinkedQueue<byte[]>();
    private AACDecoderUtil mAudioDevice = null;
    private boolean isRun = true;

    public AudioCodecManager() {
    }


    // 初始化音频播放设备
    public void initAudioDecoder() {
        mAudioDevice = AACDecoderUtil.getInstance();
    }

    public void startDecode() {
        mAudioDevice.start();
        new DecodeThread().start();
    }

    public void stopDecoder() {
        isRun = false;
        if (null != mAudioDevice) {
            clearCache();
            mAudioDevice.stop();
        }
    }

    /**
     * 解码线程
     */
    private class DecodeThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (isRun) {
                if (writeQueue != null && !writeQueue.isEmpty()) {
                    byte[] frame = getFrame();
                    Log.d("tag", "=============");
                    if (frame != null) {
                        boolean decode = mAudioDevice.decode(frame, 0, frame.length);
                    }
                }
            }
        }
    }

    public void putData(byte[] data) {
        if (writeQueue != null && writeQueue.size() <= 40) {
            writeQueue.add(data);
        } else if (writeQueue != null) {
            writeQueue.clear();
        }
    }

    public void clearCache() {
        if (!writeQueue.isEmpty()) {
            writeQueue.clear();
        }
    }

    public byte[] getFrame() {
        if (null == writeQueue) {
            return null;
        }
        if (!writeQueue.isEmpty()) {
            byte[] bytes = writeQueue.poll();
            return bytes;
        }
        return null;
    }

    public int getSize() {
        return writeQueue.size();
    }
}
