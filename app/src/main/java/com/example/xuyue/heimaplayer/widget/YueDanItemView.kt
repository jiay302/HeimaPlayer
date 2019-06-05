package com.example.xuyue.heimaplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.model.YueDanBean
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.item_yuedan.view.*

/**
 * @Date 2019/5/22 10:20 PM
 * @Author xuyue
 * @Description 悦单Item
 */
class YueDanItemView : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_yuedan, this)
    }

    fun setData(data: YueDanBean.PlayListsBean) {

        //  歌单名称
        title.text = data.title

        // 歌手名称
        author_name.text = data.creator?.nickName

        // 歌曲数量
        count.text = data.videoCount.toString()

        // 头像
        Picasso.get().load(data.playListPic).transform(CropCircleTransformation()).into(author_image)

        // 背景图
        Picasso.get().load(data.thumbnailPic).into(bg)
    }
}