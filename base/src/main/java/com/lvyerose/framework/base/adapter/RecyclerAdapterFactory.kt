package com.lvyerose.framework.base.adapter

object RecyclerAdapterFactory {

    fun <T> createRecyclerAdapter(body: BaseRecyclerAdapter<T>.() -> Unit): BaseRecyclerAdapter<T> {
        val adapter = object : BaseRecyclerAdapter<T>() {}
        var resultAdapter = adapter as BaseRecyclerAdapter<T>
        resultAdapter.body()
        return resultAdapter
    }

    inline fun <reified T> createRecyclerAdapter(
        layoutId: Int,
        body: BaseRecyclerAdapter<T>.() -> Unit
    ): BaseRecyclerAdapter<T> {
        val adapter = object : BaseRecyclerAdapter<T>() {}
        var resultAdapter = adapter as BaseRecyclerAdapter<T>
        resultAdapter.layout {
            layoutId
        }
        resultAdapter.body()
        return resultAdapter
    }
}