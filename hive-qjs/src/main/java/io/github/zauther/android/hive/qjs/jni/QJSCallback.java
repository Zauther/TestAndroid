package io.github.zauther.android.hive.qjs.jni;

import io.github.zauther.android.hive.qjs.value.QJSValue;

public interface QJSCallback {
    QJSValue call(QJSContext qjsContext, QJSValue ...params);
}
