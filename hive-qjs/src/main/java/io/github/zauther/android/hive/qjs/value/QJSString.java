package io.github.zauther.android.hive.qjs.value;

import io.github.zauther.android.hive.qjs.jni.QuickJSJNI;

/**
 * Description:
 *
 * @author zauther
 * @data 21-9-12
 */
public class QJSString extends QJSValue {
    long ctxInstance;

    public QJSString(long ctxInstance, long valInstance) {
        super(valInstance);
        tag = QJSTag.JS_TAG_STRING;
        this.ctxInstance = ctxInstance;
    }

    public String getJSString() {
        return QuickJSJNI.nativeGetString(ctxInstance, valInstance);
    }
}
