package io.github.zauther.android.hive.hybrid.jsbridge.plugin;

import android.util.Log;

import java.util.Map;

import io.github.zauther.android.hive.api.plugins.base.IHiveCallback;
import io.github.zauther.android.hive.web.IWebView;

public abstract class AbsPlugin implements IPlugin {

    public void execInner(IWebView webView, String namespace, String method, String params, IHiveCallback<Map<String,Object>> callback) {
        try {
            exec(webView, namespace, method, params, callback);
        } catch (Throwable e) {
            Log.d("AbsPlugin", "" + e.toString());
        }

    }

}
