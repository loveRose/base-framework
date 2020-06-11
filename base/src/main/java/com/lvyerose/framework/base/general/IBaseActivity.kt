package com.lvyerose.framework.base.general

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes

/**
 * 基础Activity抽象接口类
 *
 */
interface IBaseActivity {

    /**
     * 设置  setContentView() 的布局
     *
     * @return 返回页面布局id
     */
    @LayoutRes
    fun setContentLayoutId(): Int

    /**
     * 刷新页面时候的回调
     * 如果可用的话
     */
    fun onRefresh(argument: Any, vararg arguments: Any)

    /**
     * onCreate方法即将执行完毕
     * 在此方法中开始自己的逻辑
     *
     * @param savedInstanceState 原onCreate里面的状态保存参数
     */
    fun startAction(savedInstanceState: Bundle?)

    /**
     * 在调用 setContentView() 方法之前
     */
    fun onBeforeSetView()

    /**
     * 在调用 setContentView() 方之后
     */
    fun onAfterSetView()

    /**
     * onNewIntent 方法在创建Activity但无需重新创建实例的情况下调用
     *
     * @param intent
     */
    fun newIntent(intent: Intent)

    /**
     * 保存状态回调
     *
     * @param outState
     */
    fun saveInstanceState(outState: Bundle)

    /**
     * 恢复状态回调
     *
     * @param outState
     */
    fun restoreInstanceState(outState: Bundle)

    /**
     * 虚拟键 返回按钮点击回调
     */
    fun onKeyBack(): Boolean

    /**
     * 虚拟键 Home按钮点击回调
     */
    fun onKeyHome(): Boolean

    /**
     * 虚拟键 菜单按钮点击回调
     */
    fun onKeyMenu(): Boolean
}