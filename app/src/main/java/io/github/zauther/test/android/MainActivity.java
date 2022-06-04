package io.github.zauther.test.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ResourceUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.CronetProvider;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


import io.github.zauther.android.hive.api.base.SystemInfoPlugin;
import io.github.zauther.android.hive.api.plugins.HivePlugins;
import io.github.zauther.android.hive.api.plugins.base.IHiveCallback;
import io.github.zauther.android.hive.qjs.jni.QJSCallback;
import io.github.zauther.android.hive.qjs.jni.QJSContext;
import io.github.zauther.android.hive.qjs.jni.QJSRuntime;
import io.github.zauther.android.hive.qjs.jni.QuickJSJNI;
import io.github.zauther.android.hive.qjs.module.CronetQJSModule;
import io.github.zauther.android.hive.qjs.value.QJSFunction;
import io.github.zauther.android.hive.qjs.value.QJSInt;
import io.github.zauther.android.hive.qjs.value.QJSValue;
import io.github.zauther.test.android.func.WX;
import io.github.zauther.test.android.list.FuncListAdapter;
import io.github.zauther.test.android.list.FuncListItem;
import io.github.zauther.test.android.testdex.DexTest;
import io.github.zauther.test.web.WebActivity;

public class MainActivity extends AppCompatActivity {

