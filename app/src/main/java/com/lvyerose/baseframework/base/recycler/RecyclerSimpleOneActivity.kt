package com.lvyerose.baseframework.base.recycler

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.lvyerose.baseframework.R
import com.lvyerose.baseframework.base.recycler.adapter.SimpleAdapter1
import com.lvyerose.framework.base.general.BaseActivity
import kotlinx.android.synthetic.main.activity_recycler_simple_one.*
import kotlinx.android.synthetic.main.item_footer_layout.view.*

/**
 * 单一数据使用说明
 *
 */
class RecyclerSimpleOneActivity : BaseActivity() {
    var simpleAdapter = SimpleAdapter1()
    override fun setContentLayoutId() = R.layout.activity_recycler_simple_one

    override fun onStartAction(savedInstanceState: Bundle?) {
        var dataList = arrayListOf(
            "基础数据1(点击任意Item可添加新数据)",
            "基础数据2",
            "基础数据3",
            "基础数据4",
            "基础数据5",
            "基础数据6",
            "基础数据7",
            "基础数据8",
            "基础数据9"

        )
        recycler_view.layoutManager = LinearLayoutManager(this)
        simpleAdapter.layout {
            R.layout.item_simple_1_layout
        }
        simpleAdapter.bindViewData { _, holder, data ->
            holder.bindView<TextView>(R.id.item_tv_title).text = data
        }
        simpleAdapter.setData {
            dataList
        }

        simpleAdapter.onItemClick { _, position ->
            if (position % 3 == 0) {
                simpleAdapter.addData(arrayListOf("1批量新增$position", "2批量新增$position"))
                simpleAdapter.notifyDataSetChanged()
            } else {
                simpleAdapter.addData("点击第 $position 项新增的数据")
                simpleAdapter.notifyDataSetChanged()
            }
        }

        simpleAdapter adapter recycler_view

        simpleAdapter.headerViewData(R.layout.item_header_layout) {
            it.item_tv_title.text = "添加的头部"
            it.item_tv_title.setOnClickListener {
                "头部被点击了".toast()
            }
        }
        simpleAdapter.footerView(R.layout.item_footer_layout) {
            it.item_tv_title.text = "添加的尾部"
            it.item_tv_title.setOnClickListener {
                "尾部被点击了".toast()
            }
        }


    }
}
