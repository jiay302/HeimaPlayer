package com.example.xuyue.heimaplayer.ui.activity

import android.support.v7.widget.Toolbar
import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.base.BaseActivity
import com.example.xuyue.heimaplayer.util.FragmentUtil
import com.example.xuyue.heimaplayer.util.ToolbarManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find

class MainActivity : BaseActivity(), ToolbarManager {

    // 惰性加载
    override val toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        initMainToolbar()
    }

    override fun initListener() {
        // 设置tab切换监听
        bottomBar.setOnTabSelectListener {
            val transaction = supportFragmentManager.beginTransaction()
            FragmentUtil.fragmentUtil.getFragment(it)?.let { current ->
                transaction.replace(R.id.container,
                    current, it.toString())
            }
            transaction.commit()
        }
    }

}
