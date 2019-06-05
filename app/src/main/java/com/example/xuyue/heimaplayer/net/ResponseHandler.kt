package com.example.xuyue.heimaplayer.net

/**
 * @Date 2019/5/21 9:04 PM
 * @Author xuyue
 * @Description 网络请求的接口回调
 */
interface ResponseHandler<T> {
    fun onError(type: Int, msg: String?)
    fun onSuccess(type: Int, result: T)
}