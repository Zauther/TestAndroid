package io.github.zauther.android.hive.hybrid.jsbridge.plugin;

import com.alibaba.fastjson.JSONObject;

public interface ICallback {
    void sendResponse(JSONObject json);
    void onSuccess(JSONObject json);
    void onFailed(JSONObject json);
}
