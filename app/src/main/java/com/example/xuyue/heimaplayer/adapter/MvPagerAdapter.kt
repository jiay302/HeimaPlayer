package com.example.xuyue.heimaplayer.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.xuyue.heimaplayer.model.MvAreaBean
import com.example.xuyue.heimaplayer.ui.fragment.MvPagerFragment

/**
 * @Date 2019/5/23 1:55 PM
 * @Author xuyue
 * @Description
 */
class MvPagerAdapter(val list: List<MvAreaBean>, fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(p0: Int): Fragment {
        val fragment = MvPagerFragment()
        val bundle = Bundle()
        bundle.putString("args", list[p0].code)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position].name
    }
}