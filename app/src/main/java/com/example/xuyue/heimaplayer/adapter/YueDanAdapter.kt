package com.example.xuyue.heimaplayer.adapter

import android.content.Context
import com.example.xuyue.heimaplayer.base.BaseListAdapter
import com.example.xuyue.heimaplayer.model.YueDanBean
import com.example.xuyue.heimaplayer.widget.YueDanItemView

/**
 * @Date 2019/5/22 10:32 PM
 * @Author xuyue
 * @Description 悦单适配器
 */
class YueDanAdapter : BaseListAdapter<YueDanBean.PlayListsBean, YueDanItemView>() {

    override fun refreshItemView(itemView: YueDanItemView, data: YueDanBean.PlayListsBean) {
        itemView.setData(data)
    }

    override fun getItemView(context: Context?): YueDanItemView {
        return YueDanItemView(context)
    }

}