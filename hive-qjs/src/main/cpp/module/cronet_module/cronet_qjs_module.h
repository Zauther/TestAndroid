////
//// Created by zauther on 2022/6/4.
////
//
//#ifndef TESTANDROID_CRONET_QJS_MODULE_H
//#define TESTANDROID_CRONET_QJS_MODULE_H
//
//
//#include "cronet/cronet.idl_c.h"
//
//
//#include "cronet_qjs_export.h"
//
//#include <stdbool.h>
//
//#ifdef __cplusplus
//
//extern "C" {
//#endif
//
//#include <stdint.h>
//
//typedef struct SampleExecutor *SampleExecutorPtr;
//typedef struct UploadDataProvider *UploadDataProviderPtr;
//
//CRONET_QJS_EXPORT const char *VersionString();
//
//CRONET_QJS_EXPORT intptr_t InitDartApiDL(void *data);
//CRONET_QJS_EXPORT void InitCronetApi(
//        Cronet_RESULT (*Cronet_Engine_Shutdown)(Cronet_EnginePtr),
//        void (*Cronet_Engine_Destroy)(Cronet_EnginePtr),
//        Cronet_BufferPtr (*Cronet_Buffer_Create)(void),
//        void (*Cronet_Buffer_InitWithAlloc)(Cronet_BufferPtr, uint64_t),
//        int32_t (*Cronet_UrlResponseInfo_http_status_code_get)(
//                const Cronet_UrlResponseInfoPtr),
//        Cronet_String (*Cronet_Error_message_get)(const Cronet_ErrorPtr),
//        Cronet_String (*Cronet_UrlResponseInfo_http_status_text_get)(
//                const Cronet_UrlResponseInfoPtr),
//        Cronet_ClientContext (*Cronet_UploadDataProvider_GetClientContext)(
//                Cronet_UploadDataProviderPtr self));
//
///* Forward declaration. Implementation on sample_executor.cc */
//CRONET_QJS_EXPORT void InitCronetExecutorApi(
//        Cronet_ExecutorPtr (*Cronet_Executor_CreateWith)(
//                Cronet_Executor_ExecuteFunc),
//        void (*Cronet_Executor_SetClientContext)(Cronet_ExecutorPtr,
//                                                 Cronet_ClientContext),
//        Cronet_ClientContext (*Cronet_Executor_GetClientContext)(
//                Cronet_ExecutorPtr),
//        void (*Cronet_Executor_Destroy)(Cronet_ExecutorPtr),
//        void (*Cronet_Runnable_Run)(Cronet_RunnablePtr),
//        void (*Cronet_Runnable_Destroy)(Cronet_RunnablePtr));
//
//CRONET_QJS_EXPORT void RegisterHttpClient(Dart_Handle h, Cronet_Engine *ce);
//CRONET_QJS_EXPORT void RegisterCallbackHandler(Dart_Port nativePort,
//                                            Cronet_UrlRequest *rp);
//CRONET_QJS_EXPORT void RemoveRequest(Cronet_UrlRequest *rp);
//
///* Callbacks. ISSUE: https://github.com/dart-lang/sdk/issues/37022 */
//
//CRONET_QJS_EXPORT void OnRedirectReceived(Cronet_UrlRequestCallbackPtr self,
//                                       Cronet_UrlRequestPtr request,
//                                       Cronet_UrlResponseInfoPtr info,
//                                       Cronet_String newLocationUrl);
//
//CRONET_QJS_EXPORT void OnResponseStarted(Cronet_UrlRequestCallbackPtr self,
//                                      Cronet_UrlRequestPtr request,
//                                      Cronet_UrlResponseInfoPtr info);
//
//CRONET_QJS_EXPORT void OnReadCompleted(Cronet_UrlRequestCallbackPtr self,
//                                    Cronet_UrlRequestPtr request,
//                                    Cronet_UrlResponseInfoPtr info,
//                                    Cronet_BufferPtr buffer,
//                                    uint64_t bytes_read);
//
//CRONET_QJS_EXPORT void OnSucceeded(Cronet_UrlRequestCallbackPtr self,
//                                Cronet_UrlRequestPtr request,
//                                Cronet_UrlResponseInfoPtr info);
//
//CRONET_QJS_EXPORT void OnFailed(Cronet_UrlRequestCallbackPtr self,
//                             Cronet_UrlRequestPtr request,
//                             Cronet_UrlResponseInfoPtr info,
//                             Cronet_ErrorPtr error);
//
//CRONET_QJS_EXPORT void OnCanceled(Cronet_UrlRequestCallbackPtr self,
//                               Cronet_UrlRequestPtr request,
//                               Cronet_UrlResponseInfoPtr info);
//
///* Sample Executor C APIs */
//
//CRONET_QJS_EXPORT SampleExecutorPtr SampleExecutorCreate();
//CRONET_QJS_EXPORT void SampleExecutorDestroy(SampleExecutorPtr executor);
//
//CRONET_QJS_EXPORT void InitSampleExecutor(SampleExecutorPtr self);
//CRONET_QJS_EXPORT Cronet_ExecutorPtr
//SampleExecutor_Cronet_ExecutorPtr_get(SampleExecutorPtr self);
//
///* Upload Data Provider C APIs */
//CRONET_QJS_EXPORT UploadDataProviderPtr UploadDataProviderCreate();
//CRONET_QJS_EXPORT void
//UploadDataProviderDestroy(UploadDataProviderPtr upload_data_provided);
//CRONET_QJS_EXPORT void UploadDataProviderInit(UploadDataProviderPtr self,
//                                           int64_t length,
//                                           Cronet_UrlRequestPtr request);
//
//CRONET_QJS_EXPORT int64_t
//UploadDataProvider_GetLength(Cronet_UploadDataProviderPtr self);
//CRONET_QJS_EXPORT void
//UploadDataProvider_Read(Cronet_UploadDataProviderPtr self,
//                        Cronet_UploadDataSinkPtr upload_data_sink,
//                        Cronet_BufferPtr buffer);
//CRONET_QJS_EXPORT void
//UploadDataProvider_Rewind(Cronet_UploadDataProviderPtr self,
//                          Cronet_UploadDataSinkPtr upload_data_sink);
//CRONET_QJS_EXPORT void
//UploadDataProvider_CloseFunc(Cronet_UploadDataProviderPtr self);
//#ifdef __cplusplus
//}
//#endif
//
//#endif //TESTANDROID_CRONET_QJS_MODULE_H