    RecyclerView funcList;
    private static final Map<Class<?>, Object> sProxyMap = new ConcurrentHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HivePlugins.register(SystemInfoPlugin.class);
        funcList = findViewById(R.id.func_list);
        funcList.setLayoutManager(new LinearLayoutManager(this));
        funcList.setAdapter(new FuncListAdapter(this, new ArrayList<FuncListItem>() {
            {
                add(new FuncListItem("Dex", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File dest = DexTest.copyAsset(MainActivity.this, "libs/nativelibdex.aar", false);
                        if (dest != null) {
                            DexTest.loadDexClass(MainActivity.this, dest);
                        }
                    }
                }));

                add(new FuncListItem("Dex-Aar Load", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Class libClazz = Class.forName("io.github.zauther.test.nativelib.NativeLib");
                            Constructor<?> localConstructor = libClazz.getConstructor();
//
                            Object obj = localConstructor.newInstance();
                            Method mMethodWrite = libClazz.getMethod("stringFromJava");
                            mMethodWrite.setAccessible(true);
                            String str = (String) mMethodWrite.invoke(obj);
                            Toast.makeText(MainActivity.this, "result is " + str, Toast.LENGTH_SHORT).show();
                        } catch (Throwable e) {
                            Toast.makeText(MainActivity.this, "result is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));

                add(new FuncListItem("Test Bug", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object a = sProxyMap.get(MainActivity.class);
                        Toast.makeText(MainActivity.this, "" + a, Toast.LENGTH_SHORT).show();
                    }
                }));

                add(new FuncListItem("WX", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        SendAuth.Req req = new SendAuth.Req();
//                        req.scope = "snsapi_userinfo";
//                        req.state = "wechat_sdk_demo_test";
                        WXTextObject textObj = new WXTextObject();
                        textObj.text = "text";
                        WXMediaMessage msg = new WXMediaMessage();
                        msg.mediaObject = textObj;
                        // msg.title = "Will be ignored";
                        msg.description = "text";

                        SendMessageToWX.Req req = new SendMessageToWX.Req();
//                        req.transaction = buildTransaction("text");
                        req.message = msg;
                        req.scene = SendMessageToWX.Req.WXSceneSession;

//                        api.sendReq(req);
                        WX.req(MainActivity.this, req);
                    }
                }));
                add(new FuncListItem("Web", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, WebActivity.class));
                    }
                }));

                add(new FuncListItem("plugin", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HivePlugins.register(SystemInfoPlugin.class);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HivePlugins.execOn(MainActivity.this, "SystemInfo", "getSystemInfoByType", new HashMap<String,Object>(){{
//            put("type","osVersion");
                                    put("type","osVersion");
                                }}, new IHiveCallback<JSONObject>() {
                                    @Override
                                    public void send(JSONObject jsonObject) {
                                        System.out.println(jsonObject.toJSONString());
                                        System.out.println(Thread.currentThread().getName());
                                        Log.d("====", jsonObject.toJSONString());
                                        Log.d("====", Thread.currentThread().getName());
                                        Toast.makeText(MainActivity.this,jsonObject.toJSONString(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        },"test").start();

                    }
                }));

                add(new FuncListItem("QJS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Log.i("QJS",""+new NativeLib().stringFromJNI());
                        QJSRuntime runtime =QJSRuntime.newQJSRuntime();
                        CronetQJSModule.init();
                        if(runtime!=null){
                            QJSContext qjsContext = runtime.newQJSContext();
                            if(qjsContext!=null){
                                QJSInt a= qjsContext.eval("console.log(\"Hello World\");console.log(\"Hello \\n World\")","",QJSInt.class);
                                Log.i("QuickJS",""+ a.getValue());

                                long undefined =QuickJSJNI.nativeQJSUndefined();

                                QJSInt qjsInt= new QJSInt(qjsContext.getInstance());
                                QJSFunction function = new QJSFunction(qjsContext.getInstance(),1,qjsInt);

                                qjsContext.createJSFunction(new QJSCallback() {
                                    @Override
                                    public QJSValue call(QJSContext qjsContext, QJSValue... params) {
                                        return null;
                                    }
                                });

                                File file = MainActivity.this.getCacheDir();
                                File dest = new File(file,"cronet-qjs-module.so");
                                ResourceUtils.copyFileFromAssets("libcronet-qjs-module.so",dest.getAbsolutePath());
                                String js= dest.getAbsolutePath();

                                js = "import { fib } from \""+dest.getAbsolutePath()+"\";\n" +
                                        "\n" +
                                        "fib(10);\n" +
                                        "console.log(\"fib(10)=\", fib(10));";
//                                js = "console.log(\"Hello == World\");";
                                qjsContext.eval(js,"",QJSInt.class);

                                a.release(qjsContext);
                            }
                        }
                    }
                }));
                add(new FuncListItem("Cronet", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int BYTE_BUFFER_CAPACITY_BYTES = 64 * 1024;
                        ByteArrayOutputStream bytesReceived = new ByteArrayOutputStream();
                        WritableByteChannel receiveChannel = Channels.newChannel(bytesReceived);

                        CronetEngine.Builder myBuilder = new CronetEngine.Builder(MainActivity.this);
                        CronetEngine cronetEngine = myBuilder.build();
                        List<CronetProvider> enabledProviders =
                                CronetProvider.getAllProviders(MainActivity.this)
                                        .stream()
                                        .filter(CronetProvider::isEnabled)
                                        .collect(Collectors.toList());
                        Executor executor = Executors.newSingleThreadExecutor();
                        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                                "https://www.example.com", new UrlRequest.Callback() {
                                    @Override
                                    public void onRedirectReceived(UrlRequest request, UrlResponseInfo info, String newLocationUrl) throws Exception {
                                        Log.i("Cronet",info.toString());
                                        request.followRedirect();
                                    }

                                    @Override
                                    public void onResponseStarted(UrlRequest request, UrlResponseInfo info) throws Exception {
                                        Log.i("Cronet",info.toString());
                                        request.read(ByteBuffer.allocateDirect(BYTE_BUFFER_CAPACITY_BYTES));
                                    }

                                    @Override
                                    public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) throws Exception {
                                        byteBuffer.flip();

                                        try {
                                            receiveChannel.write(byteBuffer);
                                        } catch (IOException e) {
                                            android.util.Log.i("Cronet", "IOException during ByteBuffer read. Details: ", e);
                                        }
                                        // Reset the buffer to prepare it for the next read
                                        byteBuffer.clear();

                                        // Continue reading the request
                                        request.read(byteBuffer);;
                                    }

                                    @Override
                                    public void onSucceeded(UrlRequest request, UrlResponseInfo info) {

                                        byte[] bodyBytes = bytesReceived.toByteArray();
                                        Log.i("Cronet",new String(bodyBytes));
                                    }

                                    @Override
                                    public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
                                        Log.e("Cronet",info.toString());
                                    }
                                }, executor);

                        UrlRequest request = requestBuilder.build();
                        request.start();
                    }
                }));
            }
        }));

    }
}