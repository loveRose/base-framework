package com.lvyerose.framework.tools

import android.content.Context
import android.content.pm.PackageManager

/**
 * desc: 跟App相关的辅助类
 * author: lad
 */
class AppUtils private constructor() {
    companion object {
        /**
         * 获取应用程序名称
         */
        fun getAppName(context: Context): String? {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0
                )
                val labelRes = packageInfo.applicationInfo.labelRes
                return context.resources.getString(labelRes)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * 获取应用程序版本名称信息
         */
        fun getVersionName(context: Context): String? {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0
                )
                return packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return null
        }
    }

    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }
}