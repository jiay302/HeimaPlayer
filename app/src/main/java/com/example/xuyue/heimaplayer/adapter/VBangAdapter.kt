package com.example.xuyue.heimaplayer.adapter

import android.content.Context
import android.database.Cursor
import android.support.v4.widget.CursorAdapter
import android.view.View
import android.view.ViewGroup
import com.example.xuyue.heimaplayer.widget.VBangItemView
import com.itheima.player.model.AudioBean

/**
 * @Date 2019/5/24 12:10 PM
 * @Author xuyue
 * @Description TODO
 */
class VBangAdapter(context: Context?, c: Cursor?) : CursorAdapter(context, c) {

    /**
     * 创建条目View
     */
    override fun newView(p0: Context?, p1: Cursor?, p2: ViewGroup?): View {
        return VBangItemView(p0)
    }

    /**
     * 进行View和数据的绑定
     */
    override fun bindView(p0: View?, p1: Context?, p2: Cursor?) {
        val itemView = p0 as VBangItemView
        val audioBean = AudioBean.getAudioBean(p2)
        itemView.setData(audioBean)
    }

}