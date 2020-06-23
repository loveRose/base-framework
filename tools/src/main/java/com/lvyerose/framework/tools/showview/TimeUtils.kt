package com.lvyerose.framework.tools.showview

/**
 * desc: 时间处理工具
 * author: lad
 */
object TimeUtils {
    fun formatTimeS(seconds: Long): String {
        var temp = 0
        val sb = StringBuffer()
        if (seconds > 3600) {
            temp = (seconds / 3600).toInt()
            sb.append(if (seconds / 3600 < 10) "0$temp:" else "$temp:")
            temp = (seconds % 3600 / 60).toInt()
            changeSeconds(seconds, temp, sb)
        } else {
            temp = (seconds % 3600 / 60).toInt()
            changeSeconds(seconds, temp, sb)
        }
        return sb.toString()
    }

    private fun changeSeconds(
        seconds: Long,
        temp: Int,
        sb: StringBuffer
    ) {
        var temp = temp
        sb.append(if (temp < 10) "0$temp:" else "$temp:")
        temp = (seconds % 3600 % 60).toInt()
        sb.append(if (temp < 10) "0$temp" else "" + temp)
    }
}