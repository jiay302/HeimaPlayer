package com.example.xuyue.heimaplayer.widget

import android.content.Context
import android.text.format.Formatter
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.xuyue.heimaplayer.R
import com.itheima.player.model.AudioBean
import kotlinx.android.synthetic.main.item_vbang.view.*

/**
 * @Date 2019/5/24 12:06 PM
 * @Author xuyue
 * @Description TODO
 */
class VBangItemView : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_vbang, this)
    }

    fun setData(audioBean: AudioBean) {
        title.text = audioBean.display_name
        artist.text = audioBean.artist
        size.text = Formatter.formatFileSize(context, audioBean.size)
    }

}