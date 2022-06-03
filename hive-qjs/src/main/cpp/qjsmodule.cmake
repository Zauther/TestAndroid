
message("===============${quickjs_BINARY_DIR}  =================")

set(QJC_HOST_PLANTFORM)
execute_process(COMMAND uname -s OUTPUT_VARIABLE QJC_HOST_PLANTFORM)
string(REGEX REPLACE "\n$" "" QJC_HOST_PLANTFORM "${QJC_HOST_PLANTFORM}")
message("===============${CMAKE_BUILD_TYPE}=================")
if (QJC_HOST_PLANTFORM MATCHES "Darwin")
    set(CONFIG_DARWIN)
endif ()

if (CMAKE_BUILD_TYPE MATCHES "Release")
    set(CONFIG_LTO)
endif ()


####################### qjsc tool #######################


####################### ndk log #######################


#add_library(ndklogmodule SHARED ${CPP_SRCS})
###target_compile_options(quickjs PRIVATE ${COMMON_FLAGS})
##target_include_directories(ndklog PUBLIC  ${quickjs_SOURCE_DIR})
#target_link_libraries(ndklogmodule  quickjs ${log-lib} )