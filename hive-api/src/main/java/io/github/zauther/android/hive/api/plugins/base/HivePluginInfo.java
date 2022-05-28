package io.github.zauther.android.hive.api.plugins.base;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 * "aliasMethod":"",
 * "originMethod":"",
 * "pluginClass":Object
 * }
 */
public class HivePluginInfo {
    Map<String, OriginMethod> methodMap = new HashMap<>();
    String namespace;

    public HivePluginInfo(String namespace) {
        this.namespace = namespace;
    }

    public void addMethod(String aliasMethod, String originMethod, Class<? extends IHivePlugin> pluginClz) {
        if (TextUtils.isEmpty(originMethod) || pluginClz == null) {
            return;
        }
        if (TextUtils.isEmpty(aliasMethod)) {
            aliasMethod = originMethod;
        }
        methodMap.put(aliasMethod, new OriginMethod(originMethod, pluginClz));
    }

    public OriginMethod getOriginMethodByMethod(String aliasMethod) {
        if (TextUtils.isEmpty(aliasMethod)) {
            return null;
        }
        return methodMap.get(aliasMethod);
    }

//    public String getOriginMethodNameByMethod(String aliasMethod) {
//        if (TextUtils.isEmpty(aliasMethod)) {
//            return null;
//        }
//        OriginMethod originMethod = methodMap.get(aliasMethod);
//        if (originMethod == null) {
//            return null;
//        }
//        return originMethod.originMethodName;
//    }

    public static class OriginMethod {
        public String originMethodName;
        public Class<? extends IHivePlugin> pluginClz;

        public OriginMethod() {
        }

        public OriginMethod(String originMethodName, Class<? extends IHivePlugin> pluginClz) {
            this.originMethodName = originMethodName;
            this.pluginClz = pluginClz;
        }
    }
}
