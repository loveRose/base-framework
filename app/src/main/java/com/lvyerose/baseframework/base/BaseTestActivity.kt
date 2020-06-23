package com.lvyerose.baseframework.base

import android.content.Intent
import android.os.Bundle
import com.lvyerose.baseframework.R
import com.lvyerose.baseframework.base.mvp.MvpMainActivity
import com.lvyerose.baseframework.base.other.OtherMainActivity
import com.lvyerose.baseframework.base.permission.PermissionMainActivity
import com.lvyerose.baseframework.base.recycler.RecyclerMainActivity
import com.lvyerose.framework.base.general.BaseActivity
import kotlinx.android.synthetic.main.activity_base_test.*

/**
 * 基础模块测试首页
 */
class BaseTestActivity : BaseActivity() {

    override fun setContentLayoutId() = R.layout.activity_base_test

    override fun onStartAction(savedInstanceState: Bundle?) {
        btn_test_recycler.setOnClickListener { startActivity(Intent(this, RecyclerMainActivity::class.java)) }
        btn_test_mvp.setOnClickListener { startActivity(Intent(this, MvpMainActivity::class.java)) }
        btn_permission_test.setOnClickListener { startActivity(Intent(this, PermissionMainActivity::class.java)) }
        btn_test_other.setOnClickListener { startActivity(Intent(this, OtherMainActivity::class.java)) }
    }
}
