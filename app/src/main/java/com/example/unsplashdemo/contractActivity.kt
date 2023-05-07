package com.example.unsplashdemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.unsplashdemo.MusicService.Companion.startServiceMusic
import com.example.unsplashdemo.MusicService.Companion.stopServiceMusic
import com.example.unsplashdemo.databinding.ActivityContractBinding

class contractActivity : AppCompatActivity() {
    lateinit var binding: ActivityContractBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityContractBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        binding.textView3.text=intent.getStringExtra("REQUEST_KEY_FOR_CONTRACT")

        val intent = Intent()
        intent.putExtra("FROM CONTRACT ACTIVITY", "FROM SECOND ACTIVITY")

        binding.btnPexelApiCall.setOnClickListener {
            val intent = Intent(this, PexelApiCall::class.java)
            startActivity(intent)
        }

        binding.btnUnsplashApiCall.setOnClickListener {
            val intent = Intent(this, UnsplashApiActivity::class.java)
            startActivity(intent)
        }

        binding.btnUnsplashSearchApiCall.setOnClickListener {
            val intent = Intent(this, UnsplashSearchApiActivity::class.java)
            startActivity(intent)
        }

        binding.btnStartService.setOnClickListener {
//            val intent = Intent(this, MusicService::class.java)
//            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

            val intent = Intent(this, MusicService::class.java)
            startServiceMusic(this,"water")

        }

        binding.btnStopService.setOnClickListener {
            val intent = Intent(this, MusicService::class.java)
            stopServiceMusic(this)
        }


    }

/*    val serviceConnection = object : ServiceConnection {
        lateinit var service: MusicService

        override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
            val localBinder = iBinder as MusicService.MyLocalBinder
            val service = localBinder.getServiceIntance()
            service.play()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            service.Stop()
        }

    }*/
}