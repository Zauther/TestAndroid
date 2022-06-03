package cn.cwiki.android.hive.qjs.jni;


import cn.cwiki.android.hive.qjs.value.QJSValue;

public interface QJSCallback {
    QJSValue call(QJSContext qjsContext, QJSValue ...params);
}
