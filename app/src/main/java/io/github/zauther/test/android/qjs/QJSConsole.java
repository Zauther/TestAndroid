package io.github.zauther.test.android.qjs;


import android.util.Log;

import com.tencent.smtt.sdk.JsValue;

import java.lang.reflect.Method;

import io.github.zauther.android.hive.qjs.jni.QJSContext;
import io.github.zauther.android.hive.qjs.jni.QuickJSJNI;
import io.github.zauther.android.hive.qjs.value.QJSString;
import io.github.zauther.android.hive.qjs.value.QJSUndefined;
import io.github.zauther.android.hive.qjs.value.QJSValue;

public class QJSConsole {
    long ctxInstance;
    long globalObjInstance;
    long consoleInstance;
    long functionInstance;
    public QJSConsole(QJSContext qjsContext){
        ctxInstance = qjsContext.getInstance();
        globalObjInstance = QuickJSJNI.nativeGetGlobalObject(ctxInstance);
        consoleInstance = QuickJSJNI.nativeNewJSObject(ctxInstance);

        Method method = null;

        try {
            method =  getClass().getMethod("error",Long.class,Long.class,Integer.class,long[].class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
//        functionInstance =QuickJSJNI.nativeNewFunction(ctxInstance,this,method,"error",1);
//        QuickJSJNI.setProperty(ctxInstance,consoleInstance,"error",functionInstance);
//        QuickJSJNI.setProperty(ctxInstance,globalObjInstance,"console",consoleInstance);
    }

    public long error(long ctxInstance, long qjs_this_val, int argc, long[] argv){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<argc;i++){
            sb.append(new QJSString(ctxInstance,argv[i]).getJSString());
        }
        Log.i("QJS",sb.toString());
        return QJSUndefined.newQJSUndefined().getValInstance();
    }

}
