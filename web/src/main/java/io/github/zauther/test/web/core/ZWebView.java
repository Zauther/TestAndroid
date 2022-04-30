package io.github.zauther.test.web.core;

import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebView;

public class ZWebView extends WebView {
    public ZWebView(Context context) {
        this(context,null);
    }

    public ZWebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet,0);
    }

    public ZWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void init(){
        setWebChromeClient(new ZWebChromeClient(this));
        setWebViewClient(new ZWebViewClient(this));
    }

}
