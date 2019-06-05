package com.example.xuyue.heimaplayer.ui.fragment

import com.example.xuyue.heimaplayer.adapter.MvListAdapter
import com.example.xuyue.heimaplayer.base.BaseListAdapter
import com.example.xuyue.heimaplayer.base.BaseListFragment
import com.example.xuyue.heimaplayer.base.BaseListPresenter
import com.example.xuyue.heimaplayer.model.VideoPlayBean
import com.example.xuyue.heimaplayer.model.VideosBean
import com.example.xuyue.heimaplayer.presenter.impl.MvListPresenterImpl
import com.example.xuyue.heimaplayer.ui.activity.JiaoziPlayerActivity
import com.example.xuyue.heimaplayer.widget.MvItemView
import org.jetbrains.anko.support.v4.startActivity

/**
 * @Date 2019/5/23 1:59 PM
 * @Author xuyue
 * @Description mv界面每个页面的Fragment
 */
class MvPagerFragment : BaseListFragment<List<VideosBean>, VideosBean, MvItemView>() {

    var code: String? = null

    override fun init() {
        // 获取传递的数据
        code = arguments?.getString("args")
    }

    override fun getSpecialAdapter(): BaseListAdapter<VideosBean, MvItemView> {
        return MvListAdapter()
    }

    override fun getList(response: List<VideosBean>?): List<VideosBean>? {
        return response
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return MvListPresenterImpl(code!!, this)
    }

    override fun initListener() {
        super.initListener()
        // 设置条目点击事件
        adapter.setMyListener {
            startActivity<JiaoziPlayerActivity>("item" to VideoPlayBean(it.id, it.title, it.url))
        }
    }

}