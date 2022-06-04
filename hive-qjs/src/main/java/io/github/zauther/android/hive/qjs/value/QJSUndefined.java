package io.github.zauther.android.hive.qjs.value;

import io.github.zauther.android.hive.qjs.jni.QuickJSJNI;

public class QJSUndefined extends QJSValue{
    public QJSUndefined(long valInstance) {
        super(valInstance);
    }

    public static QJSUndefined newQJSUndefined(){
        return new QJSUndefined(QuickJSJNI.nativeQJSUndefined());
    }
}
