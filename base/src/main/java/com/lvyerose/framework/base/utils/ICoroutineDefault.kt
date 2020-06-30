package com.lvyerose.framework.base.utils

import com.lvyerose.framework.base.exception.BusinessException
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
     *  onStart = {//流程开始逻辑}
     *  work = {//工作线程处理逻辑},
     *  onSuccess = {//处理完成之后的逻辑},
     *  onFail = {//处理失败之后的逻辑},
     *  onError = {//处理出现异常之后的逻辑}
     *  onCompleted = {//流程结束逻辑}
     * )
     */
    fun <T> executeCoroutine(
        context: CoroutineContext = EmptyCoroutineContext,
        onStart: () -> Unit = {},
        work: suspend () -> T?,
        onSuccess: (T?) -> Unit = {},
        onFail: (Int, String?) -> Unit = { Int, String -> },
        onError: (Throwable) -> Unit = {},
        onCompleted: () -> Unit = {}
    ): Job {
        return CoroutineScope(Dispatchers.Main).launch(context) {
            try {
                onStart()
                val res: T? = withContext(Dispatchers.IO) { work() }
                onSuccess(res)
            } catch (e: Exception) {
                if (e is BusinessException)
                    onFail(e.code, e.message)
                else
                    onError(e)
            } finally {
                onCompleted()
            }
        }
    }
}
