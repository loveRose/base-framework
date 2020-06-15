package com.lvyerose.baseframework.base.mvp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.lvyerose.baseframework.R
import com.lvyerose.baseframework.base.mvp.presenter.MvpMainPresenter
import com.lvyerose.framework.base.mvp.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_mvp_main.*

class MvpMainActivity : BaseMvpActivity<MvpMainView, MvpMainPresenter>(), MvpMainView {

    override fun setContentLayoutId() = R.layout.activity_mvp_main

    override fun onStartAction(savedInstanceState: Bundle?) {
        btn_send_network.setOnClickListener {
            mPresenter?.sendNetwork("success")
        }
        btn_send_error_network.setOnClickListener {
            mPresenter?.sendErrorNetwork("error")
        }
        btn_send_close_network.setOnClickListener {
            mPresenter?.sendCloseNetwork("close")
            finish()
        }
        btn_more_model.setOnClickListener {
            startActivity(Intent(this, MoreModelMvpActivity::class.java))
        }
        btn_fragment_test.setOnClickListener {
            startActivity(Intent(this, SimpleFragmentActivity::class.java))
        }
    }

    override fun showHint(msg: String) {
        msg.toast()
    }

    override fun startPresenter() {
        Log.e("mvp_test", "开始执行")
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
    }
}
