package com.langchain.mobile.native_feature

import android.app.Application
import android.content.Context

class LangChainNativeFeatureApplication : Application() {

    companion object {
        lateinit var app: Application
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        app = this
    }
}