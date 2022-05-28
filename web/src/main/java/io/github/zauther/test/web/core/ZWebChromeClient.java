package io.github.zauther.test.web.core;

import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.sdk.WebChromeClient;

public class ZWebChromeClient extends WebChromeClient {
    public ZWebChromeClient(ZWebView zWebView) {

    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }
}
