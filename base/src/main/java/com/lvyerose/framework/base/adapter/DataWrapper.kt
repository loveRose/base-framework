package com.lvyerose.framework.base.adapter

/**
 * 数据创建包裹回调
 * 主要是用来创建多类型布局的数据
 */
class DataWrapper<T> {

    var dataATypeList: MutableList<Pair<Int, T>> = arrayListOf()

    fun addData(itemType: Int, data: T) {
        dataATypeList.add(itemType to data)
    }
}