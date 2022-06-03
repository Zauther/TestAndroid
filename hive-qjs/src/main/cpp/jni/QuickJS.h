//
// Created by zauther on 21-4-3.
//

#ifndef QUICKJS4ANDROID_QUICKJS_H
#define QUICKJS4ANDROID_QUICKJS_H
#include <quickjs.h>
#include <jni.h>


class QuickJS {
private:
    JSRuntime * runtime;
    JSContext * context;

public:


    static JSRuntime * createNewQJSRuntime();
    static void setMemoryLimit(JSRuntime *runtime, long limit);
    static void setMaxStackSize(JSRuntime *runtime, long size);
    static void releaseQJSRuntime(JSRuntime *runtime);

    static JSContext * newQJSContext(JSRuntime *runtime);
    static void releaseQJSContext(JSContext *context);

    static JSValue* eval(JSContext *ctx, const char *buf, int buf_len,
             const char *filename, int eval_flags);

    int eval_file(JSContext *ctx, const char *filename, int module);




    static int eval_expr(JSContext *ctx, const char * expr);

    static int eval_buf(JSContext *ctx, const void *buf, int buf_len,
                         const char *filename, int eval_flags);

};


#endif //QUICKJS4ANDROID_QUICKJS_H
