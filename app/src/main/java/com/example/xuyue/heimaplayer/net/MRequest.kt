package com.example.xuyue.heimaplayer.net

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType

/**
 * @Date 2019/5/21 9:06 PM
 * @Author xuyue
 * @Description 所有网络请求的基类
 */
open class MRequest<T>(val type: Int, val url: String, val handler: ResponseHandler<T>) {

    /**
     * 解析网络请求结果
     */
    fun parseResult(result: String?): T {
        val gson = Gson()
        // 获取泛型类型
        val type = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return gson.fromJson<T>(result, type)
    }

    /**
     * 发送网络请求
     */
    fun execute() {
        NetManager.manager.sendRequest(this)
    }
}