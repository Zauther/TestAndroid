package io.github.zauther.test.android.testdex;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;

public class DexTest {
    public static File copyAsset(Context context, String assetPath, boolean force) {
        if (TextUtils.isEmpty(assetPath)) {
            return null;
        }
        File dest = new File(context.getCacheDir().getAbsolutePath(), assetPath);
        if (!dest.exists() || force) {
            ResourceUtils.copyFileFromAssets(assetPath, dest.getAbsolutePath());
        }
        try {
            ZipUtils.unzipFile(dest,new File(context.getCacheDir().getAbsolutePath(),"lib"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return dest;
    }

    public static void loadDexClass(Context context, File dexFile) {
        if (dexFile == null || !dexFile.exists() || !dexFile.isFile()) {
            return;
        }

        File cacheFile = context.getCacheDir();
        DexClassLoader dexClassLoader = new DexClassLoader(dexFile.getAbsolutePath(), cacheFile.getAbsolutePath(), new File(context.getCacheDir().getAbsolutePath(),"lib/jni/arm64-v8a").getAbsolutePath(), context.getClass().getClassLoader());
        try {
            Class libClazz = dexClassLoader.loadClass("io.github.zauther.test.nativelib.NativeLib");
            Constructor<?> localConstructor = libClazz.getConstructor();
//
            Object obj = localConstructor.newInstance();
            Method mMethodWrite = libClazz.getMethod("stringFromJava");
            mMethodWrite.setAccessible(true);
            String str = (String) mMethodWrite.invoke(obj);
            Toast.makeText(context, "result is " + str, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "result is error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

//        try {
//            Class libClazz = dexClassLoader.loadClass("io.github.zauther.test.testdex.TestDexLoad");
//            Constructor<?> localConstructor = libClazz.getConstructor();
//            Object obj = localConstructor.newInstance();
//            Method mMethodWrite = libClazz.getMethod("getString");
//            mMethodWrite.setAccessible(true);
//            String str = (String) mMethodWrite.invoke(obj);
//            Toast.makeText(context, "result is " + str, Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Toast.makeText(context, "result is error " + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }

    }
}
