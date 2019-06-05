package com.example.servicedemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {

    var inten: Intent? = null
    val conn by lazy { MyConnection() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inten = Intent(this, MyService::class.java)
    }

    fun start(view: View) {
        startService(inten)
    }

    fun stop(view: View) {
        stopService(inten)
    }

    fun bind(view: View) {
        bindService(inten, conn, Context.BIND_AUTO_CREATE)
    }

    fun invoke(view: View) {
        iService?.callSayHello()
    }

    fun unbind(view: View) {
        unbindService(conn)
    }

    var iService: IService? = null
    inner class MyConnection : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iService = service as IService

        }

    }
}
