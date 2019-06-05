package com.example.xuyue.heimaplayer.ui.activity

import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.base.BaseActivity
import com.example.xuyue.heimaplayer.model.VideoPlayBean
import kotlinx.android.synthetic.main.activity_video_player_ijk.*

class IjkPlayerActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_video_player_ijk
    }

    override fun initData() {
        // 获取数据
        val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
        videoView.setVideoPath(videoPlayBean.url) // 异步准备
        videoView.setOnPreparedListener {
            videoView.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 停止播放
        videoView.stopPlayback()
    }
}
