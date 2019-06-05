package com.example.xuyue.heimaplayer.adapter

import android.content.Context
import com.example.xuyue.heimaplayer.base.BaseListAdapter
import com.example.xuyue.heimaplayer.widget.HomeItemView
import com.itheima.player.model.bean.HomeItemBean

/**
 * @Date 2019/5/13 6:32 PM
 * @Author xuyue
 * @Description 首页适配器
 */
class HomeAdapter : BaseListAdapter<HomeItemBean, HomeItemView>() {

    override fun refreshItemView(itemView: HomeItemView, data: HomeItemBean) {
        itemView.setData(data)
    }

    override fun getItemView(context: Context?): HomeItemView {
        return HomeItemView(context)
    }

}