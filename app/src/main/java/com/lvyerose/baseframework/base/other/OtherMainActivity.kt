package com.lvyerose.baseframework.base.other

import android.content.Intent
import android.os.Bundle
import com.lvyerose.baseframework.R
import com.lvyerose.framework.base.general.BaseActivity
import kotlinx.android.synthetic.main.activity_other_main.*

class OtherMainActivity : BaseActivity() {

    override fun setContentLayoutId() = R.layout.activity_other_main

    override fun onStartAction(savedInstanceState: Bundle?) {
        btn_login_test.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
