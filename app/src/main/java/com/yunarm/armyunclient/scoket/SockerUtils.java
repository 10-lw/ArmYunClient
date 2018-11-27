package com.yunarm.armyunclient.scoket;

import java.io.IOException;
import java.io.InputStream;

public class SockerUtils {
    // byte数组长度为4, bytes[3]为高8位
    public static int bytes2Int(byte[] bytes){
        int value=0;
        value = ((bytes[3] & 0xff)<<24)|
                ((bytes[2] & 0xff)<<16)|
                ((bytes[1] & 0xff)<<8)|
                (bytes[0] & 0xff);
        return value;
    }

    public static byte[] intToBytes(int value )
    {
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }


    public static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (8 - ix) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    public static byte[] readData(InputStream inputStream, int len) {
        byte[] b = new byte[len];
        int l = 0;
        int r = 0;

        try {
            while (r < len) {
                l = inputStream.read(b, r, len - r);
                if (l == -1)
                    return b;
                r += l;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }
}
