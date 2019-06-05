package com.example.xuyue.heimaplayer.util

import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.base.BaseFragment
import com.example.xuyue.heimaplayer.ui.fragment.HomeFragment
import com.example.xuyue.heimaplayer.ui.fragment.MvFragment
import com.example.xuyue.heimaplayer.ui.fragment.VBangFragment
import com.example.xuyue.heimaplayer.ui.fragment.YueDanFragment

/**
 * @Date 2019/5/13 5:48 PM
 * @Author xuyue
 * @Description 管理Fragment的util类 单例
 */
class FragmentUtil private constructor() { // 私有化构造方法

    private val homeFragment by lazy { HomeFragment() }
    private val mvFragment by lazy { MvFragment() }
    private val vbangFragment by lazy { VBangFragment() }
    private val yuedanFragment by lazy { YueDanFragment() }

    companion object {
        val fragmentUtil by lazy { FragmentUtil() }
    }

    /**
     * 根据tabId获取对应的Fragment
     */
    fun getFragment(tabId: Int): BaseFragment? {

        when(tabId) {
            R.id.tab_home -> return homeFragment
            R.id.tab_mv -> return mvFragment
            R.id.tab_vbang -> return vbangFragment
            R.id.tab_yuedan -> return yuedanFragment
        }
        return null
    }
}