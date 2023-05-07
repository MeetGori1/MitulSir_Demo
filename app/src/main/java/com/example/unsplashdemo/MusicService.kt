package com.example.unsplashdemo

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startForegroundService

class MusicService : Service() {
    var mp: MediaPlayer? = null
    private val CHANNEL_ID = "ForegroundService Kotlin"

    companion object {
        fun startServiceMusic(context: Context, message: String) {
            val startIntent = Intent(context, MusicService::class.java)
            startIntent.putExtra("inputExtra", message)
            startForegroundService(context, startIntent)

        }
        fun stopServiceMusic(context: Context) {
            val stopIntent = Intent(context, MusicService::class.java)
            context.stopService(stopIntent)
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

//        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Foreground Service Kotlin Example")
//            .setContentText("Water")
//            .setSmallIcon(R.drawable.baseline_close_24)
//            .build()

//        startForeground(1, notification)
        return START_NOT_STICKY
    }

    override fun onCreate() {
//        startForeground(0,)
        mp = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        mp?.isLooping = true
        mp?.start()
        super.onCreate()
    }


    override fun onDestroy() {
        mp?.stop()
        startServiceMusic(this,"vds")
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
       return null
    }


}