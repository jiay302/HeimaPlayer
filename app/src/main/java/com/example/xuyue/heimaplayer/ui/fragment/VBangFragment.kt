package com.example.xuyue.heimaplayer.ui.fragment

import android.Manifest
import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.View
import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.adapter.VBangAdapter
import com.example.xuyue.heimaplayer.ui.activity.AudioPlayerActivity
import com.example.xuyue.heimaplayer.base.BaseFragment
import com.example.xuyue.heimaplayer.util.CursorUtil
import com.itheima.player.model.AudioBean
import kotlinx.android.synthetic.main.fragment_vbang.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * @Date 2019/5/13 5:43 PM
 * @Author xuyue
 * @Description V榜 Fragment
 */
class VBangFragment : BaseFragment() {

    var adapter: VBangAdapter? = null

//    val handler = MyHandler()

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_vbang, null)
    }

    override fun initData() {

        //检查权限是否已经申请
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 进行授权
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            loadData()
        }

    }

    private fun loadData() {

        // 开启单独线程查询音乐数据
//        Thread(Runnable {
//            // 加载音乐数据
//            val resolver = context!!.contentResolver
//            val cursor = resolver.query(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                arrayOf(MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST),
//                null, null, null
//            )
//            val msg = Message.obtain()
//            msg.obj = cursor
//            handler.sendMessage(msg)
//        }).start()

        // asynctask
//        AudioTask().execute(context!!.contentResolver)

        // AsyncQueryHandler
        val handler = MyAsyncQueryHandler(context!!.contentResolver)
        handler.startQuery(0, adapter, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST),
            null, null, null)
    }

    override fun initListener() {

        adapter = VBangAdapter(context, null)
        listView.adapter = adapter

        // 条目点击事件
        listView.setOnItemClickListener { parent, view, position, id ->
            val cursor = adapter!!.getItem(position) as Cursor
            val list = AudioBean.getAudioBeans(cursor)
            startActivity<AudioPlayerActivity>("list" to list, "position" to position)
        }
    }

    class MyHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            msg?.let {
                val cursor = msg.obj as Cursor
                CursorUtil.logCursor(cursor)
            }
        }
    }

    /**
     * 音乐查询异步任务
     */
    class AudioTask : AsyncTask<ContentResolver, Void, Cursor>() {

        override fun doInBackground(vararg params: ContentResolver?): Cursor? {
            // 加载音乐数据
            val resolver = params[0]
            return resolver?.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST),
                null, null, null
            )
        }

        override fun onPostExecute(result: Cursor?) {
            CursorUtil.logCursor(result)
        }

    }

    class MyAsyncQueryHandler(resolver: ContentResolver) : AsyncQueryHandler(resolver) {
        override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
            // 查询完成的回调  主线程中
            CursorUtil.logCursor(cursor)
            // 设置数据源
            // 刷新列表
            (cookie as VBangAdapter).swapCursor(cursor)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 关闭Cursor
        adapter?.changeCursor(null)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initData()
                } else {
                    myToast("权限被拒绝了")
                }
            }
        }
    }
}