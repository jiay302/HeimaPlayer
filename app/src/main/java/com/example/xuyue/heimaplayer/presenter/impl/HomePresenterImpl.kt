package com.example.xuyue.heimaplayer.presenter.impl

import com.example.xuyue.heimaplayer.base.BaseListPresenter
import com.example.xuyue.heimaplayer.base.BaseView
import com.example.xuyue.heimaplayer.net.HomeRequest
import com.example.xuyue.heimaplayer.net.ResponseHandler
import com.example.xuyue.heimaplayer.presenter.interf.HomePresenter
import com.itheima.player.model.bean.HomeItemBean

/**
 * @Date 2019/5/21 6:44 PM
 * @Author xuyue
 * @Description
 */
class HomePresenterImpl(var homeView: BaseView<List<HomeItemBean>>?) : HomePresenter, ResponseHandler<List<HomeItemBean>> {

    /**
     * 解绑view和presenter
     */
    override fun destroyView() {
        if (homeView != null) {
            homeView = null
        }
    }

    /**
     * 初始加载数据
     */
    override fun loadData() {
        // 定义request并发送
        HomeRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH, 0,this).execute()
    }

    /**
     * 加载更多数据
     */
    override fun loadMore(offset: Int) {
        // 定义request并发送
        HomeRequest(BaseListPresenter.TYPE_LOAD_MORE, offset, this).execute()
    }

    override fun onError(type: Int, msg: String?) {
        homeView?.onError(msg)
    }

    override fun onSuccess(type: Int, result: List<HomeItemBean>) {
        when (type) {
            BaseListPresenter.TYPE_INIT_OR_REFRESH -> homeView?.onLoadDataSuccess(result)
            BaseListPresenter.TYPE_LOAD_MORE -> homeView?.onLoadMoreSuccess(result)
        }
    }

}