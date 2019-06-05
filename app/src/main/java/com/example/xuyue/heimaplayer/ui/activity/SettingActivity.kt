package com.example.xuyue.heimaplayer.ui.activity

import android.support.v7.widget.Toolbar
import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.base.BaseActivity
import com.example.xuyue.heimaplayer.util.ToolbarManager
import org.jetbrains.anko.find

/**
 * @Date 2019/5/13 4:33 PM
 * @Author xuyue
 * @Description 设置界面
 */
class SettingActivity : BaseActivity(), ToolbarManager {

    // 惰性加载
    override val toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initData() {
        initSettingToolbar()
    }
}
