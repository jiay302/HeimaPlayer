package com.example.xuyue.heimaplayer.base

/**
 * @Date 2019/5/23 12:23 PM
 * @Author xuyue
 * @Description 所有view接口的基类
 */
interface BaseView<T> {
    fun onError(message: String?)
    fun onLoadDataSuccess(response: T?)
    fun onLoadMoreSuccess(response: T?)
}