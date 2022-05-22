package io.github.zauther.hive.hybrid.jsbridge.plugin;

import io.github.zauther.hive.web.IWebView;

public interface IPlugin {
    void exec(IWebView webView, String namespace, String method, String params, ICallback callback);
}
