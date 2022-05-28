package io.github.zauther.android.hive;

import io.github.zauther.android.hive.hybrid.inner.plugins.SystemInfoPlugin;
import io.github.zauther.android.hive.hybrid.jsbridge.HybridBridge;

public class HiveHybrid {
    public static void init(){
        HybridBridge.registerPlugin("SystemInfo", SystemInfoPlugin.class);
    }
}
