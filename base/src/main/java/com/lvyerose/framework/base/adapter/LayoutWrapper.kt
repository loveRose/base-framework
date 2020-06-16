package com.lvyerose.framework.base.adapter

/**
 * 布局创造包裹回调
 * 主要是用来创建多类型布局
 */
class LayoutWrapper {

    private var layoutMap: MutableMap<Int, Int> = mutableMapOf()

    /**
     * 创建一个资源ID为布局类型的布局
     * @param layoutIdRes 布局资源ID
     */
    fun layout(layoutIdRes: () -> Int) {
        var id = layoutIdRes()
        var itemType = id
        layoutMap[itemType] = id
    }

    /**
     * 创建一个自定义itemType的布局
     * @param itemType 布局的类型
     * @param layoutIdRes 布局的资源ID
     */
    fun layout(itemType: Int, layoutIdRes: Int) {
        layoutMap[itemType] = layoutIdRes
    }

    fun getValue(): MutableMap<Int, Int> {
        return layoutMap
    }
}