package com.example.xuyue.heimaplayer.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.model.LyricBean
import com.example.xuyue.heimaplayer.util.LyricLoader
import com.example.xuyue.heimaplayer.util.LyricUtil
import org.jetbrains.anko.doAsync

/**
 * @Date 2019/6/4 12:30 PM
 * @Author xuyue
 * @Description 自定义歌词控件
 */
class LyricView : View {

    private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) } // 抗锯齿
    private val list by lazy { ArrayList<LyricBean>() } // 歌词句子集合

    private var viewW = 0
    private var viewH = 0
    var bigSize = 0f
    var smallSize = 0f
    var white = 0
    var green = 0
    var lineHeight = 0

    private var centerLine = 0 // 居中行

    private var updateByPro = true

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        bigSize = resources.getDimension(R.dimen.bigSize)
        smallSize = resources.getDimension(R.dimen.smallSize)
        lineHeight = resources.getDimensionPixelOffset(R.dimen.lineHeight)
        white = resources.getColor(R.color.white)
        green = resources.getColor(R.color.green)
        paint.textAlign = Paint.Align.CENTER

        // 循环添加歌词bean
        for (i in 0 until 30) {
            list.add(LyricBean(2000 * i, "正在播放第${i}行歌词"))
        }
    }

    override fun onDraw(canvas: Canvas?) {

        if (list.size == 0) {
            drawSingleLine(canvas)
        } else {
            drawMultiLine(canvas)
        }
    }

    private var offsetY = 0f
    /**
     * 绘制多行居中文本
     */
    private fun drawMultiLine(canvas: Canvas?) {

        if (updateByPro) {
            // 求居中行偏移y
            var lineTime = 0 // 行可用时间
            lineTime = if (centerLine == list.size) {
                duration - list[centerLine].startTime
            } else {
                list[centerLine + 1].startTime - list[centerLine].startTime
            }
            val offsetTime = progress - list[centerLine].startTime // 偏移时间
            val offsetPercent = offsetTime / (lineTime.toFloat()) // 偏移百分比
            offsetY = lineHeight * offsetPercent
        }

        val centerText = list[centerLine].content
        val bounds = Rect()
        paint.getTextBounds(centerText, 0, centerText.length, bounds)
        val textH = bounds.height()
        // 居中行y坐标
        val centerY = (viewH + textH) / 2 - offsetY
        for ((index, value) in list.withIndex()) {
            if (index == centerLine) {
                // 绘制居中行
                paint.color = green
                paint.textSize = bigSize
            } else {
                // 其他行
                paint.color = white
                paint.textSize = smallSize
            }
            val curX = viewW / 2
            val curY = centerY + (index - centerLine) * lineHeight

            // 超出边界处理
            if (curY < 0) continue
            if (curY > viewH + lineHeight) break
            val currentText = value.content
            canvas?.drawText(currentText, curX.toFloat(), curY.toFloat(), paint)
        }
    }

    /**
     * 绘制单行居中文本
     */
    private fun drawSingleLine(canvas: Canvas?) {
        // 初始化paint字体大小和颜色
        paint.textSize = bigSize
        paint.color = green

        val text = "正在加载歌词..."
        // 求文本的宽度和高度
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val textW = bounds.width()
        val textH = bounds.height()
        // 计算绘制文本的左下角坐标
        // val x = (viewW - textW) / 2
        val x = viewW / 2
        val y = (viewH + textH) / 2
        // 绘制内容
        canvas?.drawText(text, x.toFloat(), y.toFloat(), paint)
    }

    /**
     * 布局之后执行
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewW = w
        viewH = h
    }

    private var progress = 0

    /**
     * 传递当前播放进度 实现歌词播放
     */
    fun updateProgress(progress: Int) {
        if (!updateByPro) return
        if (list.size == 0) return
        this.progress = progress
        // 获取居中行行号
        // 先判断是否是最后一行
        if (progress >= list[list.size - 1].startTime) {
            // progress>=最后一行开始时间
            // 最后一行居中
            centerLine = list.size - 1
        } else {
            // 其他行居中 循环遍历集合
            for (index in 0 until list.size) {
                // progress>=当前行开始时间&progress<下一行开始时间
                val curStartTime = list[index].startTime
                val nextStartTime = list[index + 1].startTime
                if (progress in curStartTime until nextStartTime) {
                    centerLine = index
                    break
                }
            }
        }
        // 重新绘制
        invalidate()
    }

    private var duration = 0

    /**
     * 设置当前播放可取总时长
     */
    fun setSongDuration(duration: Int) {
        this.duration = duration
    }

    fun setSongName(name: String) {
        doAsync {
            val file = LyricLoader.LoadLyricFile(name)
            val tempList = LyricUtil.parseLyric(file)
            list.clear()
            list.addAll(tempList)
        }
    }

    private var downY = 0f
    private var markY = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    updateByPro = false
                    downY = event.y
                    markY = offsetY
                }
                MotionEvent.ACTION_MOVE -> {
                    val endY = event.y
                    val offY = downY - endY
                    offsetY = markY + offY
                    // 偏移量大于行高 重新确定行高
                    if (Math.abs(offsetY) >= lineHeight) {
                        centerLine += (offsetY / lineHeight).toInt()
                        if(centerLine < 0) centerLine = 0 else if (centerLine > list.size - 1) centerLine = list.size -1
                        downY = endY
                        offsetY %= lineHeight
                        markY = offsetY

                        // 更新播放进度
                        listener?.invoke(list[centerLine].startTime)
                    }
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    updateByPro = true
                }
            }
        }
        return true
    }

    private var listener: ((progress: Int) -> Unit)? = null
    fun setProgressListener(listener: (progress: Int) -> Unit) {
        this.listener = listener
    }
}