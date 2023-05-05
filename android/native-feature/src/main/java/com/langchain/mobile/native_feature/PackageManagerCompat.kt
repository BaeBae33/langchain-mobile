package com.langchain.mobile.native_feature

import android.content.pm.PackageInfo
import android.content.pm.PackageManager

object PackageManagerCompat {
    fun getInstalledPackages(flags: Int): List<PackageInfo> {
        return if (OsUtils.atLeastT()) {
            SystemServices.packageManager.getInstalledPackages(PackageManager.PackageInfoFlags.of(flags.toLong()))
        } else {
            SystemServices.packageManager.getInstalledPackages(flags)
        }
    }

    fun getApplicationLabel(packageInfo: PackageInfo): String {
        return SystemServices.packageManager.getApplicationLabel(packageInfo.applicationInfo).toString()
    }

}