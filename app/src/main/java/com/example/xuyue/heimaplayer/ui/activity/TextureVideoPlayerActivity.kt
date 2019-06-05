package com.example.xuyue.heimaplayer.ui.activity

import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.view.Surface
import android.view.TextureView
import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.base.BaseActivity
import com.example.xuyue.heimaplayer.model.VideoPlayBean
import kotlinx.android.synthetic.main.activity_video_player_texture.*

class TextureVideoPlayerActivity : BaseActivity(), TextureView.SurfaceTextureListener {

    private val mediaPlayer by lazy { MediaPlayer() }

    private var videoPlayBean: VideoPlayBean? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_video_player_texture
    }

    override fun initData() {
        // 获取数据
        videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
        textureView.surfaceTextureListener = this
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        // view大小变化
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
        // 视图更新
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        // 视图销毁
        mediaPlayer.stop()
        mediaPlayer.release()
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        // 视图可用
        videoPlayBean?.let {
            mediaPlayer.setDataSource(it.url)
            mediaPlayer.setSurface(Surface(surface))
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
                textureView.rotation = 100f
            }
        }
    }
}
