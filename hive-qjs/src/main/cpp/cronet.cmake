
set(PLUGIN_NAME "wrapper")


if(IOS)
    add_library(${PLUGIN_NAME} STATIC
#            "wrapper.cc"
#            "wrapper_utils.cc"
#            "upload_data_provider.cc"
#            "cronet_impl/sample_executor.cc"
            "module/cronet_module/cronet_qjs_module.cpp"
            )
else()
    add_library(${PLUGIN_NAME} SHARED
#            "wrapper.cc"
#            "wrapper_utils.cc"
#            "upload_data_provider.cc"
#            "cronet_impl/sample_executor.cc"
            "module/cronet_module/cronet_qjs_module.cpp"
            )
endif()

include_directories(${quickjs_SOURCE_DIR})

set_target_properties(${PLUGIN_NAME} PROPERTIES
        CXX_VISIBILITY_PRESET hidden)

target_include_directories(${PLUGIN_NAME} INTERFACE
        "module/cronet_module"
        "module/cronet_module/cronet"
        )
target_link_libraries(${PLUGIN_NAME} quickjs ${log-lib} )
if (MSVC)
    set(MSVC_DISABLED_WARNINGS_LIST
            "C4152" # nonstandard extension, function/data pointer conversion in expression
            "C4255"
            "C4820"
            "C4255"
            "C4668"
            )
    string(REPLACE "C" " -wd" MSVC_DISABLED_WARNINGS_STR ${MSVC_DISABLED_WARNINGS_LIST})
    set(CMAKE_C_FLAGS   "-utf-8 -Wall -WX ${MSVC_DISABLED_WARNINGS_STR}")
endif (MSVC)
