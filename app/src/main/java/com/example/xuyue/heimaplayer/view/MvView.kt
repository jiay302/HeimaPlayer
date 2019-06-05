package com.example.xuyue.heimaplayer.view

import com.example.xuyue.heimaplayer.model.MvAreaBean

/**
 * @Date 2019/5/23 1:34 PM
 * @Author xuyue
 * @Description TODO
 */
interface MvView {
    fun onError(msg: String?)
    fun onSuccess(result: List<MvAreaBean>)
}