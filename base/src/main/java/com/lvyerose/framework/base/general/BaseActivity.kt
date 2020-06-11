package com.lvyerose.framework.base.general

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lvyerose.framework.base.utils.RxLifecycleManager

abstract class BaseActivity : AppCompatActivity(), IBaseActivity {

    /** 当前界面 Context 对象 */
    protected lateinit var mContext: AppCompatActivity
    var rxLifecycleManager: RxLifecycleManager? = RxLifecycleManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        onBeforeSetView()
        setContentView(setContentLayoutId())
        onAfterSetView()
        startAction(savedInstanceState)
    }


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
    }

    fun Any.toast(duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(this@BaseActivity, this.toString(), duration).apply { show() }
    }
}