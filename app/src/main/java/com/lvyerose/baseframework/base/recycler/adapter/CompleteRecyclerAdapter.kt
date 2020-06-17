package com.lvyerose.baseframework.base.recycler.adapter

import com.lvyerose.baseframework.R
import com.lvyerose.baseframework.base.recycler.ItemData
import com.lvyerose.framework.base.adapter.BaseRecyclerAdapter
import com.lvyerose.framework.base.adapter.RecyclerAdapterFactory

var completeAdapter: BaseRecyclerAdapter<ItemData> = RecyclerAdapterFactory.createRecyclerAdapter {
    layoutMultiple {
        layout {
            //顶部
            R.layout.item_complete_header_layout
        }
        layout {
            //横向推荐位
            R.layout.item_complete_header_layout
        }
        layout {
            //正常布局
            R.layout.item_complete_header_layout
        }
    }

}