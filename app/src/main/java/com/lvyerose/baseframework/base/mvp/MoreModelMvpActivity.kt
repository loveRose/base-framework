package com.lvyerose.baseframework.base.mvp

import android.os.Bundle
import com.lvyerose.baseframework.R
import com.lvyerose.baseframework.base.mvp.presenter.MvpMorePresenter1
import com.lvyerose.framework.base.mvp.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_more_model_mvp.*

class MoreModelMvpActivity : BaseMvpActivity<MvpMoreView1, MvpMorePresenter1>(), MvpMoreView1 {

    override fun setContentLayoutId() = R.layout.activity_more_model_mvp

    override fun onStartAction(savedInstanceState: Bundle?) {

        tv_test_load_presenter1.setOnClickListener {
            mPresenter?.loadPresenterData("from presenter1")
        }
        tv_test_load_model_1.setOnClickListener {
            mPresenter?.loadModelData()
        }
        tv_test_load_model_2.setOnClickListener {
            mPresenter?.loadOtherModelData()
        }
        tv_test_load_model_3.setOnClickListener {
            mPresenter?.loadModelModelData()
        }
    }

    override fun callView1(msg: String?) {
        tv_test_result_show.text = tv_test_result_show.text.toString().trim() + "\n" + msg
    }

    override fun startPresenter() {
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }
}
