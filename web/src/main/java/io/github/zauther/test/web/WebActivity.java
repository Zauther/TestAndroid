package io.github.zauther.test.web;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import io.github.zauther.test.web.core.ZWebView;

public class WebActivity extends AppCompatActivity {


    ZWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_web);
        webView = findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.ele.me");
    }
}