package com.example.xuyue.heimaplayer.util

import android.os.Handler
import android.os.Looper

/**
 * @Date 2019/5/13 7:52 PM
 * @Author xuyue
 * @Description 线程操作工具
 */
object ThreadUtil {

    val handler = Handler(Looper.getMainLooper())

    /**
     * 运行在主线程中
     */
    fun runOnMainThread(runnable: Runnable) {
        handler.post(runnable)
    }
}