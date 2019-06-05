package com.example.xuyue.heimaplayer.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.xuyue.heimaplayer.widget.LoadMoreView

/**
 * @Date 2019/5/23 12:40 PM
 * @Author xuyue
 * @Description 所有适配器的基类
 */
abstract class BaseListAdapter<ITEMBEAN, ITEMVIEW : View> : RecyclerView.Adapter<BaseListAdapter.BaseListHolder>()  {

    private val list = ArrayList<ITEMBEAN>()

    /**
     * 更新数据操作
     */
    fun updateList(list: List<ITEMBEAN>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(it)
            notifyDataSetChanged()
        }
    }

    /**
     * 加载更多数据
     */
    fun loadMore(list: List<ITEMBEAN>?) {
        list?.let {
            this.list.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseListHolder {
        return if (p1 == 1) {
            BaseListHolder((LoadMoreView(p0.context)))
        } else {
            BaseListHolder(getItemView(p0.context))
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(p0: BaseListHolder, p1: Int) {

        if (p1 == list.size) return

        val data = list[p1]
        val itemView = p0.itemView as ITEMVIEW
        refreshItemView(itemView, data)

        // 设置条目点击事件
        itemView.setOnClickListener {
            // 条目点击事件
            listener?.invoke(data)
        }
    }

    // 定义函数类型变量
    private var listener: ((itemBean: ITEMBEAN) -> Unit)? = null

    fun setMyListener(listener: ((itemBean: ITEMBEAN) -> Unit)) {
        this.listener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size) {
        // 最后一条
            1
        } else{
            // 普通条目
            0
        }
    }

    class BaseListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * 刷新条目view
     */
    abstract fun refreshItemView(itemView: ITEMVIEW, data: ITEMBEAN)

    /**
     * 获取条目view
     */
    abstract fun getItemView(context: Context?): ITEMVIEW
}