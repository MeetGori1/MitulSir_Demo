package com.example.unsplashdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unsplashdemo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding

    private val contract = registerForActivityResult(Contract()){
        binding.textView.text=it
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySecondBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textView.text = intent.getStringExtra("REQUEST_KEY")
        val intent = Intent()
        intent.putExtra("RESULT_KEY", "FROM SECOND ACTIVITY")
        setResult(RESULT_OK, intent)

        binding.btnContract.setOnClickListener{
            contract.launch("FROM SECOND ACTIVITY")
        }

        binding.btnWeatherApiCall.setOnClickListener {
            val intent=Intent(this,WeatherapiCall::class.java)
            startActivity(intent)
        }
    }
}