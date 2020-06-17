package com.lvyerose.baseframework.base.recycler

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lvyerose.baseframework.R
import com.lvyerose.baseframework.base.recycler.adapter.completeAdapter
import com.lvyerose.framework.base.adapter.RecyclerAdapterFactory
import com.lvyerose.framework.base.general.BaseActivity
import kotlinx.android.synthetic.main.activity_recycler_simple_one.*
import kotlinx.android.synthetic.main.item_footer_layout.view.*

/**
 * 适配器完整示例搭建
 */
class RecyclerCompleteActivity : BaseActivity() {
    private var menuAdapter = RecyclerAdapterFactory.createRecyclerAdapter<MenuItemData> {
        layout {
            R.layout.item_menu_layout
        }

        bindViewData { itemType, holder, data ->
            holder.bindView<TextView>(R.id.tv_menu_name).text = data.menuName
        }

        onItemClick { itemView, position ->
            "$position 菜单被点击了".toast()
        }
    }

    override fun setContentLayoutId() = R.layout.activity_recycler_multiple1

    override fun onStartAction(savedInstanceState: Bundle?) {
        var dataList = arrayListOf(
            ItemData(R.mipmap.complete_1, "数据1"),
            ItemData(R.mipmap.complete_2, "数据2"),
            ItemData(R.mipmap.complete_3, "数据3"),
            ItemData(R.mipmap.complete_4, "数据4"),
            ItemData(R.mipmap.complete_5, "数据5"),
            ItemData(R.mipmap.complete_6, "数据6"),
            ItemData(R.mipmap.complete_7, "数据7"),
            ItemData(R.mipmap.complete_8, "数据8"),
            ItemData(R.mipmap.complete_9, "数据9"),
            ItemData(R.mipmap.complete_10, "数据10"),
            ItemData(R.mipmap.complete_11, "数据11"),
            ItemData(R.mipmap.complete_12, "数据12"),
            ItemData(R.mipmap.complete_13, "数据13")
        )
        recycler_view.layoutManager = LinearLayoutManager(this)
        completeAdapter.layoutMultiple {
            layout { R.layout.item_complete_header_layout }
            layout { R.layout.item_complete_circle_layout }
            layout { R.layout.item_complete_default_layout }
        }
        completeAdapter.dataAdapter(dataList) {
            //第一波数据全部用通用布局类型
            addData(R.layout.item_complete_default_layout, it)
        }
        completeAdapter.bindViewData { itemType, holder, data ->
            when (itemType) {
                R.layout.item_complete_default_layout -> {
                    //普通数据适配
                    holder.bindView<TextView>(R.id.tv_default_name).text = data.data
                    holder.bindView<ImageView>(R.id.imv_cover).setImageResource(data.imgRes)
                }
            }
        }
        completeAdapter adapter recycler_view

        completeAdapter.headerViewData(R.layout.item_complete_header_layout) {
            it.item_tv_title.setOnClickListener {
                "头部文本控件被点击了".toast()
            }
        }
        completeAdapter.footerView(R.layout.item_footer_layout) {
            it.item_tv_title.text = "添加的尾部"
            it.item_tv_title.setOnClickListener {
                "尾部被点击了".toast()
            }
        }
        completeAdapter.interceptBindViewData(R.layout.item_complete_top_mune_layout) { itemType, holder, data, otherData ->
            //拦截top_menu布局类型 并在此处进行数据绑定
            if (itemType == R.layout.item_complete_top_mune_layout) {
                holder.bindView<RecyclerView>(R.id.item_menu_recycler_view).layoutManager = GridLayoutManager(this, 4)
                menuAdapter.setData {
                    (otherData as MenuData).itemList!!
                }
                menuAdapter adapter holder.bindView(R.id.item_menu_recycler_view)
            }
        }
        completeAdapter.interceptBindViewData(R.layout.item_complete_circle_layout) { itemType, holder, data, otherData ->
            //拦截圆形布局类型 并在此处进行数据绑定
            if (otherData is CircleItemData) {
                holder.bindView<TextView>(R.id._banal_1).text = otherData.name1
                holder.bindView<TextView>(R.id._banal_2).text = otherData.name2
                holder.bindView<TextView>(R.id._banal_3).text = otherData.name3
            }
        }
        //添加顶部菜单数据
        var list = arrayListOf<MenuItemData>()
        repeat(8) {
            var menuItemData = MenuItemData(it, "Menu$it")
            list.add(menuItemData)
        }
        var menuData = MenuData()
        menuData.itemList = list
        completeAdapter.addOtherData(0, R.layout.item_complete_top_mune_layout, menuData)
        //添加顶部横向布局
        completeAdapter.addOtherData(1, R.layout.item_complete_circle_layout, CircleItemData("菜单1", "菜单2", "菜单3"))
        completeAdapter.notifyDataSetChanged()

    }
}
