package com.lvyerose.framework.base.exception

class BusinessException constructor(var code: Int, override val message: String?) : RuntimeException() {
}