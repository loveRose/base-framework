package com.lvyerose.framework.tools.showview

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * desc: 打开或关闭软键盘
 * author: lad
 */
object KeyBoardUtils {
    /**
     * 打卡软键盘
     *
     * @param mEditText
     * 输入框
     * @param mContext
     * 上下文
     */
    fun openKeyBord(mEditText: EditText?, mContext: Context) {
        val imm = mContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     * 输入框
     * @param mContext
     * 上下文
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }
}