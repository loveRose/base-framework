package com.lvyerose.framework.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerViewHolder>() {
    lateinit var layoutInflater: LayoutInflater
    private var mLayout: Int = 0
    //处理多类型布局
    private var mLayoutIds: MutableMap<Int, Int> = mutableMapOf()
    //处理多类型Type
    private var mDataList: MutableList<RecyclerItemData<T>> = arrayListOf()

    private var mTypes: ArrayList<Int> = ArrayList()
    //
    private var mItemDataList: MutableList<RecyclerItemData<T>> = arrayListOf()
    private var mOriginData: List<T?> = arrayListOf()
    //存储第一次设置的规则
    private var defaultRulesFunction: (DataCreator<T>.(T) -> Unit)? = null

    private var mDataWithTypes: ArrayList<Pair<Int, T?>> = arrayListOf()
    //保存拦截的类型和处理函数
    private var interceptViews: MutableMap<Int, Any> = mutableMapOf()

    private var mOnItemClickListener: ((position: Int, view: View) -> Unit)? = null

    private var mHeaderView: WeakReference<View>? = null

    private var mHeaderLayoutId: Int? = null

    private var mFooterView: WeakReference<View>? = null

    private var mFooterLayoutId: Int? = null

    private lateinit var mBind: ((type: Int, vh: RecyclerViewHolder, data: T) -> Unit?)

    private var mBindInterceptView: ((type: Int, vh: RecyclerViewHolder, data: T?, backupData: Any?) -> Unit?)? = null

    private lateinit var mBindHeader: (view: View) -> Unit

    private lateinit var mBindFooter: (view: View) -> Unit

    /**
     * 添加单个布局
     */
    fun layout(layoutId: () -> Int) {
        mLayout = layoutId()
        mLayoutIds[mLayout] = mLayout
    }

    /**
     * 添加多个布局
     */
    fun layout(layoutIds: List<Int>) {
        layoutIds.forEach {
            mLayoutIds[it] = it
        }
        if (mLayoutIds.isEmpty()) {
            var firstKey = mLayoutIds.keys.first()
            mLayout = mLayoutIds[firstKey]!!
        }
    }

    /**
     * 通过布局创造起进行添加多个布局
     */
    fun layoutMultiple(initLayout: LayoutCreator.() -> Unit) {
        val creator = LayoutCreator()
        creator.initLayout()
        var firstKey = creator.getValue().keys.first()
        mLayout = creator.getValue()[firstKey]!!
        mLayoutIds = creator.getValue()
    }


    /**
     * 适配数据
     */
    fun data(dataList: List<T>, initData: DataCreator<T>.(T) -> Unit) {
        clear()
        defaultRulesFunction = initData
        mOriginData = dataList
        var creator = DataCreator<T>()
        dataList.forEach {
            creator.initData(it)
        }

        creator.dataAndTypes.forEach { (type, data) ->
            mDataList.add(RecyclerItemData(type = type, data = data))
        }

        mDataList.forEach {
            if (it.type == null) {
                it.type = mLayout//最后一个
            }
            mTypes.add(it.type!!)
        }
    }

    /**
     * 仅更新数据，单布局使用此方法，如果和类型数据不设置，默认用最后一个设置的layout
     */
    fun data(dataList: () -> List<*>) {
        clear()
        var tempdatas = dataList() as ArrayList<T?>
        mOriginData = tempdatas
        tempdatas.forEach {
            mDataList.add(RecyclerItemData(data = it, type = mLayout))
            mTypes.add(mLayout)
        }

    }

    /**
     * 新增单个数据在尾部
     * notice 多布局调用不起作用
     */
    fun addData(data: T) {
        if (!isMultipleLayoutMode()) {
            mDataList.add(RecyclerItemData(data = data, type = mLayout))
            mTypes.add(mLayout)
        }
    }

    /**
     * 新增MultipleType数据在尾部
     */
    fun addData(type: Int, data: T) {
        mDataList.add(RecyclerItemData(data = data, type = type))
        mTypes.add(type)
    }

    /**
     * 指定位置添加数据
     */
    fun addData(index: Int, type: Int, data: T) {
        mDataList.add(index, RecyclerItemData(data = data, type = type))
        mTypes.add(index, type)
    }

    /**
     * 指定位置添加数据
     */
    fun addOtherData(index: Int, type: Int, backupData: Any) {
        if (isMultipleLayoutMode()) {
            mDataList.add(index, RecyclerItemData(type = type, backupData = backupData))
            mTypes.add(index, type)
        }
    }

    /**
     * 新增MultipleType数据在尾部
     */
    fun addData(dataList: List<T>) {
        if (isMultipleLayoutMode()) {
            var creator = DataCreator<T>()
            if (defaultRulesFunction != null) {
                dataList.forEach {
                    defaultRulesFunction!!.invoke(creator, it)
                }

                creator.dataAndTypes.forEach { (type, data) ->
                    mDataList.add(RecyclerItemData(type = type, data = data))
                    mTypes.add(type)
                }

            }
        } else {
            dataList.forEach {
                mDataList.add(RecyclerItemData(data = it, type = mLayout))
                mTypes.add(mLayout)
            }
        }
    }

    /***
     * MultipleType新增数据
     */
    fun addData(dataList: List<T>, initData: DataCreator<T>.(T) -> Unit) {
        var creator = DataCreator<T>()
        dataList.forEach {
            creator.initData(it)
        }

        creator.dataAndTypes.forEach { (type, data) ->
            mDataList.add(RecyclerItemData(type = type, data = data))
        }

        mDataList.forEach {
            if (it.type == null) {
                it.type = mLayout//最后一个
            }
            mTypes.add(it.type!!)
        }
    }

    /**
     * 判断当前适配器是否存在多个布局
     * 用于add数据的时候进行区分
     */
    private fun isMultipleLayoutMode(): Boolean {
        if (mLayoutIds.isEmpty()) {
            error("请至少设置一个布局")
        }
        return mLayoutIds.size > 1
    }

    private fun clear() {
        mDataList.clear()
        mTypes.clear()
        interceptViews.clear()
        defaultRulesFunction = null

    }

    /**
     * 当我们已经定义好大部分要绑定的数据是，只是个别的需要单独设置，我们可以通过这个方法拦截，backupData作为备份数据，也就是其他少量布局数据
     */
    fun bindData(type: Int, interceptBind: (type: Int, vh: RecyclerViewHolder, data: T?, backupData: Any?) -> Unit) {
        interceptViews[type] = interceptBind
    }

    /**
     * 判断此类型布局是否被拦截
     */
    private fun isIntercept(itemViewType: Int): Boolean {
        return interceptViews.containsKey(itemViewType)
    }

    fun bindData(bind: (type: Int, vh: RecyclerViewHolder, data: T) -> Unit) {
        mBind = bind
    }

    fun onItemClick(itemClickFunction: (position: Int, view: View) -> Unit) {
        mOnItemClickListener = itemClickFunction
    }

    fun header(view: View, bindHeader: (view: View) -> Unit) {
        bindHeader(view)
    }

    fun header(layoutId: Int, bindHeader: (view: View) -> Unit) {
        mHeaderLayoutId = layoutId
        mBindHeader = bindHeader
    }

    fun footer(layoutId: Int, bindFooter: (view: View) -> Unit) {
        mFooterLayoutId = layoutId
        mBindFooter = bindFooter
    }

    fun inflater(inflater: () -> LayoutInflater) {
        layoutInflater = inflater()
    }

    fun inflater(inflater: LayoutInflater, withInflater: BaseRecyclerAdapter<T>.() -> Unit) {
        layoutInflater = inflater
        withInflater()
    }

    fun inflater(context: Context, withContext: BaseRecyclerAdapter<T>.() -> Unit) {
        layoutInflater = LayoutInflater.from(context)
        withContext()
    }

    infix fun into(recyclerView: RecyclerView?) {
        recyclerView?.adapter = this
    }

    fun into(recyclerView: () -> RecyclerView) {
        recyclerView().adapter = this
    }


    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (type) {
            HEAD_TYPE -> if (mHeaderLayoutId != null) return RecyclerViewHolder(
                inflater.inflate(
                    mHeaderLayoutId!!,
                    parent,
                    false
                )
            )
            FOOT_TYPE -> if (mFooterLayoutId != null) return RecyclerViewHolder(
                inflater.inflate(
                    mFooterLayoutId!!,
                    parent,
                    false
                )
            )
        }
        if (mTypes.contains(type) && mLayoutIds[type] != null) {
            return RecyclerViewHolder(mLayoutIds[type]?.let {
                inflater.inflate(it, parent, false)
            }!!)
        }
        return RecyclerViewHolder(inflater.inflate(mLayout, parent, false))
    }

    override fun getItemCount(): Int {
        var count = mDataList.size
        if (mHeaderLayoutId != null || mHeaderView != null) {
            count++
        }
        if (mFooterLayoutId != null || mFooterView != null) {
            count++
        }
        return count
    }

    override fun onBindViewHolder(vh: RecyclerViewHolder, position: Int) {
        if (getItemViewType(position) == HEAD_TYPE) {
            mBindHeader(vh.itemView)
        } else if (getItemViewType(position) == FOOT_TYPE) {
            mBindFooter(vh.itemView)
        } else {
            var calculatePosition = position
            if (mHeaderView != null || mHeaderLayoutId != null) {
                calculatePosition = position - 1
            }
            vh.itemView.setOnClickListener { view ->
                mOnItemClickListener?.invoke(calculatePosition, view)
            }
            if (isIntercept(getItemViewType(position))) {
                mBindInterceptView =
                    interceptViews[getItemViewType(position)] as ((type: Int, vh: RecyclerViewHolder, data: T?, backupData: Any?) -> Unit?)?
                if (mBindInterceptView != null) {
                    mBindInterceptView?.invoke(
                        getItemViewType(position),
                        vh,
                        mDataList[calculatePosition].data,
                        mDataList[calculatePosition].backupData
                    )
                }
            } else {
                mDataList[calculatePosition].data?.let { mBind(getItemViewType(position), vh, it) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when {
            isHead(position) -> HEAD_TYPE
            isFoot(position) -> FOOT_TYPE
            mTypes.size > 0 -> getType(position)
            else -> super.getItemViewType(position)
        }


    private fun getType(position: Int): Int {
        var p = position
        if (mHeaderLayoutId != null || mHeaderView != null) {
            p = if (position > 0) position - 1 else position
        }
        return mTypes[p]
    }

    private fun isFoot(position: Int): Boolean {
        if (position == itemCount - 1) {
            if (mFooterLayoutId != null || mFooterView != null) {
                return true
            }
        }
        return false
    }

    private fun isHead(position: Int): Boolean {
        if (position == 0) {
            if (mHeaderLayoutId != null || mHeaderLayoutId != null) {
                return true
            }
        }
        return false
    }

    companion object {
        const val HEAD_TYPE = 1
        const val FOOT_TYPE = 2
    }

}