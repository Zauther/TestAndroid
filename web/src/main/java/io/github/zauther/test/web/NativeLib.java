package io.github.zauther.test.web;

public class NativeLib {

    // Used to load the 'web' library on application startup.
    static {
        System.loadLibrary("web");
    }

    /**
     * A native method that is implemented by the 'web' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}