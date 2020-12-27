package com.example.module_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.module_activity.databinding.ActivityTestBBinding

class ActivityTestB : AppCompatActivity() {

    private lateinit var binding: ActivityTestBBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_b)
        Log.d("TAG_activity", "onCreate: B")

        initAction()
    }

    private fun initAction() {
        binding.moduleActivityTestBBtnStart.setOnClickListener {
            startActivity(Intent(this, ActivityTestC::class.java))
        }
        binding.moduleActivityTestBTvShow.text = this.taskId.toString()
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG_activity", "onStart: B")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG_activity", "onResume: B")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG_activity", "onPause: B")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG_activity", "onStop: B")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG_activity", "onDestroy: B")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TAG_activity", "onRestart: B")
    }
}