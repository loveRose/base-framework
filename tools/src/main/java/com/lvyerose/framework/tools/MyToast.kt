package com.lvyerose.framework.tools

import android.content.Context
import android.view.Gravity
import android.widget.Toast

/**
 * desc: 统一管理类
 * author: lad
 */
class MyToast private constructor() {
    companion object {
        private var isShow = true

        /**
         * 屏幕中间位置显示短时间Toast
         *
         * @param context
         * @param msg
         */
        fun toastShortCenter(context: Context?, msg: String?) {
            if (isShow) {
                if (context != null) {
                    val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            }
        }

        /**
         * 屏幕中心位置长时间显示Toast
         *
         * @param context
         * @param message
         */
        fun toastLongCenter(
            context: Context?,
            message: String?
        ) {
            if (isShow) {
                if (context != null) {
                    val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            }
        }

        /**
         * 自定义显示Toast时间
         *
         * @param context
         * @param message
         * @param duration
         */
        fun toastShow(
            context: Context?,
            message: String?,
            duration: Int
        ) {
            if (isShow) Toast.makeText(context, message, duration).show()
        }
    }

    init {
        //不能被实例化
        throw UnsupportedOperationException("cannot be instantiated")
    }
}