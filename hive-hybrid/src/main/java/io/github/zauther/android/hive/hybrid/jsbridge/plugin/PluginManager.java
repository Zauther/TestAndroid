package io.github.zauther.android.hive.hybrid.jsbridge.plugin;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.zauther.android.hive.api.plugins.base.IHiveCallback;
import io.github.zauther.android.hive.hybrid.jsbridge.JSAPI;
import io.github.zauther.android.hive.web.IWebView;

public class PluginManager {

    static final Map<String, Class<? extends IPlugin>> pluginMaps = new ConcurrentHashMap<>();

    public static void register(String namespace, Class<? extends IPlugin> pluginClz) {
        pluginMaps.put(namespace, pluginClz);
    }


    public static Class<? extends IPlugin> findPlugin(String namespace) {
        return pluginMaps.get(namespace);
    }

    public static boolean existPlugin(String namespace) {
        return pluginMaps.containsKey(namespace);
    }

    public static IPlugin newPlugin(String namespace) {
        Class<? extends IPlugin> pluginClz = findPlugin(namespace);
        if (pluginClz == null) {
            return null;
        }
        try {
            return pluginClz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void callPlugin(IWebView webView, JSAPI jsapi) {
        if (jsapi == null || TextUtils.isEmpty(jsapi.namespace)||TextUtils.isEmpty(jsapi.method)) {
            return;
        }
        if(!existPlugin(jsapi.namespace)){
            return;
        }
        IPlugin plugin = newPlugin(jsapi.namespace);
        plugin.exec(webView, jsapi.namespace, jsapi.method, jsapi.params, new IHiveCallback<Map<String, Object>>() {
            @Override
            public void send(Map<String, Object> stringObjectMap) {
                Log.d("==jsapi.token===",jsapi.token);
//                webView.evaluateJavascript("console.log(`heloo:${window||window.hivejsapi}`)");

                webView.evaluateJavascript("javascript:window.hivejsapi.callback(\""+jsapi.token+"\","+new JSONObject().toJSONString(stringObjectMap) +",{\"failed\":\"I'm failed\"});");
            }
        });
    }

}
