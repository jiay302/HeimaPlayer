package com.example.xuyue.heimaplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.model.VideosBean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_mv.view.*

/**
 * @Date 2019/5/23 2:31 PM
 * @Author xuyue
 * @Description TODO
 */
class MvItemView : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_mv, this)
    }

    fun setData(data: VideosBean) {
        title.text = data.title
        artist.text = data.artistName
        Picasso.get().load(data.playListPic).into(bg)
    }

}