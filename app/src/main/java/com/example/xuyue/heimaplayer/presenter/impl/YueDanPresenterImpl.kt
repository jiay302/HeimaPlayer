package com.example.xuyue.heimaplayer.presenter.impl

import com.example.xuyue.heimaplayer.base.BaseListPresenter
import com.example.xuyue.heimaplayer.base.BaseView
import com.example.xuyue.heimaplayer.model.YueDanBean
import com.example.xuyue.heimaplayer.net.ResponseHandler
import com.example.xuyue.heimaplayer.net.YueDanRequest
import com.example.xuyue.heimaplayer.presenter.interf.YueDanPresenter

/**
 * @Date 2019/5/22 10:48 PM
 * @Author xuyue
 * @Description TODO
 */
class YueDanPresenterImpl(var yueDanView: BaseView<YueDanBean>?) : YueDanPresenter, ResponseHandler<YueDanBean> {

    override fun destroyView() {
        if (yueDanView != null) {
            yueDanView = null
        }
    }

    override fun loadData() {
        YueDanRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH, 0, this).execute()
    }

    override fun loadMore(offset: Int) {
        YueDanRequest(BaseListPresenter.TYPE_LOAD_MORE, offset, this).execute()
    }

    override fun onError(type: Int, msg: String?) {
        yueDanView?.onError(msg)
    }

    override fun onSuccess(type: Int, result: YueDanBean) {
        when (type) {
            BaseListPresenter.TYPE_INIT_OR_REFRESH -> yueDanView?.onLoadDataSuccess(result)
            BaseListPresenter.TYPE_LOAD_MORE -> yueDanView?.onLoadMoreSuccess(result)
        }
    }

}