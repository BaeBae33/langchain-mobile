package com.langchain.mobile.native_feature

import android.content.pm.PackageManager

object SystemServices {
    val packageManager: PackageManager by lazy { LangChainNativeFeatureApplication.app.packageManager }
}