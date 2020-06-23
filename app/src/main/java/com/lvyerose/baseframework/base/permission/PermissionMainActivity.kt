package com.lvyerose.baseframework.base.permission

import android.os.Bundle
import com.lvyerose.baseframework.R
import com.lvyerose.framework.base.general.BaseActivity

class PermissionMainActivity : BaseActivity() {
    override fun setContentLayoutId() = R.layout.activity_main_permission

    override fun onStartAction(savedInstanceState: Bundle?) {
//        PermissionHelper.listenerViewCameraPermission(rxPermissions, btn_listener_permission) {
//            "权限申请成功".toast()
//        }
    }

}