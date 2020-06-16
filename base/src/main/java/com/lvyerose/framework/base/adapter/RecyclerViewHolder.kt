package com.lvyerose.framework.base.adapter

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var viewList = SparseArray<View>()

    fun <T : View> bindView(id: Int): T {
        return if (viewList.get(id) == null) {
            var view = itemView.findViewById<View>(id)
            viewList.put(id, view)
            view as T
        } else {
            viewList.get(id) as T
        }
    }

}