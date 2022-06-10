package io.github.zauther.android.hive.network.jni;

public class JNIHelper {
    static {
        System.loadLibrary("hive-network");
    }
    public static native String nativeVersion();

    public static native long nativeCreateCronetEngine();

    public static native long CronetEngineDestroy();


    public static native long Cronet_Engine_GetClientContext();
    // Cronet_Engine_GetVersionString

}
