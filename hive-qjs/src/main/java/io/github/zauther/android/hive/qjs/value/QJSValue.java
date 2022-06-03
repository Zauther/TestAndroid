package io.github.zauther.android.hive.qjs.value;

import android.util.Log;

import io.github.zauther.android.hive.qjs.jni.QJSContext;
import io.github.zauther.android.hive.qjs.jni.QuickJSJNI;

/**
 * Description:
 *
 * @author zauther
 * @data 21-4-3
 */
public class QJSValue {
    long tag;
    long instance;

    public QJSValue(long instance) {
        this.instance = instance;
    }


    public void release(QJSContext context) {
        if (instance != 0 && context != null && context.getInstance() != 0) {
            try {
                QuickJSJNI.nativeReleaseJSValue(context.getInstance(), instance);
            } catch (Throwable e) {
                Log.e("QuickJS", e.toString());
            }

        }
    }
}
