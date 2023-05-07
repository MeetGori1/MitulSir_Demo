package com.example.unsplashdemo.worker.get

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.unsplashdemo.WeatherapiCall


class apiWorkerService(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        Log.d("WorkerClass","It's Working")
        return Result.success()
    }
}