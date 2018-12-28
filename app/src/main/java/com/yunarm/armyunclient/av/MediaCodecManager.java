package com.yunarm.armyunclient.av;

import android.util.Log;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Description : 读取 H264文件送入解码器解码线程
 */
public class MediaCodecManager {

    //解码器
    private MediaCodecUtil util;
    //文件读取完成标识
    private boolean isFinish = false;
    //根据帧率获取的解码每帧需要休眠的时间,根据实际帧率进行操作
    private static final int PRE_FRAME_TIME = 1000 / 25;
    //按帧用来缓存h264数据
    private ConcurrentLinkedQueue<byte[]> frameList;
    //缓存最多的帧数
    private static final int MAX_FRAME_SIZE = 100;


    /**
     * 初始化解码器
     *
     * @param util 解码 Util
     */
    public MediaCodecManager(MediaCodecUtil util) {
        this.util = util;
        frameList = new ConcurrentLinkedQueue<>();
    }

    /**
     * 寻找指定 buffer 中 h264 头的开始位置
     *
     * @param data   数据
     * @param offset 偏移量
     * @param max    需要检测的最大值
     * @return h264头的开始位置 ,-1表示未发现
     */
    private int findHead(byte[] data, int offset, int max) {
        int i;
        for (i = offset; i <= max; i++) {
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
     * 判断是否是I帧/P帧头:
     * 00 00 00 01 65    (I帧)
     * 00 00 00 01 61 / 41   (P帧)
     * 00 00 00 01 67    (SPS)
     * 00 00 00 01 68    (PPS)
     *
     * @param data   解码数据
     * @param offset 偏移量
     * @return 是否是帧头
     */
    private boolean isHead(byte[] data, int offset) {
        boolean result = false;
        // 00 00 00 01 x
        if (data[offset] == 0x00 && data[offset + 1] == 0x00
                && data[offset + 2] == 0x00 && data[3] == 0x01 && isVideoFrameHeadType(data[offset + 4])) {
            result = true;
        }
        // 00 00 01 x
        if (data[offset] == 0x00 && data[offset + 1] == 0x00
                && data[offset + 2] == 0x01 && isVideoFrameHeadType(data[offset + 3])) {
            result = true;
        }
        return result;
    }

    /**
     * I帧或者P帧
     */
    private boolean isVideoFrameHeadType(byte head) {
        return head == (byte) 0x65 || head == (byte) 0x61 || head == (byte) 0x41
                || head == (byte) 0x67 || head == (byte) 0x68;
    }

    //视频解码
    private void onFrame(byte[] frame, int offset, int length) {
        if (util != null) {
            try {
                util.onFrame(frame, offset, length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("TAG", "mediaCodecUtil is NULL");
        }
    }

    //将视频数据添加到缓存List
    public void addFrame(byte[] frame) {
        if (frameList != null) {
            synchronized (frameList) {
                frameList.add(frame);
            }
        }

        //当长度多于MAX_FRAME_SIZE时,休眠2秒，避免OOM
//        if (frameList.size() > MAX_FRAME_SIZE) {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
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

    /**
     * 解码线程
     */
    private class DecodeThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isFinish || frameList.size() > 0) {
                if (frameList != null && frameList.size() > 0) {
                    synchronized (frameList) {
                        byte[] frame = frameList.poll();
                        onFrame(frame, 0, frame.length);
                    }
                }
            }
        }
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