package com.yunarm.armyunclient.av;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 播放aac音频文件
 */
public class AACAudioCodecManager {

    //音频解码器
    private AACDecoderUtil audioUtil;
    //文件读取完成标识
    private boolean isFinish = false;
    //这个值用于找到第一个帧头后，继续寻找第二个帧头，如果解码失败可以尝试缩小这个值
    private int FRAME_MIN_LEN = 50;
    //一般AAC帧大小不超过200k,如果解码失败可以尝试增大这个值
    private static int FRAME_MAX_LEN = 100 * 1024;
    //根据帧率获取的解码每帧需要休眠的时间,根据实际帧率进行操作
    private int PRE_FRAME_TIME = 1000 / 50;
    //记录获取的帧数
    private int count = 0;

    private ConcurrentLinkedQueue<byte[]> frameList;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public AACAudioCodecManager() {
        audioUtil = new AACDecoderUtil();
        audioUtil.start();
        frameList = new ConcurrentLinkedQueue<>();
    }


    public void startDecodeThread() {
        new AudioDecodeThread().start();
    }

    public void addAudioData(byte[] data) {
        if (frameList != null) {
            synchronized (frameList) {
                frameList.add(data);
            }
        }
    }

    /**
     * 解码线程
     */
    private class AudioDecodeThread extends Thread {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run() {
            super.run();
            while (!isFinish || frameList.size() > 0) {
                if (frameList != null && frameList.size() > 0) {
//                    synchronized (frameList) {
                        byte[] frame = frameList.poll();
                        audioUtil.decode(frame, 0, frame.length);
//                        audioUtil.decode(frame, frame.length);
//                    }

                }
            }
        }
    }


    /**
     * 寻找指定buffer中AAC帧头的开始位置
     *
     * @param startIndex 开始的位置
     * @param data       数据
     * @param max        需要检测的最大值
     * @return
     */
    private int findHead(byte[] data, int startIndex, int max) {
        int i;
        for (i = startIndex; i <= max; i++) {
            //发现帧头
            if (isHead(data, i))
                break;
        }
        //检测到最大值，未发现帧头
        if (i == max) {
            i = -1;
        }
        return i;
    }

    /**
     * 判断aac帧头
     */
    private boolean isHead(byte[] data, int offset) {
        boolean result = false;
        if (data[offset] == (byte) 0xFF && data[offset + 1] == (byte) 0xF1
                && data[offset + 3] == (byte) 0x80) {
            result = true;
        }
        return result;
    }

    //修眠
    private void sleepThread(long startTime, long endTime) {
        //根据读文件和解码耗时，计算需要休眠的时间
        long time = PRE_FRAME_TIME - (endTime - startTime);
        if (time > 0) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}