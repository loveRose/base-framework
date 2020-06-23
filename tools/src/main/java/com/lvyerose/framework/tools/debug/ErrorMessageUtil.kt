package com.lvyerose.framework.tools.debug

import android.content.Context
import com.lvyerose.framework.tools.app.MobileUtil
import com.lvyerose.framework.tools.app.VersionUtil

/**
 * desc: 错误信息反馈信息
 * author: lad
 */
object ErrorMessageUtil {

    fun printErrorMessage(
        context: Context?,
        methodName: String,
        errorMessage: String
    ): String {
        return ("""

    ############################errorMessage start ##############################
    ${context?.let { MobileUtil.printMobileInfo(it) }}${MobileUtil.printSystemInfo()}
    错误信息：$errorMessage
    方法名：$methodName
    当前app版本号：
    """.trimIndent() + context?.let {
            VersionUtil.getVersion(
                it
            )
        }
                + "\n############################errorMessage end##############################")
    }
}