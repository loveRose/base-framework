@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.lvyerose.framework.tools.app

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils

/**
 * desc: 剪切板工具
 * author: lad
 */
object ClipBoardUtils {
    /**
     * 实现文本复制功能
     *
     * @param content 复制的文本
     */
    fun copy(content: String, context: Context) {
        if (!TextUtils.isEmpty(content)) {
            // 得到剪贴板管理器
            val cmb =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cmb.text = content.trim { it <= ' ' }
            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
            val clipData = ClipData.newPlainText(null, content)
            // 把数据集设置（复制）到剪贴板
            cmb.primaryClip = clipData
        }
    }

    /**
     * 获取系统剪贴板内容
     */
    fun getClipContent(context: Context): String {
        val manager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (manager.hasPrimaryClip() && manager.primaryClip.itemCount > 0) {
            val addedText = manager.primaryClip.getItemAt(0).text
            //                String addedTextString = String.valueOf(addedText);
            if (!TextUtils.isEmpty(addedText)) {
                return addedText.toString()
            }
        }
        return ""
    }

    /**
     * 清空剪贴板内容
     */
    fun clearClipboard(context: Context) {
        val manager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        try {
            manager.primaryClip = manager.primaryClip
            manager.text = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}