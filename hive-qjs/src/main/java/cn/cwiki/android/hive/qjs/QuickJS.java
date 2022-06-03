package cn.cwiki.android.hive.qjs;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;

import cn.cwiki.android.hive.qjs.jni.QJSRuntime;
import cn.cwiki.android.hive.qjs.jni.QuickJSJNI;


public class QuickJS {

    public static void init() {
    }



    @Nullable
    public static QJSRuntime createJSRuntime() {
        long instance = QuickJSJNI.nativeCreateQJSRuntime();
        if (instance == 0) {
            Log.w("QuickJS", "nativeCreateQJSRuntime Error");
            return null;
        }
        return new QJSRuntime(instance);


//        instance = QuickJSJNI.nativeNewQJSContext(instance);
//        if (instance == 0) {
//            Log.w("QuickJS", "nativeNewQJSContext Error");
//            return null;
//        }
//
//        instance =QuickJSJNI.nativeEval(instance,"i=1+1;++i;a=0;++a;1+1+10","",0);
//        if (instance == 0) {
//            Log.w("QuickJS", "nativeEval Error");
//            return null;
//        }
//        Log.i("QuickJS",""+ QuickJSJNI.nativeGetInt(instance));
//        return new QJSRuntime(instance);
    }

//    public static int run(QJSRuntime runtime, String script) {
//        if (runtime != null && runtime.getInstance() != 0) {
//            return QuickJSJNI.nativeEvalExpr(runtime.getInstance(), script);
//        }
//        return -1;
//    }


    public static String getVersion() {

        return "";
    }


    public static List<String> getSupportGlobalFunc() {
        return null;
    }

    public static String eval(String script) {
        return "";
    }
}
