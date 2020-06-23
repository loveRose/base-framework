package com.lvyerose.framework.tools.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration
import java.util.*

/**
 * desc: 系统工具类
 * author: lad
 */
object SystemUtil {
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    val systemLanguage: String
        get() = Locale.getDefault().language

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    val systemLanguageList: Array<Locale>
        get() = Locale.getAvailableLocales()

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    val systemVersion: String
        get() = Build.VERSION.RELEASE

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    val systemModel: String
        get() = Build.MODEL

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    val deviceBrand: String
        get() = Build.BRAND

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getIMEI(ctx: Context): String? {
        val tm =
            ctx.getSystemService(Activity.TELEPHONY_SERVICE) as TelephonyManager
        return tm.deviceId
    }

    fun checkDeviceHasNavigationBar(activity: Activity?): Boolean {
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        val hasMenuKey = ViewConfiguration.get(activity)
            .hasPermanentMenuKey()
        val hasBackKey = KeyCharacterMap
            .deviceHasKey(KeyEvent.KEYCODE_BACK)
        return !hasMenuKey && !hasBackKey
        // 有虚拟按键返回 false
    }
}