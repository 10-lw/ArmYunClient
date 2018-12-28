package com.yunarm.armyunclient.scoket;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.armnet.player.avpkg.ffmpeg.Player;
import com.google.flatbuffers.FlatBufferBuilder;
import com.yunarm.armyunclient.av.AACAudioCodecManager;
import com.yunarm.armyunclient.av.MediaCodecManager;
import com.yunarm.armyunclient.av.MediaCodecUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import CSProto.ControlAVTrans;
import CSProto.ControlIFrame;
import CSProto.OutputScreen;
import CSProto.ShakeKick;
import CSProto.ShakeOnline;
import CSProto.ShakeOnlineR;

import static com.yunarm.armyunclient.scoket.SockerUtils.bytes2Int;
import static com.yunarm.armyunclient.scoket.SockerUtils.intToBytes;
import static com.yunarm.armyunclient.scoket.SockerUtils.readData;


public class SocketManager {

    private static Socket socket;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static boolean start = false;
    private static MediaCodecManager codecManager;
    private AACAudioCodecManager audioCodecManager;
    private boolean hardDecode = true;
    private Player videoDecodeManager;
    private Surface surface;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void startLoginSocket(SurfaceHolder surfaceHolder) {
        try {
            socket = new Socket("192.168.1.52", 8888);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            surface = surfaceHolder.getSurface();

            if (hardDecode) {
                //硬解视频
                MediaCodecUtil util = new MediaCodecUtil(surfaceHolder);
                util.startCodec();
                codecManager = new MediaCodecManager(util);
                codecManager.startDecodeThread();
            } else {
                videoDecodeManager = new Player();
//                videoDecodeManager.startDecodeThread();
                videoDecodeManager.initDecode();
                videoDecodeManager.setSurfaceView(surface);
                videoDecodeManager.setEventHandler((buffer, len) -> {

                });

            }

            audioCodecManager = new AACAudioCodecManager();
            audioCodecManager.startDecodeThread();

            onRequestShakeOnLine();

            while (socket.isConnected() && start) {
                //接收协议头
                byte[] header = readData(inputStream, 2);
                byte type = header[0];
                byte subtype = header[1];
                byte[] length = readData(inputStream, 4);
                int len = bytes2Int(length);


                //接收协议体
                byte[] body = readData(inputStream, len);
//                Log.d("tag", "=====type: " + type + ", subtype: " + subtype + ", len: " + len);
                if (len > 0) {
                    handleSocketByType(type, subtype, body);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void endSocket() {
        start = false;
        if (socket != null && socket.isConnected()) {
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onRequestShakeOnLine() {
        try {
            //上线请求
            FlatBufferBuilder build = new FlatBufferBuilder();
            int i = build.createString("34");
            int s = build.createString("12");  ///sessionId
            int p = build.createString("13");  ///padcode
            int a = build.createString("14");  ///appName
            int online = ShakeOnline.createShakeOnline(build, i, s, p, a, 0);
            build.finish(online);


            ByteBuffer buf = build.dataBuffer();
            //先发送协议头
            byte ty = 0; //
            byte subty = 0;
            byte[] dataLen = intToBytes(buf.limit() - buf.position());
            outputStream.write(ty);
            outputStream.write(subty);
            outputStream.write(dataLen);

            //发送协议体
            byte[] tmp = new byte[buf.limit() - buf.position()];
            buf.get(tmp, 0, buf.limit() - buf.position());
            outputStream.write(tmp);
            start = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSocketByType(byte typeByte, byte subTypeByte, byte[] body) {
        int type = byte2Int(typeByte);
        int subType = byte2Int(subTypeByte);
        switch (type) {
            case SocketMessage.MSG_SHAKE:
                switch (subType) {
                    case SocketMessage.MSG_SHAKE_ONLINE_R:
                        onShakeOnLineReply(body);
                        break;
                    case SocketMessage.MSG_SHAKE_KICK:
                        onShakeKick(body);
                        break;
                }
                break;
            case SocketMessage.MSG_AV:
                switch (subType) {
                    case SocketMessage.MSG_AV_AUDIO:
                        onAvAudioReply(body);
                        break;
                    case SocketMessage.MSG_AV_VIDEO:
                        // 视频数据
                        onAvVideoReply(body);
                        break;
                }
                break;
            case SocketMessage.MSG_OUTPUT:
                switch (subType) {
                    case SocketMessage.MSG_OUTPUT_SCREEN_OR:
                        onScreenOrientationChange(body);
                        break;
                }
                break;
        }

    }

    private void onShakeKick(byte[] body) {
        ShakeKick shakeKick = ShakeKick.getRootAsShakeKick(ByteBuffer.wrap(body));
//        Log.d("tag", "========code: " + shakeKick.code() + " msg: " + shakeKick.msg());
    }

    private void onScreenOrientationChange(byte[] body) {
        OutputScreen screen = OutputScreen.getRootAsOutputScreen(ByteBuffer.wrap(body));
        int height = screen.height();
        int width = screen.width();
        byte rotation = screen.rotation();
//        Log.d("tag", "=========onScreenOrientationChange====height: " + height + " width: " + width + " rotation: " + rotation);

    }
    //一般H264帧大小不超过200k,如果解码失败可以尝试增大这个值
    private static int MAX_FRAME_BUF_LEN = 300 * 1024;
    byte[] frameBuffer = new byte[MAX_FRAME_BUF_LEN];
    private void onAvVideoReply(byte[] body) {
        int fpTpye = body[0];
//        Log.d("tag", "=======onAvVideoReply========type: " + fpTpye);
        if (hardDecode) {
            System.arraycopy(body, 1 ,frameBuffer, 0 ,body.length -1);
            codecManager.addFrame(frameBuffer);
        } else {
//            videoDecodeManager.addFrame(body);
            boolean decode = videoDecodeManager.decode(body, body.length);
            if (!decode) {

            }
        }
    }

    private void requestIFrame() {
        FlatBufferBuilder bufferBuilder = new FlatBufferBuilder();
//        ControlIFrame.createControlIFrame()
    }

    private void onAvAudioReply(byte[] body) {
//        Log.d("tag", "=======onAvAudioReply========" + body.length);
//        if (body[0] == 1) {
            audioCodecManager.addAudioData(body);
//        }

    }

    private void onShakeOnLineReply(byte[] body) {
        ShakeOnlineR sh = ShakeOnlineR.getRootAsShakeOnlineR(ByteBuffer.wrap(body));
        String app = sh.app();
        int code = sh.code();
        String id = sh.id();
        String msg = sh.msg();
        String ser = sh.server();
        String token = sh.token();
        int type = sh.type();
//        Log.d("jsocket", "=====app:" + app + ",code:" + code + ",id:" + id + ",msg:" + msg + ",ser:" + ser + ",token:" + token + ",type:" + type);
        if (msg.equals("ok") || code == 0) {
            startRequestFmt();
        }
    }

    private void startRequestFmt() {
        //请求视频
        FlatBufferBuilder build = new FlatBufferBuilder();
        int avTrans = ControlAVTrans.createControlAVTrans(build, true, true, false);
        build.finish(avTrans);

        try {
            ByteBuffer buf = build.dataBuffer();
            //先发送协议头
            byte type = 1; //
            byte subtype = 8;
            byte[] dataLen = intToBytes(buf.limit() - buf.position());
            outputStream.write(type);
            outputStream.write(subtype);
            outputStream.write(dataLen);

            //发送协议体
            byte[] tmp = new byte[buf.limit() - buf.position()];
            buf.get(tmp, 0, buf.limit() - buf.position());
            outputStream.write(tmp);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public int byte2Int(byte b) {
        return b & 0xff;
    }
}
