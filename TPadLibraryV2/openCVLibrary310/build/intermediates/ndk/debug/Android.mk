LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := openCVLibrary310
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/Users/David/Documents/AndroidProjects/TPadLibraryV2/openCVLibrary310/src/main/jniLibs/armeabi-v7a/libopencv_java3.so \
	/Users/David/Documents/AndroidProjects/TPadLibraryV2/openCVLibrary310/src/main/jniLibs/x86/libopencv_java3.so \
	/Users/David/Documents/AndroidProjects/TPadLibraryV2/openCVLibrary310/src/main/jniLibs/x86_64/libopencv_java3.so \

LOCAL_C_INCLUDES += /Users/David/Documents/AndroidProjects/TPadLibraryV2/openCVLibrary310/src/main/jni
LOCAL_C_INCLUDES += /Users/David/Documents/AndroidProjects/TPadLibraryV2/openCVLibrary310/src/main/jniLibs
LOCAL_C_INCLUDES += /Users/David/Documents/AndroidProjects/TPadLibraryV2/openCVLibrary310/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
