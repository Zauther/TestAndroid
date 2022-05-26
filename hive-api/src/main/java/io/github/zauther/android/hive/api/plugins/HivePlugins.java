package io.github.zauther.android.hive.api.plugins;

import android.content.Context;
import android.text.TextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.zauther.android.hive.api.plugins.annotations.HiveMethod;
import io.github.zauther.android.hive.api.plugins.annotations.HiveParam;
import io.github.zauther.android.hive.api.plugins.annotations.HivePlugin;
import io.github.zauther.android.hive.api.plugins.base.HivePluginInfo;
import io.github.zauther.android.hive.api.plugins.base.IHiveCallback;
import io.github.zauther.android.hive.api.plugins.base.IHivePlugin;

public class HivePlugins {
    private static final Map<String, HivePluginInfo> sPluginsMap = new ConcurrentHashMap<>();

    public static void register(Class<? extends IHivePlugin> pluginClz) {
        if (pluginClz == null) {
            return;
        }

        HivePlugin hivePlugin = pluginClz.getAnnotation(HivePlugin.class);
        String namespace = (hivePlugin == null || TextUtils.isEmpty(hivePlugin.namespace())
                ? pluginClz.getSimpleName()
                : hivePlugin.namespace());
        register(namespace, pluginClz);

    }

    public static void register(String namespace, Class<? extends IHivePlugin> pluginClz) {
        if (pluginClz != null && !TextUtils.isEmpty(namespace)) {
            Method[] methods = pluginClz.getMethods();
            if (methods.length != 0) {
                HivePluginInfo hivePluginInfo = sPluginsMap.get(namespace);
                if (hivePluginInfo == null) {
                    hivePluginInfo = new HivePluginInfo(namespace);
                }
                boolean containPluginMethodAnnotation = false;
                for (Method m : methods) {
                    HiveMethod hiveMethod = m.getAnnotation(HiveMethod.class);
                    if (hiveMethod != null) {
                        containPluginMethodAnnotation = true;
                        String aliasMethodName = hiveMethod.name();
                        if (TextUtils.isEmpty(aliasMethodName)) {
                            aliasMethodName = m.getName();
                        }

                        String originMethodName = m.getName();
                        hivePluginInfo.addMethod(aliasMethodName, originMethodName, pluginClz);
                    }
                }

                if (containPluginMethodAnnotation) {
                    sPluginsMap.put(namespace, hivePluginInfo);
                }
            }
        }
    }

    public static HivePluginInfo.OriginMethod getHiveOriginMethod(String namespace, String method) {
        if (TextUtils.isEmpty(namespace) || TextUtils.isEmpty(method)) {
            return null;
        }

        HivePluginInfo hivePluginInfo = sPluginsMap.get(namespace);
        if (hivePluginInfo == null) {
            return null;
        }
        return hivePluginInfo.getOriginMethodByMethod(method);
    }

    public static boolean exec(Context context, String namespace, String method, Object param, IHiveCallback<Object> callback) {
        if (TextUtils.isEmpty(namespace) || TextUtils.isEmpty(method)) {
            return false;
        }
        HivePluginInfo.OriginMethod originMethod = getHiveOriginMethod(namespace, method);
        if (originMethod == null || originMethod.pluginClz == null || TextUtils.isEmpty(originMethod.originMethodName)) {
            return false;
        }
        Method targetMethod = null;
        Method[] methods = originMethod.pluginClz.getMethods();
        for (Method m : methods) {
            if (m.getName().equals(originMethod.originMethodName)) {
                targetMethod = m;
                break;
            }
        }

        if (targetMethod == null) {
            return false;
        }
        HiveMethod hiveMethod = targetMethod.getAnnotation(HiveMethod.class);
        if (hiveMethod == null) {
            return false;
        }

        try {
            IHivePlugin hivePluginInstance = originMethod.pluginClz.newInstance();
            Class<?>[] parTypes = targetMethod.getParameterTypes();
            if (parTypes.length == 0) {
                Object invoke = targetMethod.invoke(hivePluginInstance);
                if (callback != null) {
                    callback.send(invoke);
                }
                return true;
            }
            Annotation[][] annos = targetMethod.getParameterAnnotations();
            Object[] args = new Object[parTypes.length];

            for (int i = 0; i < parTypes.length; i++) {
                if (Context.class.isAssignableFrom(parTypes[i])) {
                    args[i] = context;
                    continue;
                } else if (IHiveCallback.class.isAssignableFrom(parTypes[i])) {
                    args[i] = callback;
                    continue;
                }

                for (Annotation an : annos[i]) {
                    if (an instanceof HiveParam) {
                        String name = ((HiveParam) an).value();
                        if (TextUtils.isEmpty(name)) {
                            name = parTypes[i].getName();
                        }

                        if (Integer.TYPE.isAssignableFrom(parTypes[i])
                                || int.class.isAssignableFrom(parTypes[i])
                                || int.class.equals(parTypes[i])
                                || Integer.class.equals(parTypes[i])) {

                            try {
                                if (args[i] == null) {
                                    args[i] = ((HiveParam) an).intDefault();
                                }
                            } catch (Throwable e) {
                                args[i] = ((HiveParam) an).intDefault();
                            }

                        } else if (Float.TYPE.isAssignableFrom(parTypes[i])
                                || float.class.isAssignableFrom(parTypes[i])
                                || Float.class.equals(parTypes[i])
                                || float.class.isAssignableFrom(parTypes[i])) {
                            try {
                                if (args[i] == null) {
                                    args[i] = ((HiveParam) an).floatDefault();
                                }
                            } catch (Throwable e) {
                                args[i] = ((HiveParam) an).floatDefault();
                            }
                        } else if (Boolean.TYPE.isAssignableFrom(parTypes[i])
                                || boolean.class.isAssignableFrom(parTypes[i])
                                || Boolean.TYPE.equals(parTypes[i])
                                || boolean.class.equals(parTypes[i])) {
                            try {
                                if (args[i] == null) {
                                    args[i] = ((HiveParam) an).booleanDefault();
                                }
                            } catch (Throwable e) {
                                args[i] = ((HiveParam) an).booleanDefault();
                            }
                        } else if (Double.TYPE.isAssignableFrom(parTypes[i])
                                || double.class.isAssignableFrom(parTypes[i])
                                || Double.TYPE.equals(parTypes[i])
                                || double.class.equals(parTypes[i])) {
                            try {
                                if (args[i] == null) {
                                    args[i] = ((HiveParam) an).doubleDefault();
                                }
                            } catch (Throwable e) {
                                args[i] = ((HiveParam) an).doubleDefault();
                            }
                        } else if (String.class.isAssignableFrom(parTypes[i])
                                || String.class.equals(parTypes[i])) {
                            try {
                                if (args[i] == null) {
                                    args[i] = ((HiveParam) an).stringDefault();
                                }
                            } catch (Throwable e) {
                                args[i] = ((HiveParam) an).stringDefault();
                            }
                        } else if (Long.TYPE.isAssignableFrom(parTypes[i])
                                || long.class.isAssignableFrom(parTypes[i])
                                || Long.class.equals(parTypes[i])
                                || long.class.equals(parTypes[i])) {
                            try {
                                if (args[i] == null) {
                                    args[i] = ((HiveParam) an).longDefault();
                                }
                            } catch (Throwable e) {
                                args[i] = ((HiveParam) an).longDefault();
                            }
                        }
                    }
                }
            }
            targetMethod.invoke(hivePluginInstance, args);
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }
}
