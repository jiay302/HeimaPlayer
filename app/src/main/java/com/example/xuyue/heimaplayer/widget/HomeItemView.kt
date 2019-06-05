package com.example.xuyue.heimaplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.xuyue.heimaplayer.R
import com.itheima.player.model.bean.HomeItemBean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * @Date 2019/5/13 6:15 PM
 * @Author xuyue
 * @Description 首页条目View
 */
class HomeItemView : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 初始化方法
     */
    init {
        View.inflate(context, R.layout.item_home, this)
    }

    /**
     * 刷新条目数据
     */
    fun setData(data: HomeItemBean) {
        // 歌曲名称
        title.text = data.title
        // 简介
        desc.text = data.description
        // 背景图片
        Picasso.get().load(data.posterPic).into(bg)
    }
}