package io.github.zauther.android.hive.hybrid.jsbridge.plugin;

import android.util.Log;

import io.github.zauther.android.hive.web.IWebView;

public abstract class AbsPlugin implements IPlugin {

    public void execInner(IWebView webView, String namespace, String method, String params, ICallback callback) {
        try {
            exec(webView, namespace, method, params, callback);
        } catch (Throwable e) {
            Log.d("AbsPlugin", "" + e.toString());
        }
    }

}
