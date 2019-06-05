package com.example.xuyue.heimaplayer.util

import android.os.Environment
import java.io.File

/**
 * @Date 2019/6/5 4:18 PM
 * @Author xuyue
 * @Description 歌词加载类
 */
object LyricLoader {
    private val dir = File(Environment.getExternalStorageDirectory(), "Download/Lyric")
    fun LoadLyricFile(displayName: String): File {
        return File(dir, "$displayName.lrc")
    }
}