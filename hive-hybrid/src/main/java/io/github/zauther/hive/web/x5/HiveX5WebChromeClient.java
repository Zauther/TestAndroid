package io.github.zauther.hive.web.x5;

import android.util.Log;

import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.sdk.WebChromeClient;

import io.github.zauther.hive.hybrid.jsbridge.HybridBridge;

public class HiveX5WebChromeClient extends WebChromeClient {

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.d("=====",consoleMessage.message());
        if (consoleMessage.message().startsWith(HybridBridge.JS_API_PREFIX)){
            HybridBridge.onJSCall(consoleMessage.message());
            return true;
        }
        return super.onConsoleMessage(consoleMessage);
    }
}
