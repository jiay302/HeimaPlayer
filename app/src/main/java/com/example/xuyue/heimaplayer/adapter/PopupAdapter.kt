package com.example.xuyue.heimaplayer.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.xuyue.heimaplayer.widget.PopupListItemView
import com.itheima.player.model.AudioBean

/**
 * @Date 2019/5/30 5:55 PM
 * @Author xuyue
 * @Description 播放列表适配器
 */
class PopupAdapter(var list: List<AudioBean>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView: PopupListItemView? = null
        itemView = if (convertView == null) {
            PopupListItemView(parent?.context)
        } else {
            convertView as PopupListItemView
        }
        itemView.setData(list[position])
        return itemView
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}