package com.example.xuyue.heimaplayer.presenter.impl

import com.example.xuyue.heimaplayer.base.BaseListPresenter
import com.example.xuyue.heimaplayer.base.BaseView
import com.example.xuyue.heimaplayer.model.MvPagerBean
import com.example.xuyue.heimaplayer.model.VideosBean
import com.example.xuyue.heimaplayer.net.MvListRequest
import com.example.xuyue.heimaplayer.net.ResponseHandler
import com.example.xuyue.heimaplayer.presenter.interf.MvListPresenter

/**
 * @Date 2019/5/23 2:51 PM
 * @Author xuyue
 * @Description TODO
 */
class MvListPresenterImpl(var code: String, var mvListView: BaseView<List<VideosBean>>?) : MvListPresenter, ResponseHandler<MvPagerBean> {


    override fun loadData() {
        MvListRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH, code, 0, this).execute()
    }

    override fun loadMore(offset: Int) {
        MvListRequest(BaseListPresenter.TYPE_LOAD_MORE, code, offset, this).execute()
    }

    override fun destroyView() {
        if (mvListView != null) {
            mvListView = null
        }
    }

    override fun onError(type: Int, msg: String?) {
        mvListView?.onError(msg)
    }

    override fun onSuccess(type: Int, result: MvPagerBean) {
        when (type) {
            BaseListPresenter.TYPE_INIT_OR_REFRESH -> mvListView?.onLoadDataSuccess(result.videos)
            BaseListPresenter.TYPE_LOAD_MORE -> mvListView?.onLoadMoreSuccess(result.videos)
        }
    }
}