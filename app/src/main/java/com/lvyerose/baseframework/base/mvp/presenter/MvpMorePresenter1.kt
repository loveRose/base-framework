package com.lvyerose.baseframework.base.mvp.presenter

import com.lvyerose.baseframework.base.mvp.MvpMoreView1
import com.lvyerose.baseframework.base.mvp.model.MvpMoreModel1
import com.lvyerose.baseframework.base.mvp.model.MvpMoreModel2
import com.lvyerose.baseframework.base.mvp.model.MvpMoreModel3
import com.lvyerose.framework.base.mvp.BasePresenter
import com.lvyerose.framework.base.mvp.IModel

class MvpMorePresenter1 : BasePresenter<MvpMoreView1, MvpMoreModel1>() {

    override fun createOtherModel(): IModel? {
        //创建model2
        return MvpMoreModel2()
    }

    override fun onAttachStartAction() {
        //创建model3 。。 4 。。 5等
        var model = MvpMoreModel3()
        addModelList(model)
    }

    fun loadPresenterData(msg: String) {
        mView?.callView1(msg)
    }

    fun loadModelData() {
        mView?.callView1(mModel?.loadModel())
    }

    fun loadOtherModelData() {
        var model: MvpMoreModel2 = otherModel as MvpMoreModel2
        mView?.callView1(model.loadModel())
    }

    fun loadModelModelData() {
        var model: MvpMoreModel3 = getModeByIndex(0) as MvpMoreModel3
        mView?.callView1(model.loadModel())
    }

}