package io.github.zauther.hive.hybrid.jsbridge;

import android.util.Log;

import io.github.zauther.hive.hybrid.jsbridge.plugin.IPlugin;
import io.github.zauther.hive.hybrid.jsbridge.plugin.PluginManager;
import io.github.zauther.hive.web.IWebView;

public class HybridBridge {

    public static final String JS_API_PREFIX = "hivejsapi://";

    public static void onJSCall(IWebView webView,String jsapiStr) {
        JSAPI jsapi = JSAPI.parse(jsapiStr);
        if (jsapi != null) {
            PluginManager.callPlugin(webView,jsapi);
        }
    }

    public static void callJS(String js) {

    }


    public static void registerPlugin(Class<? extends IPlugin> pluginClz) {

    }

    public static void registerPlugin(String namespace, Class<? extends IPlugin> pluginClz) {
        PluginManager.register(namespace,pluginClz);
    }
}
