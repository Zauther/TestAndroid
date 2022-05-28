package io.github.zauther.android.hive.hybrid.inner.plugins;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.zauther.android.hive.api.plugins.HivePlugins;
import io.github.zauther.android.hive.api.plugins.base.IHiveCallback;
import io.github.zauther.android.hive.hybrid.jsbridge.plugin.AbsPlugin;
import io.github.zauther.android.hive.web.IWebView;


public class SystemInfoPlugin extends AbsPlugin {
    @Override
    public void exec(IWebView webView, String namespace, String method, String params, IHiveCallback<Map<String,Object>> callback) {
        Log.i("=====", namespace + ", " + method + ", " + params + ", ");

//        webView.evaluateJavascript("(function(window){\n" +
//                "window.hivejsapi.callback['SystemInfo_getOS']('','','')\n" +
//                "})(window);");

        HivePlugins.execOn(webView.getContext(),namespace,method, new HashMap<>(),callback);
    }
}
