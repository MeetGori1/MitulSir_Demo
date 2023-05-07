package com.example.unsplashdemo

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class Contract(): ActivityResultContract<String,String>() {
    override fun createIntent(context: Context, input: String): Intent {
        val intent=Intent(context,contractActivity::class.java)
        intent.putExtra("REQUEST_KEY_FOR_CONTRACT",input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        return "FROM CONTRACT ACTIVITY"
    }
}