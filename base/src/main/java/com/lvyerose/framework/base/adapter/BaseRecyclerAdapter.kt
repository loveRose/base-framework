package com.lvyerose.framework.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerViewHolder>() {
    //基本布局资源ID
    private var mLayoutIdRes: Int = -1
    //多布局资源Map集 key为布局类型#itemType value为布局资源ID
    private var mMultiLayoutIdResMap: MutableMap<Int, Int> = mutableMapOf()
    //头部布局资源ID
    private var mHeaderLayoutIdRes: Int? = null
    //脚部布局资源ID
    private var mFooterLayoutIdRes: Int? = null
    //布局类型集合
    private var mItemTypeList: ArrayList<Int> = arrayListOf()
    //原始数据集
    private var mRawDataList: ArrayList<T> = arrayListOf()
    //经过加工之后适应本适配器使用的数据集
    private var mDataList: MutableList<RecyclerItemData<T>> = arrayListOf()

    //拦截器容器，存储需要拦截的View类型作为key，拦截规则作为value
    private var mInterceptViewMap: MutableMap<Int, Any> = mutableMapOf()
    //拦截器规则函数
    private var mBindInterceptView:
            ((itemType: Int, holder: RecyclerViewHolder, data: T?, otherData: Any?) -> Unit?)? = null
    //数据布局对应规则
    private var dataRulesFunction: (DataWrapper<T>.(T) -> Unit)? = null
    //普通view的绑定函数
    private lateinit var mBindItemView: ((itemType: Int, holder: RecyclerViewHolder, data: T) -> Unit?)
    //定义Item点击监听回调
    private var mOnItemClickListener: ((itemView: View, position: Int) -> Unit)? = null
    //HeadView绑定函数
    private lateinit var mBindHeaderView: (view: View) -> Unit
    //FootView绑定函数
    private lateinit var mBindFooterView: (view: View) -> Unit

    /**
     * 计算布局数量
     * 如果头部和尾部设置了，则增加数量
     */
    override fun getItemCount(): Int {
        var count = mDataList.size
        if (mHeaderLayoutIdRes != null) {
            count++
        }
        if (mFooterLayoutIdRes != null) {
            count++
        }
        return count
    }

    /**
     * 覆盖系统的类型获取方法，提供我们的逻辑
     * 如果不是头部类型和尾部类型则判断类型存储集合是否为空，不为空的话返回对应的类型
     */
    override fun getItemViewType(position: Int): Int =
        when {
            isHeaderByPosition(position) -> BaseRecyclerAdapter.HEAD_TYPE
            isFooterByPosition(position) -> BaseRecyclerAdapter.FOOT_TYPE
            mItemTypeList.isNotEmpty() -> getItemType(position)
            else -> super.getItemViewType(position)
        }

    /**
     *
     * 如果当前存在头部布局，则每个位置 -1 为实际数据的位置
     */
    private fun getItemType(position: Int): Int {
        var itemPosition = position
        if (mHeaderLayoutIdRes != null) {
            itemPosition = if (position > 0) position - 1 else position
        }
        return mItemTypeList[itemPosition]
    }

    /**
     * 根据position判断是否需要添加尾部位置
     */
    private fun isFooterByPosition(position: Int): Boolean {
        if (position == itemCount - 1) {
            if (mFooterLayoutIdRes != null) {
                return true
            }
        }
        return false
    }

    /**
     * 根据position判断是否需要添加头部位置
     */
    private fun isHeaderByPosition(position: Int): Boolean {
        if (position == 0) {
            if (mHeaderLayoutIdRes != null) {
                return true
            }
        }
        return false
    }

    /**
     * 根据类型判断布局是否需要拦截
     */
    private fun isIntercept(itemViewType: Int): Boolean {
        return mInterceptViewMap.containsKey(itemViewType)
    }

    /**
     * 根据视图类型返回对应的ViewHolder
     * 视图类型已经由系统调用 #getItemViewType()方法获取
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //when里判断是否是头部类型、是否是尾部类型、是否存在多布局类型，如果没有则执行最后的单一布局类型
        when (viewType) {
            BaseRecyclerAdapter.HEAD_TYPE -> mHeaderLayoutIdRes?.let {
                return RecyclerViewHolder(
                    inflater.inflate(
                        it!!,
                        parent,
                        false
                    )
                )
            }
            BaseRecyclerAdapter.FOOT_TYPE -> mFooterLayoutIdRes?.let {
                return RecyclerViewHolder(
                    inflater.inflate(
                        it!!,
                        parent,
                        false
                    )
                )
            }
            else -> {
                if (mItemTypeList.contains(viewType) && mMultiLayoutIdResMap[viewType] != null) {
                    return RecyclerViewHolder(mMultiLayoutIdResMap[viewType]?.let {
                        inflater.inflate(it, parent, false)
                    }!!)
                }
            }
        }
        return RecyclerViewHolder(inflater.inflate(mLayoutIdRes!!, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        var itemType = getItemViewType(position)
        if (itemType == HEAD_TYPE) {
            //头部 视图数据绑定
            mBindHeaderView(holder.itemView)
        } else if (itemType == FOOT_TYPE) {
            //尾部 视图数据绑定
            mBindFooterView(holder.itemView)
        } else {
            //普通 视图数据绑定
            var itemPosition = position
            if (mHeaderLayoutIdRes != null) {
                itemPosition = position - 1
            }
            holder.itemView.setOnClickListener { view ->
                mOnItemClickListener?.invoke(view, itemPosition)
            }
            if (isIntercept(getItemViewType(position))) {
                mBindInterceptView =
                    mInterceptViewMap[getItemViewType(position)] as ((itemType: Int, holder: RecyclerViewHolder, data: T?, otherData: Any?) -> Unit?)?
                if (mBindInterceptView != null) {
                    mBindInterceptView?.invoke(
                        getItemViewType(position),
                        holder,
                        mDataList[itemPosition].data,
                        mDataList[itemPosition].otherData
                    )
                }
            } else {
                mDataList[itemPosition].data?.let { mBindItemView(getItemViewType(position), holder, it) }
            }
        }
    }

    private fun clear() {
        mDataList.clear()
        mItemTypeList.clear()
        mInterceptViewMap.clear()
        dataRulesFunction = null
    }

    companion object {
        const val HEAD_TYPE = 1
        const val FOOT_TYPE = 2
    }

    infix fun adapter(recyclerView: RecyclerView?) {
        recyclerView?.adapter = this
    }

    fun adapter(recyclerView: () -> RecyclerView) {
        recyclerView().adapter = this
    }


    /*********************************************************         对外提供的API        ****************************************************************************/

    /**
     * 添加一个布局
     * @param layoutIdRes 布局资源ID
     */
    fun layout(layoutIdRes: () -> Int) {
        mLayoutIdRes = layoutIdRes()
        mMultiLayoutIdResMap[mLayoutIdRes] = mLayoutIdRes
    }

    /**
     * 添加多个布局
     * 通过List<layoutIdRes>进行创建
     * @param layoutIdList 布局资源ID集合
     */
    fun layoutMultiple(layoutIdList: List<Int>) {
        layoutIdList.forEach {
            mMultiLayoutIdResMap[it] = it
        }
        if (mMultiLayoutIdResMap.isNotEmpty()) {
            var firstKey = mMultiLayoutIdResMap.keys.first()
            mLayoutIdRes = mMultiLayoutIdResMap[firstKey]!!
        }
    }

    /**
     * 添加多个布局
     * 通过布局创造器添加多个布局
     * @param initLayout 布局创造器
     */
    fun layoutMultiple(initLayout: LayoutWrapper.() -> Unit) {
        val wrapper = LayoutWrapper()
        wrapper.initLayout()
        var firstKey = wrapper.getValue().keys.first()
        mLayoutIdRes = wrapper.getValue()[firstKey]!!
        mMultiLayoutIdResMap = wrapper.getValue()
    }


    /**
     * 设置数据源
     * 单一数据源使用该方法
     * 先清除所有数据重新添加
     *
     */
    fun setData(dataList: () -> ArrayList<T>) {
        clear()
        mRawDataList = dataList()
        dataList().forEach {
            mDataList.add(RecyclerItemData(itemType = mLayoutIdRes, data = it))
            mItemTypeList.add(mLayoutIdRes)
        }

    }

    /**
     * 数据适配器
     * 多数据模块下使用该方法
     */
    fun dataAdapter(dataList: ArrayList<T>, initData: DataWrapper<T>.(T) -> Unit) {
        clear()
        dataRulesFunction = initData
        mRawDataList = dataList
        var wrapper = DataWrapper<T>()
        //遍历原始数据 并回调创建器
        dataList.forEach {
            wrapper.initData(it)
        }
        //经过上面回调创建器 创建了大量数据和类型并存储在dataTypeList中
        //取出并创建我们适配器通用的数据类型
        wrapper.dataATypeList.forEach { (itemType, data) ->
            mDataList.add(RecyclerItemData(itemType = itemType, data = data))
        }
        //设置对应类型集
        mDataList.forEach {
            if (it.itemType == null) {
                it.itemType = mLayoutIdRes
            }
            mItemTypeList.add(it.itemType!!)
        }
    }

    /**
     * 添加一条单个数据
     */
    fun addData(data: T) {
        if (!isMultipleLayoutMode()) {
            mDataList.add(RecyclerItemData(itemType = mLayoutIdRes, data = data))
            mItemTypeList.add(mLayoutIdRes)
        } else {
            mDataList.add(RecyclerItemData(itemType = mLayoutIdRes, data = data))
            mItemTypeList.add(mLayoutIdRes)
//            //多布局类型数据添加，使用最后一次布局规则
//            var wrapper = DataWrapper<T>()
//            if (dataRulesFunction != null) {
//                dataRulesFunction!!.invoke(wrapper, data)
//                wrapper.dataATypeList.forEach { (itemType, data) ->
//                    mDataList.add(RecyclerItemData(itemType = itemType, data = data))
//                    mItemTypeList.add(itemType)
//                }
//            }
        }
    }

    /**
     * 添加一条多类型模式数据
     * 同样适用于单一布局类型
     */
    fun addData(itemType: Int, data: T) {
        mDataList.add(RecyclerItemData(itemType = itemType, data = data))
        mItemTypeList.add(itemType)
    }

    /**
     * 添加一条数据在指定位置
     */
    fun addData(index: Int, itemType: Int, data: T) {
        mDataList.add(index, RecyclerItemData(itemType = itemType, data = data))
        mItemTypeList.add(index, itemType)
    }

    /**
     * 指定位置添加数据
     */
    fun addOtherData(index: Int, type: Int, backupData: Any) {
        if (isMultipleLayoutMode()) {
            mDataList.add(index, RecyclerItemData(itemType = type, otherData = backupData))
            mItemTypeList.add(index, type)
        }
    }

    /**
     * 添加List数据
     * 如果是多布局，则使用最后一次布局规则进行绘制
     * 如果是单布局，则直接按单布局规则添加
     */
    fun addData(dataList: List<T>) {
        if (isMultipleLayoutMode()) {
            //多布局类型数据添加，使用最后一次布局规则
            var wrapper = DataWrapper<T>()
            if (dataRulesFunction != null) {
                dataList.forEach {
                    dataRulesFunction!!.invoke(wrapper, it)
                }
                wrapper.dataATypeList.forEach { (itemType, data) ->
                    mDataList.add(RecyclerItemData(itemType = itemType, data = data))
                    mItemTypeList.add(itemType)
                }
            }
        } else {
            //普通布局数据添加
            dataList.forEach {
                addData(mLayoutIdRes, it)
            }
        }
    }


    /***
     * mutilType新增数据
     */
    fun addDatas(dataList: List<T>, initData: DataWrapper<T>.(T) -> Unit) {
        var wrapper = DataWrapper<T>()
        dataList.forEach {
            wrapper.initData(it)
        }

        wrapper.dataATypeList.forEach { (type, data) ->
            mDataList.add(RecyclerItemData(itemType = type, data = data))
        }
        mDataList.forEach {
            if (it.itemType == null) {
                it.itemType = mLayoutIdRes
            }
            mItemTypeList.add(it.itemType!!)
        }
    }

    private fun isMultipleLayoutMode(): Boolean {
        if (mMultiLayoutIdResMap.isEmpty()) {
            error("请至少设置一个布局")
        }
        return mMultiLayoutIdResMap.size > 1
    }

    /**
     * item点击事件
     */
    fun onItemClick(itemClickFunction: (itemView: View, position: Int) -> Unit) {
        mOnItemClickListener = itemClickFunction
    }

    /**
     * 绑定普通布局对应数据的回调
     */
    fun bindViewData(bindView: (itemType: Int, holder: RecyclerViewHolder, data: T) -> Unit) {
        mBindItemView = bindView
    }

    /**
     * 添加headerView 并携带布局回调函数
     * 通过资源ID创建
     */
    fun headerViewData(layoutId: Int, bindHeader: (view: View) -> Unit) {
        mHeaderLayoutIdRes = layoutId
        mBindHeaderView = bindHeader
    }

    /**
     * 添加footerView 并携带布局回调函数
     * 通过资源ID创建
     */
    fun footerView(layoutId: Int, bindFooter: (view: View) -> Unit) {
        mFooterLayoutIdRes = layoutId
        mBindFooterView = bindFooter
    }

}