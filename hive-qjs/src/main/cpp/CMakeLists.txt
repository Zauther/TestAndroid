# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.16.2)

# Declares and names the project.

project("quickjs4android")
message("==============${PROJECT_NAME}===============")
# 打印编译过程
set ( CMAKE_VERBOSE_MAKEFILE on  )

include("quickjs.cmake")
include("qjsmodule.cmake")
include("cronet.cmake")

#FetchContent_GetProperties(quickjs)
#if(NOT quickjs_POPULATED)
#    FetchContent_Populate(quickjs)
#    add_subdirectory(${quickjs_SOURCE_DIR} ${quickjs_BINARY_DIR} EXCLUDE_FROM_ALL)
#endif()

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

include_directories(${CMAKE_SOURCE_DIR}/ ${CMAKE_SOURCE_DIR}/jni/ ${CMAKE_SOURCE_DIR}/include/)
LINK_DIRECTORIES(${CMAKE_SOURCE_DIR}/libs/)

file(GLOB_RECURSE CPP_SRCS jni/*.cpp *.cpp module/*.c )

find_library(log-lib log )






# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.



##导入第三方依赖库
#add_library(
#        quickjs
#        STATIC IMPORTED
#)
##设置第三方依赖库的目录地址
#set_target_properties(
#        quickjs
#        PROPERTIES IMPORTED_LOCATION
#        ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libquickjs.a
#)


# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

add_library( # Sets the name of the library.
        quickjs4android

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        ${CPP_SRCS} )

target_link_libraries( # Specifies the target library.
        quickjs4android
        quickjs
                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
