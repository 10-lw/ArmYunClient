package com.yunarm.armyunclient.av;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 用于硬件解码(MediaCodec)H264的工具类
 */
public class MediaCodecUtil {


    private String TAG = "MediaCodecUtil";
    //解码后显示的surface
    private SurfaceHolder holder;
    private int width, height;
    //解码器
    private MediaCodec mCodec;
    private boolean isFirst = true;
    //解码器序号

    // 需要解码的类型
    private final static String MIME_TYPE = "video/avc"; // H.264 Advanced Video
    private final static int TIME_INTERNAL = 5;
    private final static int HEAD_OFFSET = 512;
    private int FrameRate = 25;

    /**
     * 初始化解码器
     *
     * @param holder 用于显示视频的surface
     * @param width  surface宽
     * @param height surface高
     *
     **/
    public MediaCodecUtil(SurfaceHolder holder, int width, int height) {
//        logger.d("MediaCodecUtil() called with: " + "holder = [" + holder + "], " +
//                "width = [" + width + "], height = [" + height + "]");
        this.holder = holder;
        this.width = width;
        this.height = height;
    }

    public MediaCodecUtil(SurfaceHolder holder) {
        this(holder, holder.getSurfaceFrame().width(), holder.getSurfaceFrame().height());
    }

    public void startCodec() {
        if (isFirst) {
            //第一次打开则初始化解码器
            initDecoder();
        }
    }

    private void initDecoder() {
        try {
            //根据需要解码的类型创建解码器
            mCodec = MediaCodec.createDecoderByType(MIME_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化MediaFormat
        Log.d("lkb", "create video format: type="+MIME_TYPE+" width="+width+" height="+height);
        MediaFormat mediaFormat = MediaFormat.createVideoFormat(MIME_TYPE, width, height);
        //配置MediaFormat以及需要显示的surface
        mCodec.configure(mediaFormat, holder.getSurface(), null, 0);
        //开始解码
        mCodec.start();
        isFirst = false;
    }

    int mCount = 0;
    MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

    public boolean onFrame(byte[] buf, int offset, int length) {

        Log.d("lkb", "util onFrame: length="+ length);
        long startDecodeTime = System.currentTimeMillis();
        //获取输入buffer
        Log.d("lkb", "queue Input Buffer +");
        int inputBufferId = mCodec.dequeueInputBuffer(-1);
        Log.d("lkb", "queue Input Buffer - id="+inputBufferId);
        if (inputBufferId >= 0){
            ByteBuffer inputBuffer = mCodec.getInputBuffer(inputBufferId);
            //inputBuffer.clear();
            inputBuffer.put(buf, offset, length);
            Log.d("lkb", "> inputBuffer");
            //mCodec.queueInputBuffer(inputBufferId, 0, length, mCount * TIME_INTERNAL, 0);
            mCodec.queueInputBuffer(inputBufferId, 0, length, System.currentTimeMillis(), 0);
            //mCodec.queueInputBuffer(inputBufferId, 0, length, 0, 0); //lkb???
            mCount++;
        }else {
            Log.d("lkb", "no avai input buffer");
        }

        Log.d("lkb", "dequeue Output Buffer +");
        while(true) {
            int outputBufferId = mCodec.dequeueOutputBuffer(bufferInfo, 0); //-1);
            Log.d("lkb", "dequeue Output Buffer - id=" + outputBufferId);
            if (outputBufferId >= 0) {
                Log.d("lkb", "> surfaceView");
                ByteBuffer outputBuffer = mCodec.getOutputBuffer(outputBufferId);
                MediaFormat bufferFormat = mCodec.getOutputFormat(outputBufferId); //bufferFormat is identical to outputFormat
                int videoWidth = bufferFormat.getInteger("width");
                int videoHeight = bufferFormat.getInteger("height");
                Log.d("lkb", "decoded video: " + videoWidth + " x " + videoHeight);


                mCodec.releaseOutputBuffer(outputBufferId, true); //true : 将解码的数据显示到surface上
            } else {
                Log.d("lkb", "no avai output buffer");
                break;
            }
        }

        return true;
    }

    /**
     * Find H264 frame head
     *
     * @param buffer
     * @param len
     * @return the offset of frame head, return 0 if can not find one
     */
    static int findHead(byte[] buffer, int len) {
        int i;
        for (i = HEAD_OFFSET; i < len; i++) {
            if (checkHead(buffer, i))
                break;
        }
        if (i == len)
            return 0;
        if (i == HEAD_OFFSET)
            return 0;
        return i;
    }

    /**
     * Check if is H264 frame head
     *
     * @param buffer
     * @param offset
     * @return whether the src buffer is frame head
     */
    static boolean checkHead(byte[] buffer, int offset) {
        // 00 00 00 01
        if (buffer[offset] == 0 && buffer[offset + 1] == 0
                && buffer[offset + 2] == 0 && buffer[3] == 1)
            return true;
        // 00 00 01
        if (buffer[offset] == 0 && buffer[offset + 1] == 0
                && buffer[offset + 2] == 1)
            return true;
        return false;
    }

    public void stopCodec() {
        try {
            mCodec.stop();
            mCodec.release();
            mCodec = null;
            isFirst = true;
        } catch (Exception e) {
            e.printStackTrace();
            mCodec = null;
        }
    }
}