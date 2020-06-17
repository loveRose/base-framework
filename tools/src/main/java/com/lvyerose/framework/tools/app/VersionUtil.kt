package com.lvyerose.framework.tools.app

import android.content.Context
import android.content.pm.PackageManager

/**
 * desc: app版本工具
 * author: lad
 */
object VersionUtil {
    /**
     * 获取应用程序名称
     */
    fun getAppName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo =
                packageManager.getPackageInfo(context.packageName, 0)
            val labelRes = packageInfo.applicationInfo.labelRes
            return context.resources.getString(labelRes)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取版本号
     */
    fun getVersion(context: Context): String {
        return try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * 版本比较
     */
    fun compareVersion(nowVersion: String?, serverVersion: String?): Boolean {
        if (nowVersion != null && serverVersion != null) {
            val nowVersions = nowVersion.split("\\.").toTypedArray()
            val serverVersions =
                serverVersion.split("\\.").toTypedArray()
            if (nowVersions.size > 1 && serverVersions.size > 1) {
                val nowVersionFirst = nowVersions[0].toInt()
                val serverVersionFirst = serverVersions[0].toInt()
                val nowVersionSecond = nowVersions[1].toInt()
                val serverVersionSecond = serverVersions[1].toInt()
                return if (nowVersionFirst < serverVersionFirst) {
                    true
                } else nowVersionFirst == serverVersionFirst && nowVersionSecond < serverVersionSecond
            }
        }
        return false
    }
}