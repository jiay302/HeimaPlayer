package com.example.xuyue.heimaplayer.ui.activity

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import cn.jzvd.Jzvd
import com.example.xuyue.heimaplayer.R
import com.example.xuyue.heimaplayer.base.BaseActivity
import com.example.xuyue.heimaplayer.model.VideoPlayBean
import kotlinx.android.synthetic.main.activity_video_player_jiaozi.*


class JiaoziPlayerActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_video_player_jiaozi
    }

    override fun initData() {
        // 应用外获取的数据
        val data = intent.data
        if (data == null) {
            // 获取数据
            val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
            jzVideo.setUp(videoPlayBean.url, videoPlayBean.title)
        } else{
            val path = getRealFilePath(this, data)
            val title = path?.substring(path.lastIndexOf("/") + 1 ,path.length)
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 进行授权
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                jzVideo.setUp(path, title)
            }
        }
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) return
        super.onBackPressed()
    }


    override fun onPause() {
        super.onPause()
        Jzvd.resetAllVideos()
    }

    private fun getRealFilePath(context: Context, uri: Uri?): String? {
        if (null == uri) return null
        val scheme = uri!!.scheme
        var data: String? = null
        if (scheme == null)
            data = uri!!.path
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri!!.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor!!.moveToFirst()) {
                    val index = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor!!.getString(index)
                    }
                }
                cursor!!.close()
            }
        }
        return data
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
