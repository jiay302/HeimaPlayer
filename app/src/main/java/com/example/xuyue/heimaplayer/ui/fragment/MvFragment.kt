package com.example.xuyue.heimaplayer.ui.fragment

import android.view.View
import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.adapter.MvPagerAdapter
import com.example.xuyue.heimaplayer.base.BaseFragment
import com.example.xuyue.heimaplayer.model.MvAreaBean
import com.example.xuyue.heimaplayer.presenter.impl.MvPresenterImpl
import com.example.xuyue.heimaplayer.view.MvView
import kotlinx.android.synthetic.main.fragment_mv.*

/**
 * @Date 2019/5/13 5:43 PM
 * @Author xuyue
 * @Description MV Fragment
 */
class MvFragment : BaseFragment(), MvView {

    val presenter by lazy { MvPresenterImpl(this) }

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_mv, null)
    }

    override fun initListener() {

    }

    override fun initData() {
        // 加载区域数据
        presenter.loadData()
    }

    override fun onError(msg: String?) {
        myToast("加载数据失败")
    }

    override fun onSuccess(result: List<MvAreaBean>) {

        // 在Fragment中管理Fragment需要用childFragmentManager
        val adapter = MvPagerAdapter(result, childFragmentManager)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }
}