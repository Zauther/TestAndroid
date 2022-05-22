package io.github.zauther.hive;

import io.github.zauther.hive.hybrid.inner.plugins.SystemInfoPlugin;
import io.github.zauther.hive.hybrid.jsbridge.HybridBridge;

public class HiveHybrid {
    public static void init(){
        HybridBridge.registerPlugin("SystemInfo", SystemInfoPlugin.class);
    }
}
