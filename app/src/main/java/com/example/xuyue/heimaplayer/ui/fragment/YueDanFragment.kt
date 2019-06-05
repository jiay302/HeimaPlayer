package com.example.xuyue.heimaplayer.ui.fragment

import com.example.xuyue.heimaplayer.adapter.YueDanAdapter
import com.example.xuyue.heimaplayer.base.BaseListAdapter
import com.example.xuyue.heimaplayer.base.BaseListFragment
import com.example.xuyue.heimaplayer.base.BaseListPresenter
import com.example.xuyue.heimaplayer.model.YueDanBean
import com.example.xuyue.heimaplayer.presenter.impl.YueDanPresenterImpl
import com.example.xuyue.heimaplayer.widget.YueDanItemView

/**
 * @Date 2019/5/13 5:43 PM
 * @Author xuyue
 * @Description 悦单 Fragment
 */
class YueDanFragment : BaseListFragment<YueDanBean, YueDanBean.PlayListsBean, YueDanItemView>() {
    override fun getSpecialAdapter(): BaseListAdapter<YueDanBean.PlayListsBean, YueDanItemView> {
        return YueDanAdapter()
    }

    override fun getList(response: YueDanBean?): List<YueDanBean.PlayListsBean>? {
        return response?.playLists
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return YueDanPresenterImpl(this)
    }

}