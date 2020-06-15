package com.lvyerose.framework.base.general

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes

interface IBaseFragment {

    /**
     * 设置  fragment要加载的布局
     *
     * @return 返回页面布局id
     */
    @LayoutRes
    fun onContentLayoutId(): Int

    fun onArgumentAction(bundle: Bundle?)
    /**
     * onViewCreated方法即将执行完毕
     * 此时View已经加载进fragment了
     * 在此方法中开始自己的逻辑了
     *
     * @param parentView fragment布局创建时使用的布局View
     */
    fun onStartAction(parentView: View)


}