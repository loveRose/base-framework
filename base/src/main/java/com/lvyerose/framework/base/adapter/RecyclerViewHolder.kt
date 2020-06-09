package com.lvyerose.framework.base.adapter

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var views = SparseArray<View>()

    fun <T : View> bindView(id: Int): T {
        return if (views.get(id) == null) {
            var view = itemView.findViewById<View>(id)
            views.put(id, view)
            view as T
        } else {
            views.get(id) as T
        }
    }

}