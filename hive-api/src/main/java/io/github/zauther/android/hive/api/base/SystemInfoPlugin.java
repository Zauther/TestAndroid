package io.github.zauther.android.hive.api.base;

import android.os.Build;

import com.alibaba.fastjson.JSONObject;

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

    @HiveMethod(value = "getSystemInfo", runOn = HiveThreadType.immediate, returnOn = HiveThreadType.immediate)
    public void getSystemInfo(@HiveBinding(BindingType.callback) IHiveCallback<JSONObject> callback) {
        JSONObject jsonObject = new JSONObject();
        if (callback != null) {
            jsonObject.put("osVersion", Build.VERSION.RELEASE);
            callback.send(jsonObject);
        }
    }

    @HiveMethod(value = "getSystemInfoByType", runOn = HiveThreadType.immediate, returnOn = HiveThreadType.immediate)
    public void getSystemInfo(@HiveParam("type") String requireType, @HiveBinding(BindingType.callback) IHiveCallback<JSONObject> callback) {
        JSONObject jsonObject = new JSONObject();
        if (callback != null) {
            if("osVersion".equals(requireType)){
                jsonObject.put("osVersion", Build.VERSION.RELEASE);
            }
            callback.send(jsonObject);
        }
    }
}
