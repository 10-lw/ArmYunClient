package com.yunarm.armyunclient.scoket;

public class SocketMessage {
    //主类型
    public static final int MSG_SHAKE = 0;    // 握手协议
    public static final int MSG_CONTROL = 1;    // 控制协议
    public static final int MSG_INPUT = 2;    // 操控/输入协议
    public static final int MSG_OUTPUT = 3;    // 输出协议
    public static final int MSG_AV = 4;    // 音视频协议


    //握手子类型
    public static final int MSG_SHAKE_ONLINE = 0;    // 上线请求
    public static final int MSG_SHAKE_ONLINE_R = 1;    // 上线应答
    public static final int MSG_SHAKE_OFFLINE = 2;    // 下线请求
    public static final int MSG_SHAKE_OFFLINE_R = 3;    // 下线应答
    public static final int MSG_SHAKE_KICK = 4;    // 强制踢出请求

    //控制子类型
    public static final int MSG_CONTROL_MTU = 0;    // MTU测试请求
    public static final int MSG_CONTROL_MTU_R = 1;    // MTU测试应答
    public static final int MSG_CONTROL_DELAY = 2;    // 延迟测试请求

    //AV 子类型
    public static final int MSG_AV_AUDIO = 0; //音频
    public static final int MSG_AV_VIDEO = 1;  // 视频

    //屏幕输出子类
    public static final int MSG_OUTPUT_SCREEN_OR = 1;
}
