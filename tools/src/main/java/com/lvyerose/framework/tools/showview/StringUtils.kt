package com.lvyerose.framework.tools.showview

import android.graphics.Color
import android.text.TextUtils
import java.math.BigDecimal
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

/**
 * desc: 字符串处理工具
 * author: lad
 */
object StringUtils {
    /**
     * @param str 将换行符替换为空格
     * @return
     */
    fun replaceWithBlank(str: String?): String {
        return if (!TextUtils.isEmpty(str)) {
            val p = Pattern.compile("\n")
            val m = p.matcher(str)
            m.replaceAll(" ")
        } else {
            ""
        }
    }

    /**
     * 判断String的文字的个数
     *
     * @param s
     * @return
     */
    fun getLength(s: String): Double {
        var valueLength = 0.0
        for (element in s) {
            valueLength += if (isEmojiCharacter(element)) {
                0.5
            } else {
                1.0
            }
        }
        //进位取整
        return Math.ceil(valueLength)
    }

    /**
     * 输入过滤换行符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    @Throws(PatternSyntaxException::class)
    fun stringFilter(str: String?): String {
        val regEx = "[/:*?<>|\"\n\t]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(str)
        return m.replaceAll("")
    }

    /**
     * 判断是否含有emijo表情
     *
     * @param str
     * @return
     */
    fun isContainsEmoji(str: String): Boolean {
        val len = str.length
        for (i in 0 until len) {
            if (isEmojiCharacter(str[i])) {
                return true
            }
        }
        return false
    }

    /**
     * 是否有表情
     *
     * @param codePoint
     * @return
     */
    private fun isEmojiCharacter(codePoint: Char): Boolean {
        return !(codePoint.toInt() == 0x0 ||
                codePoint.toInt() == 0x9 ||
                codePoint.toInt() == 0xA ||
                codePoint.toInt() == 0xD ||
                codePoint.toInt() in 0x20..0xD7FF ||
                codePoint.toInt() in 0xE000..0xFFFD ||
                codePoint.toInt() in 0x10000..0x10FFFF)
    }

    /**
     * 色值字符串是否正确
     *
     * @param colorStr
     * @return
     */
    fun isColorTure(colorStr: String?): Int {
        var color = 0
        if (!TextUtils.isEmpty(colorStr)) {
            color = try {
                Color.parseColor(colorStr)
            } catch (e: IllegalArgumentException) {
                Color.parseColor("#000000")
            }
        }
        return color
    }

    fun intChange2Str(number: Int): String {
        val str: String
        str = when {
            number <= 0 -> {
                "0"
            }
            number < 10000 -> {
                number.toString() + ""
            }
            else -> {
                val d = number.toDouble()
                val num = d / 10000 //1.将数字转换成以万为单位的数字
                val b = BigDecimal(num)
                val f1: Double = b.setScale(1, BigDecimal.ROUND_HALF_UP).toDouble() //2.转换后的数字四舍五入保留小数点后一位;
                f1.toString() + "w"
            }
        }
        return str
    }

    var regEx = "[\u4e00-\u9fa5]" // 中文范围

    /**
     * 格式化字符串
     *
     * @param string   原始输入字符串
     * @param maxCount 最大字符限制，中文算作2个字符，其他都算1个字符
     * @return
     */
    fun formatText(string: String?, maxCount: Int): String? {
        var string = string
        if ((string == null || string.isEmpty())
            && getChCount(string) > maxCount
        ) {
            string =
                subStrByLen(string, maxCount - 1)
        }
        return string
    }

    /**
     * 截取字符串，超出最大字数截断并显示"..."
     *
     * @param str    原始字符串
     * @param length 最大字数限制（以最大字数限制7个为例，当含中文时，length应设为2*7，不含中文时设为7）
     * @return 处理后的字符串
     */
    private fun subStrByLen(str: String?, length: Int): String {
        if (str == null || str.isEmpty()) {
            return ""
        }
        val chCnt = getStrLen(str)
        // 超出进行截断处理
        if (chCnt > length) {
            var cur = 0
            var cnt = 0
            val sb = StringBuilder()
            while (cnt <= length && cur < str.length) {
                val nextChar = str[cur]
                if (isChCharacter(nextChar.toString())) {
                    cnt += 2
                } else {
                    cnt++
                }
                if (cnt <= length) {
                    sb.append(nextChar)
                } else {
//                    return sb.toString() + "...";
                    return sb.toString()
                }
                cur++
            }
            //            return sb.toString() + "...";
            return sb.toString()
        }
        // 未超出直接返回
        return str
    }

    /**
     * 获取字符串中的中文字数
     */
    private fun getChCount(str: String?): Int {
        var cnt = 0
        val pattern =
            Pattern.compile(regEx)
        val matcher = pattern.matcher(str)
        while (matcher.find()) {
            cnt++
        }
        return cnt
    }

    /**
     * 判断字符是不是中文
     */
    private fun isChCharacter(str: String?): Boolean {
        if (str == null || str.isEmpty()) {
            return false
        }
        return if (str.length > 1) {
            false
        } else Pattern.matches(
            regEx,
            str
        )
    }

    /**
     * 获取字符长度，中文算作2个字符，其他都算1个字符
     */
    private fun getStrLen(str: String?): Int {
        return if (str == null || str.isEmpty()) {
            0
        } else str.length + getChCount(str)
    }
}