package io.github.zauther.test.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
            }
        }));

    }
}