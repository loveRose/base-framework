package com.lvyerose.framework.tools.app

import android.app.Activity
import android.app.AppOpsManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import java.lang.reflect.InvocationTargetException

/**
 * desc: 权限相关工具类
 * author: lad
 */
object PermissionUtils {
    /**
     * 检查通知栏权限有没有开启
     * 参考SupportCompat包中的： NotificationManagerCompat.from(context).areNotificationsEnabled();
     */
    fun isNotificationEnabled(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).areNotificationsEnabled()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val appOps =
                context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val appInfo = context.applicationInfo
            val pkg = context.applicationContext.packageName
            val uid = appInfo.uid
            try {
                val appOpsClass =
                    Class.forName(AppOpsManager::class.java.name)
                val checkOpNoThrowMethod = appOpsClass.getMethod(
                    "checkOpNoThrow",
                    Integer.TYPE,
                    Integer.TYPE,
                    String::class.java
                )
                val opPostNotificationValue =
                    appOpsClass.getDeclaredField("OP_POST_NOTIFICATION")
                val value = opPostNotificationValue[Int::class.java] as Int
                checkOpNoThrowMethod.invoke(appOps, value, uid, pkg) as Int == 0
            } catch (ignored: NoSuchMethodException) {
                true
            } catch (ignored: NoSuchFieldException) {
                true
            } catch (ignored: InvocationTargetException) {
                true
            } catch (ignored: IllegalAccessException) {
                true
            } catch (ignored: RuntimeException) {
                true
            } catch (ignored: ClassNotFoundException) {
                true
            }
        } else {
            true
        }
    }

    /**
     * 设置页面跳转
     * @param activity
     */
    fun openPush(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            val intent = Intent()
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
            intent.putExtra(
                Settings.EXTRA_CHANNEL_ID,
                activity.applicationInfo.uid
            )
            activity.startActivity(intent)
        } else {
            toPermissionSetting(activity)
        }
    }

    /**
     * 跳转到权限设置
     *
     * @param activity
     */
    private fun toPermissionSetting(activity: Activity) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            toSystemConfig(activity)
        } else {
            try {
                toApplicationInfo(activity)
            } catch (e: Exception) {
                e.printStackTrace()
                toSystemConfig(activity)
            }
        }
    }

    /**
     * 应用信息界面
     *
     * @param activity
     */
    private fun toApplicationInfo(activity: Activity) {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        localIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        localIntent.data = Uri.fromParts("package", activity.packageName, null)
        activity.startActivity(localIntent)
    }

    /**
     * 系统设置界面
     *
     * @param activity
     */
    private fun toSystemConfig(activity: Activity) {
        try {
            val intent = Intent(Settings.ACTION_SETTINGS)
            activity.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}