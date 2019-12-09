LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
# Reference java/ and aidl/ source files
LOCAL_SRC_FILES := \
    $(call all-java-files-under, app/src/main/java) \
    $(call all-named-files-under, *.aidl, app/src/main/aidl)
# Re-map res/ and assets/ directly
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/app/src/main/res
LOCAL_ASSET_DIR := $(LOCAL_PATH)/app/src/main/assets

LOCAL_PACKAGE_NAME := MySystemApplication
LOCAL_SDK_VERSION := current
LOCAL_PROGUARD_ENABLED := disabled
LOCAL_CERTIFICATE := platform

include $(BUILD_PACKAGE)


