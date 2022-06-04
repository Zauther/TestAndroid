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
    int tag;
    long valInstance;

    public QJSValue(long valInstance) {
        this.valInstance = valInstance;

    }

    public long getValInstance(){
        return valInstance;
    }

    public void release(QJSContext context) {
        if (valInstance != 0 && context != null && context.getInstance() != 0) {
            try {
                QuickJSJNI.nativeReleaseJSValue(context.getInstance(), valInstance);
            } catch (Throwable e) {
                Log.e("QuickJS", e.toString());
            }

        }
    }
}
