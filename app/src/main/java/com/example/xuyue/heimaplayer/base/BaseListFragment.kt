package com.example.xuyue.heimaplayer.base

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.xuyue.heimaplayer.R
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * @Date 2019/5/23 12:57 PM
 * @Author xuyue
 * @Description 所有列表页的公共基类
 */
abstract class BaseListFragment<RESPONSE, ITEMBEAN, ITEMVIEW : View> : BaseFragment(), BaseView<RESPONSE> {
    val adapter by lazy {
        getSpecialAdapter()
    }

    val presenter by lazy {
        getSpecialPresenter()
    }

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_list, null)
    }

    override fun initListener() {
        // 初始化RecycleView
        recycleView.layoutManager = LinearLayoutManager(context)
        // 适配
        recycleView.adapter = adapter

        // 初始化下拉刷新控件
        refreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN)
        // 刷新监听
        refreshLayout.setOnRefreshListener {
            presenter.loadData()
        }

        // 监听列表滑动
        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 最后一条是否已经显示
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                        if (lastVisibleItemPosition == adapter.itemCount - 1) {
                            // 最后一条已经显示了
                            presenter.loadMore(adapter.itemCount - 1)
                        }
                    }
                }
            }
        })
    }

    override fun initData() {
        // 初始化数据
        presenter.loadData()
    }

    override fun onLoadDataSuccess(response: RESPONSE?) {
        refreshLayout.isRefreshing = false
        adapter.updateList(getList(response))
    }

    override fun onLoadMoreSuccess(response: RESPONSE?) {
        refreshLayout.isRefreshing = false
        adapter.loadMore(getList(response))
    }

    override fun onError(message: String?) {
        myToast("获取数据失败")
        refreshLayout.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroyView()
    }

    /**
     * 获取适配器Adapter
     */
    abstract fun getSpecialAdapter(): BaseListAdapter<ITEMBEAN, ITEMVIEW>

    /**
     * 获取列表数据
     */
    abstract fun getList(response: RESPONSE?): List<ITEMBEAN>?

    /**
     * 获取Presenter
     */
    abstract fun getSpecialPresenter(): BaseListPresenter
}