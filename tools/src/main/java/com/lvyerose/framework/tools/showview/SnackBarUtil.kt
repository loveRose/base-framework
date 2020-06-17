package com.lvyerose.framework.tools.showview
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.lvyerose.framework.tools.R

/**
 * desc: SnackBar的工具类
 * author: lad
 */
object SnackBarUtil {
    private const val Success = 1
    private const val Error = 2
    private const val Warning = 3
    private var red = -0xbbcca
    private var blue = -0xde6a0d
    private var orange = -0x3ef9

    /**
     * 短显示SnackBar，自定义颜色
     *
     */
    fun shortSnackBar(
        view: View?,
        message: String?,
        messageColor: Int,
        gravity: Int,
        backgroundColor: Int
    ): Snackbar {
        val snackBar = Snackbar.make(view!!, message!!, Snackbar.LENGTH_SHORT)
        setSnackBarColor(
            snackBar,
            messageColor,
            gravity,
            backgroundColor
        )
        return snackBar
    }

    /**
     * 长显示SnackBar，自定义颜色
     *
     */
    fun longSnackBar(
        view: View?,
        message: String?,
        messageColor: Int,
        gravity: Int,
        backgroundColor: Int
    ): Snackbar {
        val snackBar = Snackbar.make(view!!, message!!, Snackbar.LENGTH_LONG)
        setSnackBarColor(
            snackBar,
            messageColor,
            gravity,
            backgroundColor
        )
        return snackBar
    }

    /**
     * 自定义时常显示SnackBar，自定义颜色
     *
     */
    fun indefiniteSnackBar(
        view: View?,
        message: String?,
        duration: Int,
        messageColor: Int,
        gravity: Int,
        backgroundColor: Int
    ): Snackbar {
        val snackBar =
            Snackbar.make(view!!, message!!, Snackbar.LENGTH_INDEFINITE).setDuration(duration)
        setSnackBarColor(
            snackBar,
            messageColor,
            gravity,
            backgroundColor
        )
        return snackBar
    }

    /**
     * 短显示SnackBar，可选预设类型
     *
     */
    fun shortSnackBar(view: View?, message: String?, type: Int): Snackbar {
        val snackBar = Snackbar.make(view!!, message!!, Snackbar.LENGTH_SHORT)
        switchType(
            snackBar,
            type
        )
        return snackBar
    }

    /**
     * 长显示SnackBar，可选预设类型
     *
     */
    fun longSnackBar(view: View?, message: String?, type: Int): Snackbar {
        val snackBar = Snackbar.make(view!!, message!!, Snackbar.LENGTH_LONG)
        switchType(
            snackBar,
            type
        )
        return snackBar
    }

    /**
     * 自定义时常显示SnackBar，可选预设类型B
     *
     */
    fun indefiniteSnackBar(
        view: View?,
        message: String?,
        duration: Int,
        type: Int
    ): Snackbar {
        val snackBar =
            Snackbar.make(view!!, message!!, Snackbar.LENGTH_INDEFINITE).setDuration(duration)
        switchType(
            snackBar,
            type
        )
        return snackBar
    }

    //选择预设类型
    private fun switchType(snackBar: Snackbar, type: Int) {
        when (type) {
            Success -> setSnackBarColor(
                snackBar,
                blue
            )
            Error -> setSnackBarColor(
                snackBar,
                red
            )
            Warning -> setSnackBarColor(
                snackBar,
                orange
            )
        }
    }

    /**
     * 设置SnackBar背景颜色
     *
     */
    private fun setSnackBarColor(snackbar: Snackbar, backgroundColor: Int) {
        val view = snackbar.view
        view.setBackgroundColor(backgroundColor)
    }

    /**
     * 设置SnackBar文字和背景颜色
     *
     */
    private fun setSnackBarColor(
        snackBar: Snackbar,
        messageColor: Int,
        gravity: Int,
        backgroundColor: Int
    ) {
        val view = snackBar.view
        view.setBackgroundColor(backgroundColor)
        (view.findViewById<View>(R.id.snackbar_text) as TextView).setTextColor(
            messageColor
        )
        (view.findViewById<View>(R.id.snackbar_text) as TextView).gravity = gravity
    }

    /**
     * 向SnackBar中添加view
     *
     * @param index    新加布局在SnackBar中的位置
     */
    fun snackBarAddView(snackBar: Snackbar, layoutId: Int, index: Int) {
        val snackBarView = snackBar.view
        val snackBarLayout = snackBarView as SnackbarLayout
        val addView =
            LayoutInflater.from(snackBarView.getContext()).inflate(layoutId, null)
        val p = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        p.gravity = Gravity.CENTER_VERTICAL
        snackBarLayout.addView(addView, index, p)
    }
}