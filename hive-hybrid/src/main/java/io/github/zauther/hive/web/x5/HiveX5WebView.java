package io.github.zauther.hive.web.x5;

import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;

import io.github.zauther.hive.web.IWebView;

public class HiveX5WebView extends WebView implements IWebView {
    public HiveX5WebView(Context context, boolean b) {
        super(context, b);
    }

    public HiveX5WebView(Context context) {
        super(context);
    }

    public HiveX5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HiveX5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override
    public void evaluateJavascript(String javascript) {
        evaluateJavascript(javascript, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {

            }
        });
    }
}
