package io.github.zauther.hive.hybrid.jsbridge;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class JSAPI implements Serializable {
    public String method;
    public String params;

    public JSAPI(String method, String params) {
        this.method = method;
        this.params = params;
    }

    @Nullable
    public static JSAPI parse(String jsapi) {
        try {
            if (TextUtils.isEmpty(jsapi) || !jsapi.startsWith(HybridBridge.JS_API_PREFIX)) {
                return null;
            }
            JSONObject object = JSONObject.parseObject(jsapi.substring(12));
            if (object.containsKey("method") && object.containsKey("params")) {
                return new JSAPI(object.getString("method"), object.getString("params"));
            }
            return null;
        } catch (Throwable e) {
            return null;
        }

    }
}
