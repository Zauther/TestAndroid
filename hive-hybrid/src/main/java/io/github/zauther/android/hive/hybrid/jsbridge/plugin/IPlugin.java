package io.github.zauther.android.hive.hybrid.jsbridge.plugin;

import java.util.Map;

import io.github.zauther.android.hive.api.plugins.base.IHiveCallback;
import io.github.zauther.android.hive.web.IWebView;

public interface IPlugin {
    void exec(IWebView webView, String namespace, String method, String params, IHiveCallback<Map<String,Object>> callback);
}
