package com.example.xuyue.heimaplayer.base

/**
 * @Date 2019/5/23 12:28 PM
 * @Author xuyue
 * @Description 所有presenter的基类
 */
interface BaseListPresenter {

    companion object {
        const val TYPE_INIT_OR_REFRESH = 1
        const val TYPE_LOAD_MORE = 2
    }


    fun loadData()
    fun loadMore(offset: Int)

    //解绑presenter和view
    fun destroyView()

}