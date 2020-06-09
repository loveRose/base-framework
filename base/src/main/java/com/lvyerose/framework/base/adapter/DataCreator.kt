package com.lvyerose.framework.base.adapter

/**
 * 数据创建者
 */
class DataCreator<T> {

    var dataAndTypes: MutableList<Pair<Int, T>> = arrayListOf()

    fun addData(type: Int, data: T) {
        dataAndTypes.add(type to data)
    }
}