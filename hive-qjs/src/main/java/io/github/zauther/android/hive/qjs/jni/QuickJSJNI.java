package io.github.zauther.android.hive.qjs.jni;

import android.util.Log;

import java.lang.reflect.Method;

public class QuickJSJNI {
    private static final String TAG = "quickjs4android";

    static {
        try {
            System.loadLibrary("quickjs4android");
//            System.loadLibrary("ndklogmodule");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    public static String eval() {
        return "";
    }

    public static String runJS(String js) {
        return js;
    }

    public native static long nativeCreateQJSRuntime();

    public native static void nativeSetMemoryLimit(long rtmInstance, long limit);

    public native static void nativeSetMaxStackSize(long rtmInstance, long size);

    public native static void nativeReleaseQJSRuntime(long rtmInstance);

    public native static long nativeNewQJSContext(long rtmInstance);

    public native static void nativeReleaseQJSContext(long ctxInstance);


    public native static long nativeEval(long ctxInstance, String code, String fileName, int flags);

    public native static int nativeEvalExpr(long rtmInstance, String expr);

    public native static int nativeGetInt(long jsValue);
    public native static boolean nativeGetBool(long jsValue);
    public native static double nativeGetFloat64(long jsValue);
    public native static String nativeGetString(long ctxInstance, long jsValue);
    public native static void nativeReleaseJSValue(long ctxInstance,long jsValue);

    //
    public native static long nativeGetGlobalObject(long ctxInstance);
    public native static long nativeNewJSObject(long ctxInstance);
//    public native static long nativeNewFunction(long ctxInstance,String methodSignature,String methodName,int length);
    public native static long nativeNewFunction(long ctxInstance, Object javaObject, String javaMethodName, String methodSignature,String jsMethodName, int length);
    public native static long setProperty(long ctxInstance, long thisObjInstance, String prop,long propObjInstance);

    public native static long nativeQJSUndefined();
}
