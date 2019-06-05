package com.example.xuyue.heimaplayer.adapter

import android.content.Context
import com.example.xuyue.heimaplayer.base.BaseListAdapter
import com.example.xuyue.heimaplayer.model.VideosBean
import com.example.xuyue.heimaplayer.widget.MvItemView

/**
 * @Date 2019/5/23 2:44 PM
 * @Author xuyue
 * @Description TODO
 */
class MvListAdapter : BaseListAdapter<VideosBean, MvItemView>() {

    override fun refreshItemView(itemView: MvItemView, data: VideosBean) {
        itemView.setData(data)

    }

    override fun getItemView(context: Context?): MvItemView {
        return MvItemView(context)
    }

}