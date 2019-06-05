package com.example.xuyue.heimaplayer.widget

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.example.xuyue.heimaplayer.R

/**
 * @Date 2019/5/30 5:26 PM
 * @Author xuyue
 * @Description TODO
 */
class PlayListPopupWindow(context: Context, adapter: BaseAdapter, listener: AdapterView.OnItemClickListener, val window: Window) : PopupWindow() {

    var alpha: Float = 0f

    init {
        // 设置布局
        contentView = LayoutInflater.from(context).inflate(R.layout.popup_playlist, null, false)

        // 设置宽度和高度
        width = ViewGroup.LayoutParams.MATCH_PARENT
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        manager.defaultDisplay.getSize(point)
        height = point.y * 3 / 5

        isFocusable = true // 设置可以获取焦点
        isOutsideTouchable = true // 设置其他地方可以获取焦点
        setBackgroundDrawable(ColorDrawable()) // 响应返回按钮

        // 获取listView
        val listView = contentView.findViewById<ListView>(R.id.listView)
        listView.adapter = adapter
        listView.onItemClickListener = listener

        // 动画
        animationStyle = R.style.popup

        // 记录当前窗体的透明度
        alpha = window.attributes.alpha
    }

    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        // 窗体显示
        val attributes = window.attributes
        attributes.alpha = 0.3f
        window.attributes = attributes
    }

    override fun dismiss() {
        super.dismiss()
        // 窗体隐藏
        val attributes = window.attributes
        attributes.alpha = alpha
        window.attributes = attributes
    }
}