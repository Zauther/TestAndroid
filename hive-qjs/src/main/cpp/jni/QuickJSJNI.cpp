//
// Created by zauther on 21-4-3.
//

#include "QuickJSJNI.h"
#include <jni.h>
#include <cstddef>
#include "QuickJS.h"
#include "qjslog.h"
#include <sstream>

JavaVM *g_VM;

static void JAVA_Console(const char *_str){
    JNIEnv *env;

    //获取当前native线程是否有没有被附加到jvm环境中
    if(g_VM== nullptr){
        return;
    }
    bool mNeedDetach;
    int getEnvStat = g_VM->GetEnv((void **) &env,JNI_VERSION_1_6);
//    if (getEnvStat == JNI_EDETACHED) {
//        //如果没有， 主动附加到jvm环境中，获取到env
//        if (g_VM->AttachCurrentThread( &env, NULL) != 0) {
//            return;
//        }
//        mNeedDetach = JNI_TRUE;
//    }
    //通过全局变量g_obj 获取到要回调的类
    jclass clazz = env->FindClass("io/github/zauther/android/hive/qjs/module/Console");

    if (clazz == nullptr) {
//        g_VM->DetachCurrentThread();
        return;
    }
    //获取要回调的方法ID
    jmethodID logID = env->GetStaticMethodID(clazz,
                                             "log", "(Ljava/lang/String;)V");
    if (logID == nullptr) {
        return;
    }
    jstring  str= env->NewStringUTF(_str);
    env->CallStaticVoidMethod(clazz,logID,str);
    //释放当前线程
//    if(mNeedDetach) {
//        g_VM->DetachCurrentThread();
//    }
    env = nullptr;
}

static JSValue js_print(JSContext *ctx, JSValueConst this_val,
                        int argc, JSValueConst *argv)
{
    int i;
    const char *str;
    size_t len;

    for(i = 0; i < argc; i++) {
        if (i != 0)
            putchar(' ');
        str = JS_ToCStringLen(ctx, &len, argv[i]);
        if (!str)
            return JS_EXCEPTION;

//        fwrite(str, 1, len, stdout);
        JAVA_Console(str);
        JS_FreeCString(ctx, str);
    }
    putchar('\n');
    return JS_UNDEFINED;
}


static void addConsole(JSContext *ctx) {
    JSValue global_obj, console;
    global_obj = JS_GetGlobalObject(ctx);

    console = JS_NewObject(ctx);
    JS_SetPropertyStr(ctx, console, "log",
                      JS_NewCFunction(ctx, js_print, "log", 1));
    JS_SetPropertyStr(ctx, global_obj, "console", console);
}




static jlong CreateQJSRuntime(JNIEnv *env, jclass __unused clazz) {
    JSRuntime *runtime = QuickJS::createNewQJSRuntime();
    return reinterpret_cast<long >(runtime);
}

static jlong NewQJSContext(JNIEnv *env, jclass __unused clazz, jlong runtime) {
    auto *_runtime = reinterpret_cast<JSRuntime * >(runtime);

    JSContext * ctx = QuickJS::newQJSContext(_runtime);
    addConsole(ctx);
    return reinterpret_cast<long >(ctx);
}

static void SetMemoryLimit(JNIEnv *env, jclass __unused clazz, jlong runtime, jlong limit) {
    auto *_runtime = reinterpret_cast<JSRuntime * >(runtime);
    QuickJS::setMemoryLimit(_runtime, limit);
}

static void SetMaxStackSize(JNIEnv *env, jclass __unused clazz, jlong runtime, jlong size) {
    auto *_runtime = reinterpret_cast<JSRuntime * >(runtime);
    QuickJS::setMaxStackSize(_runtime, size);
}

static void ReleaseQJSRuntime(JNIEnv *env, jclass __unused clazz, jlong runtime) {
    auto *_runtime = reinterpret_cast<JSRuntime * >(runtime);
    QuickJS::releaseQJSRuntime(_runtime);
}

static void ReleaseQJSContext(JNIEnv *env, jclass __unused clazz, jlong context) {
    auto *_context = reinterpret_cast<JSContext * >(context);
    QuickJS::releaseQJSContext(_context);
}


static jlong Eval(JNIEnv *env, jclass __unused clazz, jlong context, jstring expr, jstring fileName,
                  jint flags) {
//    QuickJS *js = reinterpret_cast<QuickJS * >(instance);
//    const char *nativeString = env->GetStringUTFChars(expr, 0);
//    int ret = QuickJS::eval_expr(js->getQJSContext(), nativeString);
//    env->ReleaseStringUTFChars(expr, nativeString);


    auto *ctx = (JSContext *) context;
    const char *_expr = NULL;
    jsize _exprLength = 0;
    const char *_fileName = NULL;
    JSValue *result = NULL;


    _expr = env->GetStringUTFChars(expr, NULL);
    _exprLength = env->GetStringUTFLength(expr);
    _fileName = env->GetStringUTFChars(fileName, NULL);

    if (_expr != NULL && _fileName != NULL) {
        result = QuickJS::eval(ctx, _expr, _exprLength, _fileName, flags);
    }

    if (_expr != NULL) {
        env->ReleaseStringUTFChars(expr, _expr);
    }
    if (_fileName != NULL) {
        env->ReleaseStringUTFChars(fileName, _fileName);
    }

//    LOGI("my name is %d\n", JS_VALUE_GET_TAG(*result));
//    jlong a = 0;
//    a = a + JS_VALUE_GET_INT(*result);
    return reinterpret_cast<jlong >(result);
}


