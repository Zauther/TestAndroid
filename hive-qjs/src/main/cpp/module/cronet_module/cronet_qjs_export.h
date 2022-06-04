//
// Created by zauther on 2022/6/4.
//

#ifndef TESTANDROID_CRONET_QJS_EXPORT_H
#define TESTANDROID_CRONET_QJS_EXPORT_H
#if defined(WIN32)
#define CRONET_QJS_EXPORT __declspec(dllexport)
#else
#define CRONET_QJS_EXPORT __attribute__((visibility("default")))
#endif
#endif //TESTANDROID_CRONET_QJS_EXPORT_H
