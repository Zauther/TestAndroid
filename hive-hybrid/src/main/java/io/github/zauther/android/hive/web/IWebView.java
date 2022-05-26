package io.github.zauther.android.hive.web;

public interface IWebView {
    void evaluateJavascript(String javascript);
    void loadUrl(String url);
}
