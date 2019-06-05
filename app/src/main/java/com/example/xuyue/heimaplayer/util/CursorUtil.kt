package com.example.xuyue.heimaplayer.util

import android.database.Cursor

/**
 * @Date 2019/5/23 10:52 PM
 * @Author xuyue
 * @Description Cursor打印类
 */
object CursorUtil {

    fun logCursor(cursor: Cursor?) {
        cursor?.let {
            // 将cursor游标复位
            it.moveToPosition(-1)
            while (it.moveToNext()) {
                for (index in 0 until it.columnCount) {
                    println("key=${it.getColumnName(index)} -- value=${it.getString(index)}")
                }
            }
        }
    }
}