package com.example.xuyue.heimaplayer.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.adapter.PopupAdapter
import com.example.xuyue.heimaplayer.base.BaseActivity
import com.example.xuyue.heimaplayer.service.AudioService
import com.example.xuyue.heimaplayer.service.IService
import com.example.xuyue.heimaplayer.util.StringUtil
import com.example.xuyue.heimaplayer.widget.PlayListPopupWindow
import com.itheima.player.model.AudioBean
import kotlinx.android.synthetic.main.activity_music_player_bottom.*
import kotlinx.android.synthetic.main.activity_music_player_middle.*
import kotlinx.android.synthetic.main.activity_music_player_top.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AudioPlayerActivity : BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemClickListener {
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        iService?.playPosition(position)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        // 进度改变回调
        if (!fromUser) return
        iService?.seekTo(progress)
        updateProgress(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        // 手指触摸回调
        iService?.updatePlayState()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        // 手指离开回调
        iService?.updatePlayState()
    }

    private val conn by lazy { AudioConnection() }
    private var audioBean: AudioBean? = null
    private var animationDrawable: AnimationDrawable? = null
    private var duration: Int = 0
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MSG_PROGRESS -> startUpdateProgress()
            }
        }
    }
    val MSG_PROGRESS = 0

    override fun initListener() {
        // 播放状态切换
        state.setOnClickListener(this)
        back.setOnClickListener(this)
        progress_sk.setOnSeekBarChangeListener(this)
        mode.setOnClickListener(this)
        pre.setOnClickListener(this)
        next.setOnClickListener(this)
        playlist.setOnClickListener(this)
        // 歌词拖动进度监听
        lyricView.setProgressListener {
            iService?.seekTo(it)
            updateProgress(it)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }

    override fun initData() {
        // 注册EventBus
        EventBus.getDefault().register(this)
        // 通过AudioService播放音乐
        intent.setClass(this, AudioService::class.java)
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
        startService(intent)

    }

    var iService: IService? = null

    inner class AudioConnection : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iService = service as IService
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        // 手动解绑服务
        unbindService(conn)

        // 反注册EventBus
        EventBus.getDefault().unregister(this)

        // 清空handler所有的消息
        handler.removeCallbacksAndMessages(null)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(audioBean: AudioBean) {
        // 设置歌曲播放名称
//        lyricView.setSongName(audioBean.display_name)

        // 接收端
        this.audioBean = audioBean
        // 界面更新
        audio_title.text = audioBean.display_name
        artist.text = audioBean.artist
        updatePlayStateBtn()

        animationDrawable = audio_anim.drawable as AnimationDrawable
        animationDrawable?.start() // 动画播放

        // 进度相关
        duration = iService?.getDuration() ?: 0
        lyricView.setSongDuration(duration )
        progress_sk.max = duration
        startUpdateProgress()

        updatePlayModeBtn()
    }

    private fun startUpdateProgress() {
        // 获取当前进度
        val progress = iService?.getProgress() ?: 0
        // 更新进度数据
        updateProgress(progress)
        // 定时获取进度
        handler.sendEmptyMessageDelayed(MSG_PROGRESS, 1000)
    }

    // 根据当前进度数据更新界面
    private fun updateProgress(pro: Int) {
        progress.text = StringUtil.parseDuration(pro) + "/" + StringUtil.parseDuration(duration)
        progress_sk.progress = pro
        // 更新歌词播放进度
        lyricView.updateProgress(pro)
    }

    // 按钮的点击事件
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.state -> updatePlayState()
            R.id.back -> finish()
            R.id.mode -> updatePlayMode()
            R.id.pre -> iService?.playPre()
            R.id.next -> iService?.playNext()
            R.id.playlist -> showPlayList()
        }
    }

    // 展示播放列表
    private fun showPlayList() {
        val list:List<AudioBean>? = iService?.getPlayList()
        list?.let {
            val popupWindow = PlayListPopupWindow(this, PopupAdapter(it), this, window)
            popupWindow.showAsDropDown(audio_player_bottom, 0, -audio_player_bottom.height)
        }
    }

    // 更新播放模式
    private fun updatePlayMode() {
        iService?.updatePlayMode()
        updatePlayModeBtn()
    }

    private fun updatePlayModeBtn() {
        iService?.let {
            when (it.getPlayMode()) {
                AudioService.MODE_ALL -> mode.setImageResource(R.drawable.selector_btn_playmode_order)
                AudioService.MODE_SINGLE -> mode.setImageResource(R.drawable.selector_btn_playmode_single)
                AudioService.MODE_RANDOM -> mode.setImageResource(R.drawable.selector_btn_playmode_random)
            }
        }
    }

    // 更新播放状态
    private fun updatePlayState() {
        // 更新播放状态
        iService?.updatePlayState()

        // 更新图标
        updatePlayStateBtn()
    }

    // 根据播放状态更新图标
    private fun updatePlayStateBtn() {
        // 获取当前播放状态
        val isPlaying = iService?.isPlaying()
        // 根据状态更新图标
        isPlaying?.let {
            if (isPlaying) {
                state.setImageResource(R.drawable.selector_btn_audio_play)
                animationDrawable?.start()
                // 开始更新进度
                handler.sendEmptyMessageDelayed(MSG_PROGRESS, 1000)
            } else {
                state.setImageResource(R.drawable.selector_btn_audio_pause)
                animationDrawable?.stop()

                // 停止更新进度
                handler.removeMessages(MSG_PROGRESS)
            }
        }
    }
}
