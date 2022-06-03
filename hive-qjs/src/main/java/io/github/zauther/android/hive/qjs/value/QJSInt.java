package io.github.zauther.android.hive.qjs.value;

import io.github.zauther.android.hive.qjs.jni.QuickJSJNI;

/**
 * Description:
 *
 * @author zauther
 * @data 21-9-12
 */
public class QJSInt extends QJSValue {

    int value;
    public QJSInt(long instance){
        super(instance);
        value = QuickJSJNI.nativeGetInt(instance);
    }

    public int getValue() {
        return value;
    }
}
