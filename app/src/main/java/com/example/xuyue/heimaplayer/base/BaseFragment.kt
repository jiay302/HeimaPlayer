package com.example.xuyue.heimaplayer.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

/**
 * @Date 2019/5/12 11:48 PM
 * @Author xuyue
 * @Description 所有Fragment的基类
 */
abstract class BaseFragment: Fragment(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    /**
     * Fragment初始化
     */
    protected open fun init() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView()
    }

    /**
     * 获取布局View
     */
    abstract fun initView(): View?

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
        initData()
    }

    /**
     * 设置一些Listener
     */
    protected open fun initListener() {

    }

    /**
     * 初始化数据
     */
    protected open fun initData() {

    }

    fun myToast(msg: String) {
        context?.runOnUiThread { toast(msg) }
    }

}