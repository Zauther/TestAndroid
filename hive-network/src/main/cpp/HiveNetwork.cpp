//
// Created by zauther on 2022/6/10.
//

#include "HiveNetwork.h"
#include <jni.h>
#include <cstddef>
#include <cronet.idl_c.h>

JavaVM *g_VM;

static jstring Version(JNIEnv *env, jclass __unused clazz) {
    jstring jstr = env->NewStringUTF("0.0.1");
    return jstr;
}

static long CreateCronetEngine(JNIEnv *env, jclass __unused clazz) {
    Cronet_EnginePtr enginePtr = Cronet_Engine_Create();
    return reinterpret_cast<long >(enginePtr);;
}

static JNINativeMethod gMethod[] = {
        {
                .name = "nativeVersion",
                .signature ="()Ljava/lang/String;",
                .fnPtr = reinterpret_cast<void *>(&Version)
        },
        {
                .name = "nativeCreateCronetEngine",
                .signature ="()J",
                .fnPtr = reinterpret_cast<void *>(&CreateCronetEngine)
        },

};

template<typename T, std::size_t N>
constexpr std::size_t size(T (&array)[N]) {
    return N;
}

bool HiveNetwork::RegisterApi(JNIEnv *env) {
    if (env == nullptr) {
        return false;
    }
    jclass clazz = env->FindClass("io/github/zauther/android/hive/network/jni/JNIHelper");
    if (clazz == nullptr) {
        return false;
    }
    env->GetJavaVM(&g_VM);
    return env->RegisterNatives(clazz, gMethod, size(gMethod)) == 0;
}

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = nullptr;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return -1;
    }
    if (!HiveNetwork::RegisterApi(env)) {
        return -1;
    }
    return JNI_VERSION_1_4;
}