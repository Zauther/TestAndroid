# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.18.1)

# Declares and names the project.
set ( CMAKE_VERBOSE_MAKEFILE on  )
project("hive-network")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/../jni/${ANDROID_ABI}")

include_directories(${CMAKE_SOURCE_DIR}/ ${CMAKE_SOURCE_DIR}/cronet_impl/ ${CMAKE_SOURCE_DIR}/cronet/ ${CMAKE_SOURCE_DIR}/cronet_sample/)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        hive-network

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        HiveNetwork.cpp)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)
#find_library(cronet-lib cronet.92.0.4509.1 )
#set_target_properties(
#        cronet-lib
#        PROPERTIES IMPORTED_LOCATION
#        ${CMAKE_SOURCE_DIR}/libcronet.92.0.4509.1.so
#)
## Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.
LINK_DIRECTORIES(${CMAKE_SOURCE_DIR})

target_link_libraries( # Specifies the target library.
        hive-network

        # Links the target library to the log library
        # included in the NDK.
        libcronet.92.0.4509.1.so
        ${log-lib})