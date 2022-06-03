#include <android/log.h>
#ifndef LOGI
#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO,"QJSNativeLog",__VA_ARGS__))
#endif