static jlong EvalExpr(JNIEnv *env, jclass clazz, jlong instance, jstring expr) {
//    QuickJS *js = reinterpret_cast<QuickJS * >(instance);
//    const char *nativeString = env->GetStringUTFChars(expr, 0);
//    int ret = QuickJS::eval_expr(js->getQJSContext(), nativeString);
//    env->ReleaseStringUTFChars(expr, nativeString);
    return reinterpret_cast<jint >(0);
}

static jint GetInt(JNIEnv *env, jclass __unused clazz, jlong jsValue) {
    auto *value = reinterpret_cast<JSValue *>(jsValue);
    return (jint) (JS_VALUE_GET_INT(*value));
}

static jboolean GetBool(JNIEnv *env, jclass __unused clazz, jlong jsValue) {
    auto *value = reinterpret_cast<JSValue *>(jsValue);
    return (jboolean) (JS_VALUE_GET_BOOL(*value));
}

static jdouble GetFloat64(JNIEnv *env, jclass __unused clazz, jlong jsValue) {
    auto *value = reinterpret_cast<JSValue *>(jsValue);
    return (jdouble) (JS_VALUE_GET_FLOAT64(*value));
}

static jstring GetString(JNIEnv *env, jclass __unused clazz, jlong context, jlong jsValue) {
    auto *value = reinterpret_cast<JSValue *>(jsValue);
    auto * ctx = reinterpret_cast<JSContext *>(context);
    const char *str = JS_ToCString(ctx, *value);
    jstring jstr = env->NewStringUTF(str);
    JS_FreeCString(ctx, str);
    return jstr;
}

static void ReleaseJSValue(JNIEnv *env, jclass __unused clazz, jlong context, jlong jsValue) {
    auto *value = reinterpret_cast<JSValue *>(jsValue);
    auto * ctx = reinterpret_cast<JSContext *>(context);
    JS_FreeValue(ctx, *value);
    js_free_rt(JS_GetRuntime(ctx), value);
}


static JNINativeMethod gMethod[] = {
        {
                .name = "nativeCreateQJSRuntime",
                .signature ="()J",
                .fnPtr = reinterpret_cast<void *>(&CreateQJSRuntime)
        },
        {
                .name = "nativeSetMemoryLimit",
                .signature ="(JJ)V",
                .fnPtr = reinterpret_cast<void *>(&SetMemoryLimit)
        },
        {
                .name = "nativeSetMaxStackSize",
                .signature ="(JJ)V",
                .fnPtr = reinterpret_cast<void *>(&SetMaxStackSize)
        },
        {
                .name = "nativeReleaseQJSRuntime",
                .signature ="(J)V",
                .fnPtr = reinterpret_cast<void *>(&ReleaseQJSRuntime)
        },
        {
                .name = "nativeNewQJSContext",
                .signature ="(J)J",
                .fnPtr = reinterpret_cast<void *>(&NewQJSContext)
        },
        {
                .name = "nativeReleaseQJSContext",
                .signature ="(J)V",
                .fnPtr = reinterpret_cast<void *>(&ReleaseQJSContext)
        },
        {
                .name = "nativeEval",
                .signature ="(JLjava/lang/String;Ljava/lang/String;I)J",
                .fnPtr = reinterpret_cast<void *>(&Eval)
        },
        {
                .name ="nativeEvalExpr",
                .signature = "(JLjava/lang/String;)I",
                .fnPtr = reinterpret_cast<void *>(&EvalExpr)
        },
        {
                .name ="nativeGetInt",
                .signature = "(J)I",
                .fnPtr = reinterpret_cast<void *>(&GetInt)
        },
        {
                .name ="nativeGetBool",
                .signature = "(J)Z",
                .fnPtr = reinterpret_cast<void *>(&GetBool)
        },
        {
                .name ="nativeGetFloat64",
                .signature = "(J)D",
                .fnPtr = reinterpret_cast<void *>(&GetFloat64)
        },
        {
                .name ="nativeGetString",
                .signature = "(JJ)Ljava/lang/String;",
                .fnPtr = reinterpret_cast<void *>(&GetString)
        },
        {
                .name ="nativeReleaseJSValue",
                .signature = "(JJ)V",
                .fnPtr = reinterpret_cast<void *>(&ReleaseJSValue)
        }

};


template<typename T, std::size_t N>
constexpr std::size_t size(T (&array)[N]) {
    return N;
}

bool QuickJSJNI::RegisterApi(JNIEnv *env) {
    if (env == nullptr) {
        return false;
    }
    jclass clazz = env->FindClass("io/github/zauther/android/hive/qjs/jni/QuickJSJNI");
    if (clazz == nullptr) {
        return false;
    }
    env->GetJavaVM(&g_VM);
    return env->RegisterNatives(clazz, gMethod, size(gMethod)) == 0;
}