package cn.cwiki.android.hive.qjs.module;

import android.util.Log;

import androidx.annotation.Keep;

/**
 * Description:
 *
 * @author zauther
 * @data 2021/9/13
 */
@Keep
public class Console {


    private static void log(String log) {
        Log.i("Console", log);
    }


    public interface OnConsoleLog {
        void log();
    }
}
