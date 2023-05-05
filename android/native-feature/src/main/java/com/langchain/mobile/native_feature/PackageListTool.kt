package com.langchain.mobile.native_feature

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.langchain.mobile.android.core.base.tool.BaseTool

class PackageListTool : BaseTool {

    operator fun invoke(): String {
        return getApplicationList().joinToString(", ") { PackageManagerCompat.getApplicationLabel(it) }
    }

    private fun getApplicationList(): List<PackageInfo> {
        return PackageManagerCompat.getInstalledPackages(PackageManager.GET_META_DATA or PackageManager.GET_PERMISSIONS)
    }

}