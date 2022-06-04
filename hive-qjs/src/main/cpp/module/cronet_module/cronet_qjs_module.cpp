//
// Created by zauther on 2022/6/4.
//
#include <quickjs.h>
#include <android/log.h>

#ifndef LOGI
#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO,"ndklogmodule",__VA_ARGS__))
#endif // LOGI(...)
//#ifndef LOGE(...)
//#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR,"ndklogmodule",__VA_ARGS__))

#define countof(x) (sizeof(x) / sizeof((x)[0]))

static int fib(int n)
{
    if (n <= 0)
        return 0;
    else if (n == 1)
        return 1;
    else
        return fib(n - 1) + fib(n - 2);
}

static JSValue js_fib(JSContext *ctx, JSValueConst this_val,
                      int argc, JSValueConst *argv)
{
    int n, res;
    if (JS_ToInt32(ctx, &n, argv[0]))
        return JS_EXCEPTION;
    res = fib(n);
//    LOGI("res:%d",res);
    return JS_NewInt32(ctx, res);
}

static const JSCFunctionListEntry cronet_module_funcs[] = {
        JS_CFUNC_DEF("fib", 1, js_fib ),
};

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
//        JAVA_Console(str);
        JS_FreeCString(ctx, str);
    }
    putchar('\n');
    return JS_UNDEFINED;
}

static int cronet_module_init(JSContext *ctx, JSModuleDef *m)
{

//    JSValue cronet;
//    global_obj = JS_GetGlobalObject(ctx);
//    cronet = JS_NewObject(ctx);
//    JS_SetPropertyStr(ctx, console, "log",
//                      JS_NewCFunction(ctx, js_print, "log", 1));
//    JS_SetPropertyStr(ctx, global_obj, "console", console);
//    return JS_SetModuleExportList(ctx, m, js_fib_funcs,
//                                  countof(js_fib_funcs));
    JSValue cronet = JS_NewCFunction(ctx, js_print, "request", 1);
    JS_SetModuleExport(ctx, m, "cronet", cronet);
    return 0;
}


#ifdef JS_SHARED_LIBRARY
#define JS_INIT_MODULE js_init_module
#else
#define JS_INIT_MODULE js_init_module_fib
#endif

JSModuleDef *JS_INIT_MODULE(JSContext *ctx, const char *module_name)
{
    JSModuleDef *m;
    m = JS_NewCModule(ctx, module_name, cronet_module_init);
    if (!m)
        return NULL;
    JS_AddModuleExportList(ctx, m, cronet_module_funcs, countof(cronet_module_funcs));
    return m;
}
