package io.github.zauther.android.hive.web;

import android.content.Context;
import android.view.View;

public interface IWebView {
    void evaluateJavascript(String javascript);
    void loadUrl(String url);
    Context getContext();
    View getWebView();
}
