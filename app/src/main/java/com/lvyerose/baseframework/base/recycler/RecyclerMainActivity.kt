package com.lvyerose.baseframework.base.recycler

import android.content.Intent
import android.os.Bundle
import com.lvyerose.baseframework.R
import com.lvyerose.framework.base.general.BaseActivity
import kotlinx.android.synthetic.main.activity_recycler_main.*

/**
 * 列表适配器测试首页
 */
class RecyclerMainActivity : BaseActivity() {

    override fun setContentLayoutId() = R.layout.activity_recycler_main

    override fun onStartAction(savedInstanceState: Bundle?) {
        btn_simple_layout_1.setOnClickListener {
            startActivity(Intent(this, RecyclerSimpleOneActivity::class.java))
        }
        btn_multiple_layout_1.setOnClickListener {
            startActivity(Intent(this, RecyclerMultiple1Activity::class.java))
        }
        btn_multiple_layout_2.setOnClickListener {
            startActivity(Intent(this, RecyclerMultiple2Activity::class.java))
        }
        btn_layout_demo.setOnClickListener {
            startActivity(Intent(this, RecyclerCompleteActivity::class.java))
        }
    }
}