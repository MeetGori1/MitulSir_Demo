package com.example.unsplashdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unsplashdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener{
            val intent=Intent(this,SecondActivity::class.java)
            intent.putExtra("REQUEST_KEY","MITUL SIR APP DEMO ")
            startActivityForResult(intent,111)


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==111){
            binding.textView2.text=data?.getStringExtra("RESULT_KEY")
            Toast.makeText(this, requestCode.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}