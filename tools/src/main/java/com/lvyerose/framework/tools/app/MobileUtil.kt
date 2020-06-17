package com.lvyerose.framework.tools.app

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.SystemClock
import android.telephony.TelephonyManager
import java.text.SimpleDateFormat
import java.util.*

/**
 * desc: 手机信息采集工具
 * author: lad
 */
object MobileUtil {
    /**
     * Print telephone info.
     */
    @SuppressLint("MissingPermission", "HardwareIds", "SimpleDateFormat")
    fun printMobileInfo(context: Context): String {
        val date = Date(System.currentTimeMillis())
        val dateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = dateFormat.format(date)
        val sb = StringBuilder()
        sb.append("系统时间：").append(time).append("\n")
        val tm =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val IMSI = tm.subscriberId
        //IMSI前面三位460是国家号码，其次的两位是运营商代号，00、02是中国移动，01是联通，03是电信。
        var providerName: String? = null
        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                providerName = "中国移动"
            } else if (IMSI.startsWith("46001")) {
                providerName = "中国联通"
            } else if (IMSI.startsWith("46003")) {
                providerName = "中国电信"
            }
        }
        sb.append(providerName).append("\n").append(
            getNativePhoneNumber(
                context
            )
        )
            .append("\n网络模式：").append(
                getNetType(
                    context
                )
            ).append("\nIMSI是：")
            .append(IMSI)
        sb.append("\nDeviceID(IMEI)       :").append(tm.deviceId)
        sb.append("\nDeviceSoftwareVersion:").append(tm.deviceSoftwareVersion)
        sb.append("\ngetLine1Number       :").append(tm.line1Number)
        sb.append("\nNetworkCountryIso    :").append(tm.networkCountryIso)
        sb.append("\nNetworkOperator      :").append(tm.networkOperator)
        sb.append("\nNetworkOperatorName  :").append(tm.networkOperatorName)
        sb.append("\nNetworkType          :").append(tm.networkType)
        sb.append("\nPhoneType            :").append(tm.phoneType)
        sb.append("\nSimCountryIso        :").append(tm.simCountryIso)
        sb.append("\nSimOperator          :").append(tm.simOperator)
        sb.append("\nSimOperatorName      :").append(tm.simOperatorName)
        sb.append("\nSimSerialNumber      :").append(tm.simSerialNumber)
        sb.append("\ngetSimState          :").append(tm.simState)
        sb.append("\nSubscriberId         :").append(tm.subscriberId)
        sb.append("\nVoiceMailNumber      :").append(tm.voiceMailNumber)
        return sb.toString()
    }

    /**
     * 打印系统信息
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat", "ObsoleteSdkInt", "HardwareIds")
    fun printSystemInfo(): String {
        val date = Date(System.currentTimeMillis())
        val dateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = dateFormat.format(date)
        val sb = StringBuilder()
        sb.append("_______  系统信息  ").append(time).append(" ______________")
        sb.append("\nID                 :").append(Build.ID)
        sb.append("\nBRAND              :").append(Build.BRAND)
        sb.append("\nMODEL              :").append(Build.MODEL)
        sb.append("\nRELEASE            :").append(Build.VERSION.RELEASE)
        sb.append("\nSDK                :").append(Build.VERSION.SDK)
        sb.append("\n_______ OTHER _______")
        sb.append("\nBOARD              :").append(Build.BOARD)
        sb.append("\nPRODUCT            :").append(Build.PRODUCT)
        sb.append("\nDEVICE             :").append(Build.DEVICE)
        sb.append("\nFINGERPRINT        :").append(Build.FINGERPRINT)
        sb.append("\nHOST               :").append(Build.HOST)
        sb.append("\nTAGS               :").append(Build.TAGS)
        sb.append("\nTYPE               :").append(Build.TYPE)
        sb.append("\nTIME               :").append(Build.TIME)
        sb.append("\nINCREMENTAL        :").append(Build.VERSION.INCREMENTAL)
        sb.append("\n_______ CUPCAKE-3 _______")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            sb.append("\nDISPLAY            :").append(Build.DISPLAY)
        }
        sb.append("\n_______ DONUT-4 _______")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            sb.append("\nSDK_INT            :").append(Build.VERSION.SDK_INT)
            sb.append("\nMANUFACTURER       :").append(Build.MANUFACTURER)
            sb.append("\nBOOTLOADER         :").append(Build.BOOTLOADER)
            sb.append("\nCPU_ABI            :").append(Build.CPU_ABI)
            sb.append("\nCPU_ABI2           :").append(Build.CPU_ABI2)
            sb.append("\nHARDWARE           :").append(Build.HARDWARE)
            sb.append("\nUNKNOWN            :").append(Build.UNKNOWN)
            sb.append("\nCODENAME           :").append(Build.VERSION.CODENAME)
        }
        sb.append("\n_______ GINGERBREAD-9 _______")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sb.append("\nSERIAL             :").append(Build.SERIAL)
        }
        return sb.toString()
    }

    /****
     * 获取网络类型
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    fun getNetType(context: Context): String {
        return try {
            val connectMgr = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectMgr.activeNetworkInfo ?: return ""
            when (info.type) {
                ConnectivityManager.TYPE_WIFI -> {
                    "WIFI"
                }
                ConnectivityManager.TYPE_MOBILE -> {
                    when (info.subtype) {
                        TelephonyManager.NETWORK_TYPE_CDMA -> {
                            "CDMA"
                        }
                        TelephonyManager.NETWORK_TYPE_EDGE -> {
                            "EDGE"
                        }
                        TelephonyManager.NETWORK_TYPE_EVDO_0 -> {
                            "EVDO0"
                        }
                        TelephonyManager.NETWORK_TYPE_EVDO_A -> {
                            "EVDOA"
                        }
                        TelephonyManager.NETWORK_TYPE_GPRS -> {
                            "GPRS"
                        }
                        TelephonyManager.NETWORK_TYPE_UMTS -> {
                            "UMTS"
                        }
                        else -> {
                            "3G"
                        }
                    }
                }
                else -> {
                    ""
                }
            }
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 获取当前设置的电话号码
     */
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getNativePhoneNumber(context: Context): String {
        val telephonyManager = context
            .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var nativePhoneNumber: String? = null
        nativePhoneNumber = telephonyManager.line1Number
        return String.format("手机号: %s", nativePhoneNumber)
    }

    /**
     * IMSI是国际移动用户识别码的简称(International Mobile Subscriber Identity)
     * IMSI共有15位，其结构如下：
     * MCC+MNC+MIN
     * MCC：Mobile Country Code，移动国家码，共3位，中国为460;
     * MNC:Mobile NetworkCode，移动网络码，共2位
     * 在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03
     * 合起来就是（也是Android手机中APN配置文件中的代码）：
     * 中国移动：46000 46002
     * 中国联通：46001
     * 中国电信：46003
     * 举例，一个典型的IMSI号码为460030912121001
     */
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getIMSI(context: Context): String {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.subscriberId
    }

    /**
     * IMEI是International Mobile Equipment Identity （国际移动设备标识）的简称
     * IMEI由15位数字组成的”电子串号”，它与每台手机一一对应，而且该码是全世界唯一的
     * 其组成为：
     * 1. 前6位数(TAC)是”型号核准号码”，一般代表机型
     * 2. 接着的2位数(FAC)是”最后装配号”，一般代表产地
     * 3. 之后的6位数(SNR)是”串号”，一般代表生产顺序号
     * 4. 最后1位数(SP)通常是”0″，为检验码，目前暂备用
     */
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getIMEI(context: Context): String {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.deviceId
    }

    /**
     * MTK Phone.
     *
     *
     * 获取 MTK 神机的双卡 IMSI、IMSI 信息
     */
    @SuppressLint("PrivateApi")
    fun getMtkTeleInfo(context: Context): TeleInfo {
        val teleInfo = TeleInfo()
        try {
            val phone =
                Class.forName("com.android.internal.telephony.Phone")
            val fields1 = phone.getField("GEMINI_SIM_1")
            fields1.isAccessible = true
            val simId_1 = fields1[null] as Int
            val fields2 = phone.getField("GEMINI_SIM_2")
            fields2.isAccessible = true
            val simId_2 = fields2[null] as Int
            val tm =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val getSubscriberIdGemini =
                TelephonyManager::class.java.getDeclaredMethod(
                    "getSubscriberIdGemini",
                    Int::class.javaPrimitiveType
                )
            val imsi_1 = getSubscriberIdGemini.invoke(tm, simId_1) as String
            val imsi_2 = getSubscriberIdGemini.invoke(tm, simId_2) as String
            teleInfo.imsi_1 = imsi_1
            teleInfo.imsi_2 = imsi_2
            val getDeviceIdGemini =
                TelephonyManager::class.java.getDeclaredMethod(
                    "getDeviceIdGemini",
                    Int::class.javaPrimitiveType
                )
            val imei_1 = getDeviceIdGemini.invoke(tm, simId_1) as String
            val imei_2 = getDeviceIdGemini.invoke(tm, simId_2) as String
            teleInfo.imei_1 = imei_1
            teleInfo.imei_2 = imei_2
            val getPhoneTypeGemini =
                TelephonyManager::class.java.getDeclaredMethod(
                    "getPhoneTypeGemini",
                    Int::class.javaPrimitiveType
                )
            val phoneType_1 = getPhoneTypeGemini.invoke(tm, simId_1) as Int
            val phoneType_2 = getPhoneTypeGemini.invoke(tm, simId_2) as Int
            teleInfo.phoneType_1 = phoneType_1
            teleInfo.phoneType_2 = phoneType_2
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return teleInfo
    }

    /**
     * MTK Phone.
     *
     *
     * 获取 MTK 神机的双卡 IMSI、IMSI 信息
     */
    @SuppressLint("MissingPermission", "HardwareIds", "PrivateApi")
    fun getMtkTeleInfo2(context: Context): TeleInfo {
        val teleInfo = TeleInfo()
        try {
            val tm =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val phone =
                Class.forName("com.android.internal.telephony.Phone")
            val fields1 = phone.getField("GEMINI_SIM_1")
            fields1.isAccessible = true
            val simId_1 = fields1[null] as Int
            val fields2 = phone.getField("GEMINI_SIM_2")
            fields2.isAccessible = true
            val simId_2 = fields2[null] as Int
            val getDefault =
                TelephonyManager::class.java.getMethod("getDefault", Int::class.javaPrimitiveType)
            val tm1 = getDefault.invoke(tm, simId_1) as TelephonyManager
            val tm2 = getDefault.invoke(tm, simId_2) as TelephonyManager
            val imsi_1 = tm1.subscriberId
            val imsi_2 = tm2.subscriberId
            teleInfo.imsi_1 = imsi_1
            teleInfo.imsi_2 = imsi_2
            val imei_1 = tm1.deviceId
            val imei_2 = tm2.deviceId
            teleInfo.imei_1 = imei_1
            teleInfo.imei_2 = imei_2
            val phoneType_1 = tm1.phoneType
            val phoneType_2 = tm2.phoneType
            teleInfo.phoneType_1 = phoneType_1
            teleInfo.phoneType_2 = phoneType_2
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return teleInfo
    }

    /**
     * Qualcomm Phone.
     * 获取 高通 神机的双卡 IMSI、IMSI 信息
     */
    @SuppressLint("PrivateApi")
    fun getQualcommTeleInfo(context: Context): TeleInfo {
        val teleInfo = TeleInfo()
        try {
            val tm =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simTMclass =
                Class.forName("android.telephony.MSimTelephonyManager")
            // Object sim = context.getSystemService("phone_msim");
            val sim =
                context.getSystemService(Context.TELEPHONY_SERVICE)
            val simId_1 = 0
            val simId_2 = 1
            val getSubscriberId =
                simTMclass.getMethod("getSubscriberId", Int::class.javaPrimitiveType)
            val imsi_1 = getSubscriberId.invoke(sim, simId_1) as String
            val imsi_2 = getSubscriberId.invoke(sim, simId_2) as String
            teleInfo.imsi_1 = imsi_1
            teleInfo.imsi_2 = imsi_2
            val getDeviceId =
                simTMclass.getMethod("getDeviceId", Int::class.javaPrimitiveType)
            val imei_1 = getDeviceId.invoke(sim, simId_1) as String
            val imei_2 = getDeviceId.invoke(sim, simId_2) as String
            teleInfo.imei_1 = imei_1
            teleInfo.imei_2 = imei_2
            val getDataState = simTMclass.getMethod("getDataState")
            val phoneType_1 = tm.dataState
            val phoneType_2 = getDataState.invoke(sim) as Int
            teleInfo.phoneType_1 = phoneType_1
            teleInfo.phoneType_2 = phoneType_2
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return teleInfo
    }

    /**
     * Spreadtrum Phone.
     *
     *
     * 获取 展讯 神机的双卡 IMSI、IMSI 信息
     */
    @SuppressLint("MissingPermission", "HardwareIds", "PrivateApi")
    fun getSpreadtrumTeleInfo(context: Context): TeleInfo {
        val teleInfo = TeleInfo()
        try {
            val tm1 =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val imsi_1 = tm1.subscriberId
            val imei_1 = tm1.deviceId
            val phoneType_1 = tm1.phoneType
            teleInfo.imsi_1 = imsi_1
            teleInfo.imei_1 = imei_1
            teleInfo.phoneType_1 = phoneType_1
            val phoneFactory =
                Class.forName("com.android.internal.telephony.PhoneFactory")
            val getServiceName = phoneFactory.getMethod(
                "getServiceName",
                String::class.java,
                Int::class.javaPrimitiveType
            )
            getServiceName.isAccessible = true
            val tm2 =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val imsi_2 = tm2.subscriberId
            val imei_2 = tm2.deviceId
            val phoneType_2 = tm2.phoneType
            teleInfo.imsi_2 = imsi_2
            teleInfo.imei_2 = imei_2
            teleInfo.phoneType_2 = phoneType_2
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return teleInfo
    }

    /**
     * 获取 MAC 地址
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     */
    @SuppressLint("WifiManagerPotentialLeak", "HardwareIds")
    fun getMacAddress(context: Context): String {
        //wifi mac地址
        val wifi =
            context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = wifi.connectionInfo
        return info.macAddress
    }

    /**
     * 获取 开机时间
     */
    val bootTimeString: String
        get() {
            val ut = SystemClock.elapsedRealtime() / 1000
            val h = (ut / 3600).toInt()
            val m = (ut / 60 % 60).toInt()
            return "$h:$m"
        }
    /////_________________ 双卡双待系统IMEI和IMSI方案（see more on http://benson37.iteye.com/blog/1923946）
    /**
     * 双卡双待神机IMSI、IMSI、PhoneType信息
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"></uses-permission>
     */
    class TeleInfo {
        var imsi_1: String? = null
        var imsi_2: String? = null
        var imei_1: String? = null
        var imei_2: String? = null
        var phoneType_1 = 0
        var phoneType_2 = 0
        override fun toString(): String {
            return "TeleInfo{" +
                    "imsi_1='" + imsi_1 + '\'' +
                    ", imsi_2='" + imsi_2 + '\'' +
                    ", imei_1='" + imei_1 + '\'' +
                    ", imei_2='" + imei_2 + '\'' +
                    ", phoneType_1=" + phoneType_1 +
                    ", phoneType_2=" + phoneType_2 +
                    '}'
        }
    }
}