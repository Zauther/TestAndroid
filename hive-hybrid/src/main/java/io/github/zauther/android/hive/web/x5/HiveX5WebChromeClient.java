package io.github.zauther.android.hive.web.x5;

import android.util.Log;

import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.sdk.WebChromeClient;

import io.github.zauther.android.hive.hybrid.jsbridge.HybridBridge;
import io.github.zauther.android.hive.web.IWebView;

public class HiveX5WebChromeClient extends WebChromeClient {
    IWebView webView;

    public HiveX5WebChromeClient(IWebView webView) {
        this.webView = webView;
    }
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.d("=====",consoleMessage.message());
        if (consoleMessage.message().startsWith(HybridBridge.JS_API_PREFIX)){
            HybridBridge.onJSCall(webView,consoleMessage.message());
            return true;
        }
        return super.onConsoleMessage(consoleMessage);
    }
}
