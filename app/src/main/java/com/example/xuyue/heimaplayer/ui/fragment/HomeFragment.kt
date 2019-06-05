package com.example.xuyue.heimaplayer.ui.fragment

import com.example.xuyue.heimaplayer.adapter.HomeAdapter
import com.example.xuyue.heimaplayer.base.BaseListAdapter
import com.example.xuyue.heimaplayer.base.BaseListFragment
import com.example.xuyue.heimaplayer.base.BaseListPresenter
import com.example.xuyue.heimaplayer.presenter.impl.HomePresenterImpl
import com.example.xuyue.heimaplayer.widget.HomeItemView
import com.itheima.player.model.bean.HomeItemBean

/**
 * @Date 2019/5/13 5:43 PM
 * @Author xuyue
 * @Description 首页 Fragment
 */
class HomeFragment : BaseListFragment<List<HomeItemBean>, HomeItemBean, HomeItemView>() {

    override fun getSpecialAdapter(): BaseListAdapter<HomeItemBean, HomeItemView> {
        return HomeAdapter()
    }

    override fun getList(response: List<HomeItemBean>?): List<HomeItemBean>? {
        return response
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return HomePresenterImpl(this)
    }

}