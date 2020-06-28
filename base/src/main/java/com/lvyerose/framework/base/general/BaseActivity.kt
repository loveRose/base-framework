package com.lvyerose.framework.base.general

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lvyerose.framework.base.R
import com.lvyerose.framework.base.constant.TransitionMode
import com.lvyerose.framework.base.utils.RxLifecycleManager
import kotlinx.coroutines.*

abstract class BaseActivity : AppCompatActivity(), IBaseActivity, CoroutineScope by MainScope() {
    /** 当前界面 Context 对象 */
    protected open lateinit var mContext: AppCompatActivity
    protected var rxLifecycleManager: RxLifecycleManager? = RxLifecycleManager()
    private var mTransitionMode = TransitionMode.RIGHT //默认进场方式 右


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setSystemTransitionMode(getOverridePendingTransitionMode(mTransitionMode))
        requestedOrientation = defaultOrientation()
        onBeforeSetView()
        setContentView(setContentLayoutId())
        onAfterSetView()
        onStartAction(savedInstanceState)
    }

    private fun setSystemTransitionMode(transitionMode: TransitionMode) {
        //Activity默认动画为右进右出
        when (transitionMode) {
            TransitionMode.LEFT -> overridePendingTransition(R.anim.left_in, R.anim.left_out)
            TransitionMode.RIGHT -> overridePendingTransition(R.anim.enter_trans, R.anim.exit_scale)
            TransitionMode.TOP -> overridePendingTransition(R.anim.top_in, R.anim.top_out)
            TransitionMode.BOTTOM -> overridePendingTransition(R.anim.bottom_in, 0)
            TransitionMode.SCALE -> overridePendingTransition(R.anim.scale_in, R.anim.scale_out)
            TransitionMode.FADE -> overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            TransitionMode.ZOOM -> overridePendingTransition(R.anim.zoomin, R.anim.zoomout)
        }
    }

    //默认强制竖屏 如果需要其他的设置 重写此方法即可
    override fun defaultOrientation() = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    override fun onSaveInstanceState(outState: Bundle) {
        //保存状态 用于恢复
        saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //恢复状态
        restoreInstanceState(savedInstanceState)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        //创建时不同启动模式导致生命周期不创建 而是调用该方法
        newIntent(intent)
        GlobalScope
    }

    override fun newIntent(intent: Intent) {
        //不强制子类重写方法，供子类选择性重写
    }

    override fun saveInstanceState(outState: Bundle) {
        //不强制子类重写方法，供子类选择性重写
    }

    override fun restoreInstanceState(outState: Bundle) {
        //不强制子类重写方法，供子类选择性重写
    }

    override fun onRefresh(argument: Any, vararg arguments: Any) {
        //不强制子类重写方法，供子类选择性重写
    }

    override fun onBeforeSetView() {
        //不强制子类重写方法，供子类选择性重写
    }

    override fun onAfterSetView() {
        //不强制子类重写方法，供子类选择性重写
    }

    override fun onKeyBack(): Boolean {
        finish()
        return true
    }

    override fun onKeyHome(): Boolean {
        //不强制子类重写方法，供子类选择性重写
        return true
    }

    override fun onKeyMenu(): Boolean {
        //不强制子类重写方法，供子类选择性重写
        return true
    }

    /**
     * 默认提供进入方式为右侧进出动画
     */
    override fun getOverridePendingTransitionMode(transitionMode: TransitionMode): TransitionMode {
        mTransitionMode = transitionMode
        return mTransitionMode
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_BACK -> onKeyBack()
            KeyEvent.KEYCODE_HOME -> onKeyHome()
            KeyEvent.KEYCODE_MENU -> onKeyMenu()
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        rxLifecycleManager!!.clear()
        rxLifecycleManager = null
        //取消Kotlin的协程
        launch {

        }
        cancel()
    }

    suspend fun cancelPer(actionTask: Job?) {
        actionTask?.cancelAndJoin()

    }

    fun Any.toast(duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(this@BaseActivity, this.toString(), duration).apply { show() }
    }

}