package io.github.zauther.test.web;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.tencent.smtt.sdk.WebSettings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.github.zauther.android.hive.HiveHybrid;
import io.github.zauther.android.hive.web.x5.HiveX5WebChromeClient;
import io.github.zauther.android.hive.web.x5.HiveX5WebView;
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
        //????????????????????????????????????
        settings.setUseWideViewPort(true); //????????????????????????webview?????????
        settings.setLoadWithOverviewMode(true); // ????????????????????????
        //????????????
        settings.setSupportZoom(true); //????????????????????????true??????????????????????????????
        settings.setBuiltInZoomControls(true); //????????????????????????????????????false?????????WebView????????????
        settings.setDisplayZoomControls(false); //???????????????????????????

        //??????????????????
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //??????webview?????????
        settings.setAllowFileAccess(true); //????????????????????????
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //????????????JS???????????????
        settings.setLoadsImagesAutomatically(true); //????????????????????????
        settings.setDefaultTextEncodingName("utf-8");//??????????????????
        ZWebView.setWebContentsDebuggingEnabled(true);
        settings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebChromeClient(new HiveX5WebChromeClient(webView));

        webView.loadUrl("file:///android_asset/test.html");
//        webView.evaluateJavascript(getJS(this,"hivejsbridge.js"));
//        webView.evaluateJavascript(getJS(this,"test.js"));
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
    public static String getJS(Context context, String fileName) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            inputStream = context.getAssets().open(fileName);
            outputStream = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[2048];
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return new String(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}