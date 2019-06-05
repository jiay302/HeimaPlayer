package com.example.xuyue.heimaplayer.util

import android.content.Intent
import android.support.v7.widget.Toolbar
import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.ui.activity.SettingActivity

/**
 * @Date 2019/5/13 4:13 PM
 * @Author xuyue
 * @Description 所有界面Toolbar管理类
 */
interface ToolbarManager {
    val toolbar: Toolbar

    /**
     * 初始化主界面中的Toolbar
     */
    fun initMainToolbar() {
        toolbar.title = "黑马影音"
        toolbar.inflateMenu(R.menu.main)
        toolbar.setOnMenuItemClickListener {
            // 跳转到设置界面
            when (it.itemId) {
                R.id.setting -> toolbar.context.startActivity(Intent(toolbar.context, SettingActivity::class.java))
            }
            true
        }
    }

    /**
     * 处理设置界面的Toolbar
     */
    fun initSettingToolbar() {
        toolbar.title = "设置界面"
    }
}