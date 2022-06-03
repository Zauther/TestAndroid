package cn.cwiki.android.hive.qjs.jni;

import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Description:
 *
 * @author zauther
 * @data 21-4-3
 */
public class QJSRuntime {
    private long instance;


    public static QJSRuntime newQJSRuntime(){
        long instance = QuickJSJNI.nativeCreateQJSRuntime();
        if (instance == 0) {
            Log.w("QuickJS", "nativeCreateQJSRuntime Error");
            return null;
        }
        return new QJSRuntime(instance);
    }

    public QJSRuntime(long instance) {
        this.instance = instance;
    }

    public void setMemoryLimit(long memoryLimit) {
        if (instance == 0) {
            return;
        }
        if (memoryLimit < 0) {
            memoryLimit = 0;
        }
        QuickJSJNI.nativeSetMemoryLimit(instance, memoryLimit);

    }

    public void setMaxStackSize(long stackSize) {
        if (instance == 0) {
            return;
        }
        if (stackSize < 0) {
            stackSize = 0;
            QuickJSJNI.nativeSetMaxStackSize(instance, stackSize);
        }
    }


    @Nullable
    public QJSContext newQJSContext() {
        if (instance == 0) {
            return null;
        }
        long ctxInstance = QuickJSJNI.nativeNewQJSContext(instance);
        if(ctxInstance == 0){
            return null;
        }
        return new QJSContext(ctxInstance);
    }

    public int run(String script) {
        if (instance != 0) {
            return QuickJSJNI.nativeEvalExpr(instance, script);
        }
        return -1;
    }

    public long getInstance() {
        return this.instance;
    }

    public void release(){
        if(instance !=0){
            QuickJSJNI.nativeReleaseQJSRuntime(instance);
        }
    }
}
