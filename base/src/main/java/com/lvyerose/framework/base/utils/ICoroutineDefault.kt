package com.lvyerose.framework.base.utils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 简易封装的Coroutine协程处理
 *
 */
interface ICoroutineDefault {
    /**
     * 在Activity或Fragment中直接使用下面方法，方法中只有work是必须的，其余都可按实际需求场景进行删减
     * ******** ******** ******** ******** *******
     * executeCoroutine
     * (
     *  this.coroutineContext,
     *  work = {//工作线程处理逻辑},
     *  onSuccess = {//处理完成之后的逻辑},
     *  onFail = {//处理失败之后的逻辑},
     *  onError = {//处理出现异常之后的逻辑}
     * )
     */
    fun <T> executeCoroutine(
        context: CoroutineContext = EmptyCoroutineContext,
        work: suspend () -> T?,
        onSuccess: (T) -> Unit = {},
        onFail: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ): Job {
        return CoroutineScope(Dispatchers.Main).launch(context) {
            try {
                val res: T? = withContext(Dispatchers.IO) { work() }
                res?.let {
                    onSuccess(it)
                }
                res ?: onFail()
            } catch (e: Exception) {
                e.printStackTrace()
                onError(e)
            }
        }
    }
}
