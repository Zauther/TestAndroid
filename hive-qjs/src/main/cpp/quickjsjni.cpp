//
// Created by zauther on 21-4-3.
//

#include <jni.h>
#include "QuickJSJNI.h"
JNIEXPORT jint JNI_OnLoad(JavaVM *vm,void *reserved){
    JNIEnv *env = nullptr;
    if(vm->GetEnv((void**)&env,JNI_VERSION_1_4)!=JNI_OK){
        return -1;
    }
    if(!QuickJSJNI::RegisterApi(env)){
        return -1;
    }
    return JNI_VERSION_1_4;
}