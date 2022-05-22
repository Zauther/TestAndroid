package io.github.zauther.hive.hybrid.inner.plugins;

import android.util.Log;

import io.github.zauther.hive.hybrid.jsbridge.plugin.AbsPlugin;
import io.github.zauther.hive.hybrid.jsbridge.plugin.ICallback;
import io.github.zauther.hive.web.IWebView;

public class SystemInfoPlugin extends AbsPlugin {
    @Override
    public void exec(IWebView webView, String namespace, String method, String params, ICallback callback) {
        Log.i("=====", namespace + ", " + method + ", " + params + ", ");
        String test = "";
        webView.evaluateJavascript("callback(\"SystemInfoPlugin\",\"\",\"\");");
    }
}
