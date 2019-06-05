package com.example.xuyue.heimaplayer.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.ui.activity.AudioPlayerActivity
import com.example.xuyue.heimaplayer.ui.activity.MainActivity
import com.itheima.player.model.AudioBean
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * @Date 2019/5/29 6:23 PM
 * @Author xuyue
 * @Description TODO
 */
class AudioService : Service() {

    var mediaPlayer: MediaPlayer? = null
    var list: ArrayList<AudioBean>? = null
    var position: Int = -2
    private val binder by lazy { AudioBinder() }
    val sp: SharedPreferences by lazy { getSharedPreferences("config", Context.MODE_PRIVATE) }
    var manager: NotificationManager? = null
    var notification: Notification? = null

    companion object {
        const val MODE_ALL = 1
        const val MODE_SINGLE = 2
        const val MODE_RANDOM = 3
        const val FROM_PRE = 1
        const val FROM_NEXT = 2
        const val FROM_STATE = 3
        const val FROM_CONTENT = 4
    }

    var mode = MODE_ALL

    override fun onCreate() {
        super.onCreate()
        mode = sp.getInt("mode", MODE_ALL)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 判断进入service的方法
        when (intent?.getIntExtra("from", -1)) {
            FROM_PRE -> {binder.playPre()}
            FROM_NEXT -> {binder.playNext()}
            FROM_CONTENT -> {binder.notifyUpdateUI()}
            FROM_STATE -> {
                binder.updatePlayState()
            }
            else -> {
                // 获取集合以及position
                val pos = intent?.getIntExtra("position", -1) ?: -1
                if (pos != position) {
                    position = pos
                    list = intent?.getParcelableArrayListExtra<AudioBean>("list")
                    // 开始播放音乐
                    binder.playItem()
                } else {
                    binder.notifyUpdateUI()
                }
            }
        }

        // START_STICKY 粘性的 服务被强制杀死之后 会尝试重新启动服务 不会传递原来的intent
        // START_NOT_STICKY 非粘性的 服务被强制杀死之后 不会尝试重新启动服务
        // START_REDELIVER_INTENT 服务被强制杀死之后 会尝试重新启动服务 会传递原来的intent
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    inner class AudioBinder : Binder(), IService, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

        // 跳转到当前进度进行播放
        override fun seekTo(progress: Int) {
            mediaPlayer?.seekTo(progress)
        }

        // 更新播放状态
        override fun updatePlayState() {
            // 获取当前播放状态
            val isPlaying = isPlaying()
            // 切换播放状态
            isPlaying?.let {
                if (isPlaying) {
                    pause()
                } else {
                    start()
                }
            }
        }

        private fun start() {
            mediaPlayer?.start()
            notifyUpdateUI()

            // 更新通知栏按钮状态
            notification?.contentView?.setImageViewResource(R.id.state, R.mipmap.btn_audio_play_normal)
            manager?.notify(1, notification)
        }

        private fun pause() {
            mediaPlayer?.pause()
            notifyUpdateUI()

            // 更新通知栏按钮状态
            notification?.contentView?.setImageViewResource(R.id.state, R.mipmap.btn_audio_pause_normal)
            manager?.notify(1, notification)
        }

        override fun isPlaying(): Boolean? {
            return mediaPlayer?.isPlaying
        }

        // 获取总进度
        override fun getDuration(): Int {
            return mediaPlayer?.duration ?: 0
        }

        // 获取当前播放进度
        override fun getProgress(): Int {
            return mediaPlayer?.currentPosition ?: 0
        }

        override fun onPrepared(mp: MediaPlayer?) {
            mediaPlayer?.start()
            // 通知界面更新
            notifyUpdateUI()
            // 显示通知
            showNotification()
        }

        private fun showNotification() {
            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notification = getNotification()
            manager?.notify(1, notification)
        }

        private fun getNotification(): Notification {

            return NotificationCompat.Builder(this@AudioService)
                .setTicker("正在播放歌曲${list?.get(position)?.display_name}")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(getRemoteViews())
                .setContentIntent(getPendingIntent())
                .setOngoing(true)
                .build()
        }

        private fun getPendingIntent(): PendingIntent {
            val intentM = Intent(this@AudioService, MainActivity::class.java)
            val intentA = Intent(this@AudioService, AudioPlayerActivity::class.java)
            intentA.putExtra("from", FROM_CONTENT)
            val intents = arrayOf(intentM, intentA)
            return PendingIntent.getActivities(this@AudioService, 1, intents, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        private fun getRemoteViews(): RemoteViews {
            val remoteViews = RemoteViews(packageName, R.layout.notification)
            remoteViews.setTextViewText(R.id.title, list?.get(position)?.display_name)
            remoteViews.setTextViewText(R.id.artist, list?.get(position)?.artist)
            // 处理按钮点击事件
            remoteViews.setOnClickPendingIntent(R.id.pre, getPrePendingIntent())
            remoteViews.setOnClickPendingIntent(R.id.state, getStatePendingIntent())
            remoteViews.setOnClickPendingIntent(R.id.next, getNextPendingIntent())
            return remoteViews
        }

        private fun getNextPendingIntent(): PendingIntent {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from", FROM_PRE)
            return PendingIntent.getService(this@AudioService, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        private fun getStatePendingIntent(): PendingIntent {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from", FROM_STATE)
            return PendingIntent.getService(this@AudioService, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        private fun getPrePendingIntent(): PendingIntent {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from", FROM_NEXT)
            return PendingIntent.getService(this@AudioService, 4, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        fun notifyUpdateUI() {
            // 发送端
            EventBus.getDefault().post(list?.get(position))
        }

        // 播放音乐
        fun playItem() {
            if (mediaPlayer != null) {
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            }
            mediaPlayer = MediaPlayer()
            mediaPlayer?.let {
                it.setDataSource(list?.get(position)?.data)
                it.prepareAsync()
                it.setOnPreparedListener(this)
                it.setOnCompletionListener(this)
            }
        }

        override fun onCompletion(mp: MediaPlayer?) {
            // 播放结束监听
            autoPlayNext()
        }

        // 根据播放模式自动播放下一曲
        private fun autoPlayNext() {
            when (mode) {
                MODE_ALL -> list?.let { position = (position + 1) % it.size }
                MODE_RANDOM -> list?.let { position = Random().nextInt(it.size) }
            }
            playItem()
        }

        override fun updatePlayMode() {
            when (mode) {
                MODE_ALL -> mode = MODE_SINGLE
                MODE_SINGLE -> mode = MODE_RANDOM
                MODE_RANDOM -> mode = MODE_ALL
            }

            sp.edit().putInt("mode", mode).apply()
        }

        override fun getPlayMode(): Int {
            return mode
        }

        override fun playPre() {
            list?.let {
                when (mode) {
                    MODE_RANDOM -> position = Random().nextInt(it.size)
                    else -> {
                        if (position == 0) {
                            position = it.size - 1
                        } else {
                            position--
                        }
                    }

                }
                playItem()
            }
        }

        override fun playNext() {
            list?.let {
                position = when (mode) {
                    MODE_RANDOM -> Random().nextInt(it.size)
                    else -> (position + 1) % it.size
                }
                playItem()
            }
        }

        override fun getPlayList(): List<AudioBean>? {
            return list
        }

        override fun playPosition(position: Int) {
            this@AudioService.position = position
            playItem()
        }
    }
}