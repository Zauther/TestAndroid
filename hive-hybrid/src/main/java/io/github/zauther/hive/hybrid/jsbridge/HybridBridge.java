package io.github.zauther.hive.hybrid.jsbridge;

import android.util.Log;

public class HybridBridge {

    public static final String JS_API_PREFIX = "hivejsapi://";

    public static void onJSCall(String jsapiStr) {
        JSAPI jsapi = JSAPI.parse(jsapiStr);
        if (jsapi != null) {
            Log.d("=====", jsapi.method);
        }
    }


    public static void registerPlugin(){

    }
}
