# fetch quickjs source code
include(FetchContent)
FetchContent_Declare(quickjs
        GIT_REPOSITORY git@github.com:bellard/quickjs.git
        GIT_TAG b5e62895c619d4ffc75c9d822c8d85f1ece77e5b)
FetchContent_MakeAvailable(quickjs)

option(LEAK_TRIGGER "Add a leak trigger" FALSE)

##### QJS_CONFIG_VERSION #####
set(QJS_CONFIG_VERSION)
#file(READ ${quickjs_SOURCE_DIR}/VERSION QJS_CONFIG_VERSION)
#string(REGEX REPLACE "\n$" "" QJS_CONFIG_VERSION "${QJS_CONFIG_VERSION}")
if(EXISTS "${quickjs_SOURCE_DIR}/VERSION")
    file(RENAME ${quickjs_SOURCE_DIR}/VERSION ${quickjs_SOURCE_DIR}/_VERSION)  # windows不区分大小写，文件会与version.h冲突
endif()

file(STRINGS ${quickjs_SOURCE_DIR}/_VERSION QJS_CONFIG_VERSION)
message("===${QJS_CONFIG_VERSION}===")
#add_definitions(-DCONFIG_VERSION="${QJS_CONFIG_VERSION}")

if (QJC_HOST_PLANTFORM MATCHES "Darwin")
    set(CONFIG_DARWIN)
endif ()

if (CMAKE_BUILD_TYPE MATCHES "Release")
    set(CONFIG_LTO)
endif ()

if (DEFINED CONFIG_DARWIN)
    set(CONFIG_CLANG)
    set(CONFIG_DEFAULT_AR)
endif ()

# include the code for BigInt/BigFloat/BigDecimal and math mode
#set(CONFIG_BIGNUM)

if (DEFINED CONFIG_CLANG)
    set(HOST_CC "clang")
else ()
    set(HOST_CC "gcc")
endif ()


if (CMAKE_CXX_COMPILER_ID MATCHES "Clang")
    set(CONFIG_CC clang)
else ()
    set(CONFIG_CC gcc)
endif ()

set(COMMON_FLAGS -D_GNU_SOURCE -DCONFIG_VERSION=\"${QJS_CONFIG_VERSION}\" -DCONFIG_CC=\"${CONFIG_CC}\" -DCONFIG_PREFIX=\"/usr/local\" -DCONFIG_BIGNUM)
if (CMAKE_BUILD_TYPE STREQUAL "Debug")
    set(COMMON_FLAGS ${COMMON_FLAGS} -DDUMP_LEAKS)
endif (CMAKE_BUILD_TYPE STREQUAL "Debug")

if (LEAK_TRIGGER)
    # Use printf as leak_trigger
    set(COMMON_FLAGS ${COMMON_FLAGS} -Dprintf=leak_trigger)
endif (LEAK_TRIGGER)


## set quickjs comple cmd
#if (CMAKE_SYSTEM_NAME MATCHES "Darwin")
#    set(CONFIG_DARWIN)
#endif ()
#
#set(CONFIG_LTO)
#
#if (DEFINED CONFIG_DARWIN)
#    set(CONFIG_CLANG)
#    set(CONFIG_DEFAULT_AR)
#endif ()
#
## include the code for BigInt/BigFloat/BigDecimal and math mode
#set(CONFIG_BIGNUM)
#
#if (DEFINED CONFIG_CLANG)
#    set(HOST_CC "clang")
#else ()
#    set(HOST_CC "gcc")
#endif ()
#
#set(__linux__)

set(QUICKJS_LIB_SOURCES
        ${quickjs_SOURCE_DIR}/quickjs.c
        ${quickjs_SOURCE_DIR}/libregexp.c
        ${quickjs_SOURCE_DIR}/libunicode.c
        ${quickjs_SOURCE_DIR}/cutils.c
        ${quickjs_SOURCE_DIR}/quickjs-libc.c
        ${quickjs_SOURCE_DIR}/libbf.c
        )
include_directories(${quickjs_SOURCE_DIR})

add_library(quickjs SHARED ${QUICKJS_LIB_SOURCES})
target_compile_options(quickjs PRIVATE ${COMMON_FLAGS})
target_include_directories(quickjs PUBLIC ${quickjs_SOURCE_DIR})

#if (CMAKE_CXX_COMPILER_ID MATCHES "Clang")
#    set(CONFIG_CC clang)
#else ()
#    set(CONFIG_CC gcc)
#endif ()
#set(COMMON_FLAGS -D_GNU_SOURCE -DCONFIG_VERSION=\"${CONFIG_VERSION}\" -DCONFIG_CC=\"${CONFIG_CC}\" -DCONFIG_PREFIX=\"/usr/local\" -DCONFIG_BIGNUM)
#
#set(QUICKJS_LIB_SOURCES
#        ${quickjs_SOURCE_DIR}/libregexp.c
#        ${quickjs_SOURCE_DIR}/libunicode.c
#        ${quickjs_SOURCE_DIR}/libbf.c
#        ${quickjs_SOURCE_DIR}/cutils.c
#        )
#set(QJS_LIB_SOURCES
#        ${quickjs_SOURCE_DIR}/quickjs-libc.c
#        ${QUICKJS_LIB_SOURCES}
#        )
#
#set(QJS_SOURCES
#        ${quickjs_SOURCE_DIR}/qjs.c
#        #        ${quickjs_SOURCE_DIR}/repl.c
#        ${quickjs_SOURCE_DIR}/qjscalc.c
#        ${QJS_LIB_SOURCES}
#        )
#if (CMAKE_BUILD_TYPE STREQUAL "Debug")
#    set(COMMON_FLAGS ${COMMON_FLAGS} -DDUMP_LEAKS)
#endif (CMAKE_BUILD_TYPE STREQUAL "Debug")
#
#if (LEAK_TRIGGER)
#    # Use printf as leak_trigger
#    set(COMMON_FLAGS ${COMMON_FLAGS} -Dprintf=leak_trigger)
#endif (LEAK_TRIGGER)
#
## Camouflage executable files as shared libraries to make apk include them
#
#add_executable(qjs ${QJS_SOURCES})
#target_compile_options(qjs PRIVATE ${COMMON_FLAGS})
#set_target_properties(qjs PROPERTIES OUTPUT_NAME libqjs.so)
#
#add_executable(qjsc ${quickjs_SOURCE_DIR}/qjsc.c ${QJS_LIB_SOURCES})
#target_compile_options(qjsc PRIVATE ${COMMON_FLAGS})
#set_target_properties(qjsc PROPERTIES OUTPUT_NAME libqjsc.so)
#
#add_executable(run-test262 ${quickjs_SOURCE_DIR}/run-test262.c ${QJS_LIB_SOURCES})
#target_compile_options(run-test262 PRIVATE ${COMMON_FLAGS})
#set_target_properties(run-test262 PROPERTIES OUTPUT_NAME librun-test262.so)
#
#add_library(quickjs STATIC ${QUICKJS_LIB_SOURCES})
#target_compile_options(quickjs PRIVATE ${COMMON_FLAGS})
#target_include_directories(quickjs PUBLIC quickjs ${quickjs_SOURCE_DIR})