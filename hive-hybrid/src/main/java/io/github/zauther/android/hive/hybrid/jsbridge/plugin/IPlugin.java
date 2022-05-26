package io.github.zauther.android.hive.hybrid.jsbridge.plugin;

import io.github.zauther.android.hive.web.IWebView;

public interface IPlugin {
    void exec(IWebView webView, String namespace, String method, String params, ICallback callback);
}
