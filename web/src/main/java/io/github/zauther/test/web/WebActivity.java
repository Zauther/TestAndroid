package io.github.zauther.test.web;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebSettings;

import io.github.zauther.hive.HiveHybrid;
import io.github.zauther.hive.web.x5.HiveX5WebChromeClient;
import io.github.zauther.hive.web.x5.HiveX5WebView;
import io.github.zauther.test.web.core.ZWebView;

public class WebActivity extends AppCompatActivity {


    String testjs = "(function(){\n" +
            "//console.log(\"hivejsapi://{'method':'hello','params':{'test':'world'}}\");\n" +
            "//console.log(\"hivejsapi://hive:12345534434/method?params={'test':'world'}\");\n" +
            "const token_map = new Map();\n" +
            "function call(namespace,method,params){\n" +
            "    var timestamp=new Date().getTime();\n" +
            "    var token = namespace+\"_\"+method+\"_\"+timestamp;\n" +
            "    token_map.set(token,callback);\n" +
            "    console.log(\"hivejsapi://\"+namespace+\":\"+token+\"/\"+method+\"?\"+params);\n" +
            "}\n" +
            "\n" +
            "function callback(token,success,failed){\n" +
            "    console.log(token);\n" +
            "}\n" +
            "//console.log(\"hivejsapi://SystemInfo:token_123455dsdssss34434/getOS?params={'test':'world'}\")\n" +
            "call('SystemInfo','getOS','paramstest');\n" +
            "})();";

    HiveX5WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_web);
        HiveHybrid.init();

        webView = findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        settings.setPluginsEnabled(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        ZWebView.setWebContentsDebuggingEnabled(true);
        settings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebChromeClient(new HiveX5WebChromeClient(webView));

        webView.loadUrl("https://thwj.tejiayun.com");
        webView.evaluateJavascript(testjs);
        webView.evaluateJavascript("");
    }


    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}