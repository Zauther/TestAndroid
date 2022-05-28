package io.github.zauther.android.hive.api.base;

import android.os.Build;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

import io.github.zauther.android.hive.api.plugins.annotations.BindingType;
import io.github.zauther.android.hive.api.plugins.annotations.HiveBinding;
import io.github.zauther.android.hive.api.plugins.annotations.HiveMethod;
import io.github.zauther.android.hive.api.plugins.annotations.HiveParam;
import io.github.zauther.android.hive.api.plugins.annotations.HivePlugin;
import io.github.zauther.android.hive.api.plugins.annotations.HiveThreadType;
import io.github.zauther.android.hive.api.plugins.base.IHiveCallback;
import io.github.zauther.android.hive.api.plugins.base.IHivePlugin;

@HivePlugin(namespace = "SystemInfo")
public class SystemInfoPlugin implements IHivePlugin {

    @HiveMethod(value = "getSystemInfo", runOn = HiveThreadType.current, returnOn = HiveThreadType.current)
    public void getSystemInfo(@HiveBinding(BindingType.callback) IHiveCallback<JSONObject> callback) {
        JSONObject jsonObject = new JSONObject();
        if (callback != null) {
            jsonObject.put("osVersion", Build.VERSION.RELEASE);
            callback.send(jsonObject);
        }
    }

    @HiveMethod(value = "getSystemInfoByType", runOn = HiveThreadType.current, returnOn = HiveThreadType.ui)
    public void getSystemInfo(@HiveParam("type") String requireType, @HiveBinding(BindingType.callback) IHiveCallback<JSONObject> callback) {
        JSONObject jsonObject = new JSONObject();
        Log.d("====getSystemInfo", Thread.currentThread().getName());
        if (callback != null) {
            if("osVersion".equals(requireType)){
                jsonObject.put("osVersion", Build.VERSION.RELEASE);
            }
            callback.send(jsonObject);
        }
    }
    @HiveMethod(value = "getOS", runOn = HiveThreadType.current, returnOn = HiveThreadType.ui)
    public void getOS(@HiveBinding(BindingType.callback) IHiveCallback<Map<String,Object>> callback) {
        JSONObject jsonObject = new JSONObject();
        Log.d("====getSystemInfo", Thread.currentThread().getName());
        if (callback != null) {
            jsonObject.put("os", "Android");
            callback.send(jsonObject);
        }
    }
}
