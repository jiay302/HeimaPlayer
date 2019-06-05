package com.example.xuyue.heimaplayer.presenter.impl

import com.example.xuyue.heimaplayer.model.MvAreaBean
import com.example.xuyue.heimaplayer.net.MvAreaRequest
import com.example.xuyue.heimaplayer.net.ResponseHandler
import com.example.xuyue.heimaplayer.presenter.interf.MvPresenter
import com.example.xuyue.heimaplayer.view.MvView

/**
 * @Date 2019/5/23 1:35 PM
 * @Author xuyue
 * @Description TODO
 */
class MvPresenterImpl(var mvView: MvView) : MvPresenter, ResponseHandler<List<MvAreaBean>> {

    override fun loadData() {
        MvAreaRequest(this).execute()
    }

    override fun onError(type: Int, msg: String?) {
        mvView.onError(msg)
    }

    override fun onSuccess(type: Int, result: List<MvAreaBean>) {
        mvView.onSuccess(result)
    }
}