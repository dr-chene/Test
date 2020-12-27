package com.example.module_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.module_activity.databinding.ActivityTestCBinding

class ActivityTestC : AppCompatActivity() {

    private lateinit var binding: ActivityTestCBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_c)
        Log.d("TAG_activity", "onCreate: C")

        initAction()
    }

    private fun initAction() {
        binding.moduleActivityTestCBtnStart.setOnClickListener {
            startActivity(Intent(this, com.example.module_activity.ActivityTestB::class.java))
        }
        binding.moduleActivityTestCTvShow.text = this.taskId.toString()
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG_activity", "onStart: C")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG_activity", "onResume: C")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG_activity", "onPause: C")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG_activity", "onStop: C")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG_activity", "onDestroy: C")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TAG_activity", "onRestart: C")
    }
}