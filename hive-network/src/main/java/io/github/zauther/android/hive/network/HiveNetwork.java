package io.github.zauther.android.hive.network;

import android.util.Log;

import io.github.zauther.android.hive.network.jni.JNIHelper;

public class HiveNetwork {
    public static void test(){
       String version= JNIHelper.nativeVersion();
        Log.d("version" ,version);
        long prt= JNIHelper.nativeCreateCronetEngine();
        Log.d("prt" ,""+prt);
    }
}
