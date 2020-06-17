package com.lvyerose.framework.tools

import android.util.Log

/**
 * desc: 日志工具类
 * author: lad
 * 这个工具类主要在三处地方做了封装。
 * 第一是加入BuildConfig.DEBUG判断，这样发布时就不会打印log，如果不了解BuildConfig，可以参考这篇文章Android BuildConfig.DEBUG的妙用
 * 第二是log工具类都常见的，预设置TAG，这样不用每次都传两个参数
 * 第三，通过StackTraceElement获取当前打印日志的类名和方法名，这个用来代替我们平时手写的TAG值。StackTrace用栈的形式保存了方法的调用信息。
 */
object MyLogger {
    private const val TAG = "DSBBM"

    /**
     * Send a VERBOSE log message.
     *
     * @param msg The message you would like logged.
     */
    fun v(msg: String?) {
        if (BuildConfig.DEBUG) Log.v(TAG, buildMessage(msg))
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    fun v(msg: String?, tr: Throwable?) {
        if (BuildConfig.DEBUG) Log.v(TAG, buildMessage(msg), tr)
    }

    /**
     * Send a DEBUG log message.
     *
     * @param msg
     */
    fun d(msg: String?) {
        if (BuildConfig.DEBUG) Log.d(TAG, buildMessage(msg))
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    fun d(msg: String?, tr: Throwable?) {
        if (BuildConfig.DEBUG) Log.d(TAG, buildMessage(msg), tr)
    }

    /**
     * Send an INFO log message.
     *
     * @param msg The message you would like logged.
     */
    fun i(msg: String?) {
        if (BuildConfig.DEBUG) Log.i(TAG, buildMessage(msg))
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    fun i(msg: String?, tr: Throwable?) {
        if (BuildConfig.DEBUG) Log.i(TAG, buildMessage(msg), tr)
    }

    /**
     * Send an ERROR log message.
     *
     * @param msg The message you would like logged.
     */
    fun e(msg: String?) {
        if (BuildConfig.DEBUG) Log.e(TAG, buildMessage(msg))
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    fun e(msg: String?, tr: Throwable?) {
        if (BuildConfig.DEBUG) Log.e(TAG, buildMessage(msg), tr)
    }

    /**
     * Send a WARN log message
     *
     * @param msg The message you would like logged.
     */
    fun w(msg: String?) {
        if (BuildConfig.DEBUG) Log.w(TAG, buildMessage(msg))
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    fun w(msg: String?, thr: Throwable?) {
        if (BuildConfig.DEBUG) Log.w(TAG, buildMessage(msg), thr)
    }

    /**
     * Send an empty WARN log message and log the exception.
     *
     * @param thr An exception to log
     */
    fun w(thr: Throwable?) {
        if (BuildConfig.DEBUG) Log.w(TAG, buildMessage(""), thr)
    }

    private fun buildMessage(msg: String?): String {

        //通过StackTraceElement获取当前打印日志的类名和方法名，这个用来代替我们平时手写的TAG值。
        // StackTrace用栈的形式保存了方法的调用信息
        val caller =
            Throwable().fillInStackTrace().stackTrace[2]
        return StringBuilder().append(caller.className).append(".")
            .append(caller.methodName).append("(): ").append(msg).toString()
    }
}