package com.lvyerose.framework.base.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lvyerose.framework.base.utils.ICoroutineDefault
import com.lvyerose.framework.base.utils.RxLifecycleManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import org.greenrobot.eventbus.EventBus
import java.io.Serializable


abstract class BaseFragment : Fragment(), IBaseFragment, CoroutineScope by MainScope(), ICoroutineDefault {
    protected val simpleKeyValue by lazy {
        "simple_value_key"
    }

    protected var parentView: View? = null
    var rxLifecycleManager: RxLifecycleManager? = RxLifecycleManager()

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onArgumentAction(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView = inflater.inflate(onContentLayoutId(), container, false)
        return parentView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            //可见状态
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useEventBus())
            EventBus.getDefault().register(this)
        onStartAction(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        rxLifecycleManager!!.clear()
        rxLifecycleManager = null
        if (useEventBus()) EventBus.getDefault().unregister(this)
        cancel()
    }

    override fun onStartAction(parentView: View) {
        //不强制子类重写
    }

    /**
     * @param bundle 携带参数的Bundle 如果不为空，如果曾经确实传递了数据过来，那么一定在它里面
     */
    override fun onArgumentAction(bundle: Bundle?) {
        //不强制子类重写
    }

    /**
     * 方法重载 每次请仅调用其中一个，否则将覆盖上一个值
     * @param key 需要传值的key
     * @param value key对应的数据
     */
    fun setArgumentData(key: String, value: Any) {
        var bundle = Bundle()
        when (value) {
            value is String -> bundle.putString(key, value as String)
            value is Int -> bundle.putInt(key, value as Int)
            value is Float -> bundle.putFloat(key, value as Float)
            value is Double -> bundle.putDouble(key, value as Double)
            value is Boolean -> bundle.putBoolean(key, value as Boolean)
            value is Serializable -> bundle.putSerializable(key, value as Serializable)
        }
        arguments = bundle
    }

    /**
     * 方法重载 每次请仅调用其中一个，否则将覆盖上一个值
     * @param bundle 需要传值的argument
     */
    fun setArgumentData(bundle: Bundle) {
        arguments = bundle
    }

    /**
     * 方法重载 每次请仅调用其中一个，否则将覆盖上一个值
     * @param simpleValue String类型的值
     */
    fun setArgumentData(simpleValue: String) {
        var bundle = Bundle()
        bundle.putString(simpleKeyValue, simpleValue)
        arguments = bundle
    }

    fun Any.toast(duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }
}
