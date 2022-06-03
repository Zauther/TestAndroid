package cn.cwiki.android.hive.qjs;

public class NativeLib {

    // Used to load the 'qjs' library on application startup.
    static {
        System.loadLibrary("qjs");
    }

    /**
     * A native method that is implemented by the 'qjs' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}