package io.github.zauther.android.hive.hybrid.jsbridge;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JSResult {
    public boolean success;
    public JSONObject successData = new JSONObject();
    public JSONObject failedData = new JSONObject();


}
