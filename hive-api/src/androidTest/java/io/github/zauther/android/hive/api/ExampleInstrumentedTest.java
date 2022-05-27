package io.github.zauther.android.hive.api;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.Toast;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.alibaba.fastjson.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import io.github.zauther.android.hive.api.base.SystemInfoPlugin;
import io.github.zauther.android.hive.api.plugins.HivePlugins;
import io.github.zauther.android.hive.api.plugins.base.IHiveCallback;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("io.github.zauther.android.hiveapi.test", appContext.getPackageName());
        HivePlugins.register(SystemInfoPlugin.class);
        HivePlugins.exec(appContext, "SystemInfo", "getSystemInfoByType", new HashMap<String,Object>(){{
//            put("type","osVersion");
            put("type","osVersion");
        }}, new IHiveCallback<JSONObject>() {
            @Override
            public void send(JSONObject jsonObject) {
                System.out.println(jsonObject.toJSONString());
                System.out.println(Thread.currentThread().getName());
            }
        });
    }
}