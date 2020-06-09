package com.lvyerose.framework.base.adapter

class LayoutCreator {

    private var layoutMap: MutableMap<Int, Int> = mutableMapOf()
    /**
     * 添加单个布局，以layoutId为type
     */
    fun layout(layoutId: () -> Int) {
        var id = layoutId()
        var type = id
        layoutMap[type] = id
    }

    /**
     * 添加单个布局，自定义Type
     */
    fun layout(type: Int, layoutId: Int) {
        layoutMap[type] = layoutId
    }

    fun getValue(): MutableMap<Int, Int> {
        return layoutMap
    }
}