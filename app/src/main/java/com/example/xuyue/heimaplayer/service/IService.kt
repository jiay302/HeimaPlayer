package com.example.xuyue.heimaplayer.service

import com.itheima.player.model.AudioBean

/**
 * @Date 2019/5/29 7:09 PM
 * @Author xuyue
 * @Description TODO
 */
interface IService {
    fun updatePlayState()
    fun isPlaying(): Boolean?
    fun getDuration(): Int
    fun getProgress(): Int
    fun seekTo(progress: Int)
    fun updatePlayMode()
    fun getPlayMode(): Int
    fun playPre()
    fun playNext()
    fun getPlayList(): List<AudioBean>?
    fun playPosition(position: Int)
}