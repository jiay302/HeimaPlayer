package com.example.xuyue.heimaplayer.net

import com.example.xuyue.heimaplayer.util.ThreadUtil
import okhttp3.*
import java.io.IOException

/**
 * @Date 2019/5/21 9:09 PM
 * @Author xuyue
 * @Description 发送网络请求类
 */
class NetManager private constructor(){

    private val client by lazy { OkHttpClient() }

    companion object {
        val manager by lazy { NetManager() }
    }

    /**
     * 发送网络请求
     */
    fun <T> sendRequest(req: MRequest<T>) {

        val request = Request.Builder()
            .url(req.url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                ThreadUtil.runOnMainThread(Runnable {
                    req.handler.onError(req.type, e.message)
                })
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body()?.string()
                val parseResult = req.parseResult(result)
                ThreadUtil.runOnMainThread(Runnable {
                    req.handler.onSuccess(req.type, parseResult)
                })
            }

        })
    }
}