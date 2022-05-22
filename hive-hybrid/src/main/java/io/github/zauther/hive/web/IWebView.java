package io.github.zauther.hive.web;

public interface IWebView {
    void evaluateJavascript(String javascript);
    void loadUrl(String url);
}
