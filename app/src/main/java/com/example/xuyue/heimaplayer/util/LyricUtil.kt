package com.example.xuyue.heimaplayer.util

import com.example.xuyue.heimaplayer.model.LyricBean
import java.io.File
import java.nio.charset.Charset

/**
 * @Date 2019/6/5 3:51 PM
 * @Author xuyue
 * @Description 歌词解析
 */
object LyricUtil {

    fun parseLyric(file: File): List<LyricBean> {
        val list = ArrayList<LyricBean>()
        if (!file.exists()) {
            list.add(LyricBean(0, "歌词加载错误"))
        } else {
//            val bfr = BufferedReader(InputStreamReader(FileInputStream(file), "gbk"))
//            var line = bfr.readLine()
//            while (line != null) {
//                line = bfr.readLine()
//            }
            val lineList = file.readLines(Charset.forName("gbk")) // 读取歌词文件 返回每一行数据集合
            for (line in lineList) {
                val tempList = parseLine(line)
                list.addAll(tempList)
            }
        }
        list.sortBy { it.startTime }
        return list
    }

    /**
     * 解析一行歌词
     */
    private fun parseLine(line: String): List <LyricBean> {
        val list = ArrayList<LyricBean>()
        val arr = line.split("]")
        val content = arr[arr.size - 1]
        for (index in 0 until arr.size - 1) {
            val startTime = parseTime(arr[index])
            list.add(LyricBean(startTime, content))
        }
        return list
    }

    /**
     * 解析时间
     */
    private fun parseTime(s: String): Int {
        val timeS = s.substring(1)
        val list = timeS.split(":")
        var hour = 0
        var min = 0
        var sec = 0f
        if (list.size == 3) {
            hour = list[0].toInt() * 60 * 60 * 1000
            min = list[1].toInt() * 60 * 1000
            sec = list[2].toFloat() * 1000
        } else {
            min = list[0].toInt() * 60 * 1000
            sec = list[1].toFloat() * 1000
        }
        return (hour + min + sec).toInt()
    }
}