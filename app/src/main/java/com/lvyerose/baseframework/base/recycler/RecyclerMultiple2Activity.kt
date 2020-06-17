package com.lvyerose.baseframework.base.recycler

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.lvyerose.baseframework.R
import com.lvyerose.baseframework.base.recycler.adapter.MultipleAdapter1
import com.lvyerose.baseframework.base.recycler.adapter.SimpleAdapter1
import com.lvyerose.framework.base.adapter.RecyclerViewHolder
import com.lvyerose.framework.base.general.BaseActivity
import kotlinx.android.synthetic.main.activity_recycler_simple_one.*
import kotlinx.android.synthetic.main.item_footer_layout.view.*

class RecyclerMultiple2Activity : BaseActivity() {
    var adapter1 = MultipleAdapter1()

    override fun setContentLayoutId() = R.layout.activity_recycler_multiple1

    override fun onStartAction(savedInstanceState: Bundle?) {
        var dataList = arrayListOf(
            "点击添加一条新数据",
            "点击添加一条新数据,新的规则",
            "点击添加多条新数据",
            "点击添加多条新数据, 新的规则",
            "点击添加额外数据"
        )
        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter1.layoutMultiple {
            layout { R.layout.item_color_1_layout }
            layout { R.layout.item_color_2_layout }
            layout { R.layout.item_color_3_layout }
            layout { R.layout.item_color_4_layout }
            layout { R.layout.item_color_5_layout }
        }
        adapter1.bindViewData { itemType, holder, data ->
            when (itemType) {
                R.layout.item_color_1_layout -> holder.bindView<TextView>(R.id.item_tv_title).text = data
                R.layout.item_color_2_layout -> holder.bindView<TextView>(R.id.item_tv_title).text = data
                R.layout.item_color_3_layout -> holder.bindView<TextView>(R.id.item_tv_title).text = data
                R.layout.item_color_4_layout -> holder.bindView<TextView>(R.id.item_tv_title).text = data
                R.layout.item_color_5_layout -> holder.bindView<TextView>(R.id.item_tv_title).text = data
            }
        }
        //使用该方法将使用第一个布局效果
//        adapter1.setData {
//            dataList
//        }

        adapter1.dataAdapter(dataList) {
            when (it) {
                dataList[0] -> {
                    addData(R.layout.item_color_1_layout, it)
                }
                dataList[1] -> {
                    addData(R.layout.item_color_2_layout, it)
                }
                dataList[2] -> {
                    addData(R.layout.item_color_3_layout, it)
                }
                dataList[3] -> {
                    addData(R.layout.item_color_4_layout, it)
                }
                dataList[4] -> {
                    addData(R.layout.item_color_5_layout, it)
                }
            }
        }
        adapter1.onItemClick { _, position ->
            when (position) {
                0 -> {
                    adapter1.addData(R.layout.item_color_3_layout, "新添加的一条数据")
                    adapter1.notifyDataSetChanged()
                }
                1 -> {
                    adapter1.interceptBindViewData(R.layout.item_color_6_layout) { itemType, holder, data, otherData ->
                        data?.let {
                            holder.itemView.item_tv_title.text = "data新新新· $data"
                        }

                    }
                    adapter1.interceptBindViewData(R.layout.item_color_7_layout) { itemType, holder, data, otherData ->
                        otherData?.let {
                            holder.itemView.item_tv_title.text = otherData as String
                        }
                    }
                    adapter1.addData("新添加带布局规则的数据") {
                        when (it) {
                            "新添加带布局规则的数据" -> {
                                addData(R.layout.item_color_6_layout, it)
                            }
                            "新添加带布局规则的数据mmmm" -> {
                                addData(R.layout.item_color_6_layout, it)
                            }
                            "新添加带布局规则的数据nnnn" -> {
                                addData(R.layout.item_color_7_layout, it)
                            }
                        }
                    }
                    adapter1.notifyDataSetChanged()
                }
                2 -> {
                    adapter1.addData(arrayListOf("新添加带布局规则的数据mmmm", "新添加带布局规则的数据mmmm"))
                    adapter1.notifyDataSetChanged()
                }
                3 -> {
                    adapter1.addData(arrayListOf("新添加带布局规则的数据mmmm", "新添加带布局规则的数据mmmm")) {
                        when (it) {
                            "新添加带布局规则的数据" -> {
                                addData(R.layout.item_color_2_layout, it)
                            }
                            "新添加带布局规则的数据mmmm" -> {
                                addData(R.layout.item_color_5_layout, it)
                            }
                            "新添加带布局规则的数据mmmm" -> {
                                addData(R.layout.item_color_7_layout, it)
                            }
                        }
                    }
                    adapter1.addOtherData(0, R.layout.item_color_7_layout, "other数据")
                    adapter1.notifyDataSetChanged()
                }
            }

        }

        adapter1 adapter recycler_view

        adapter1.headerViewData(R.layout.item_header_layout) {
            it.item_tv_title.text = "添加的头部"
            it.item_tv_title.setOnClickListener {
                "头部被点击了".toast()
            }
        }
        adapter1.footerView(R.layout.item_footer_layout) {
            it.item_tv_title.text = "添加的尾部"
            it.item_tv_title.setOnClickListener {
                "尾部被点击了".toast()
            }
        }

    }
}
