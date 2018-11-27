package com.yunarm.armyunclient;

public class MyJni {
    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromYourJNI();
}
