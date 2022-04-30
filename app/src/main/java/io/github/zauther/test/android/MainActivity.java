package io.github.zauther.test.android;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.zauther.test.android.list.FuncListAdapter;
import io.github.zauther.test.android.list.FuncListItem;
import io.github.zauther.test.android.testdex.DexTest;

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
            }
        }));

    }
}