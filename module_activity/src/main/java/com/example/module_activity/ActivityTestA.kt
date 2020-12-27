package com.example.module_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.module_activity.databinding.ActivityTestABinding

@Route(path = "/test/a/activity")
class ActivityTestA : AppCompatActivity() {

    private lateinit var binding: ActivityTestABinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_a)
        Log.d("TAG_activity", "onCreate: A")

        initAction()
    }

    private fun initAction() {
        binding.moduleActivityTestABtnStart.setOnClickListener {
            startActivity(Intent(this, ActivityTestB::class.java))
        }
        binding.moduleActivityTestATvShow.text = this.taskId.toString()
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG_activity", "onStart: A")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG_activity", "onResume: A")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG_activity", "onPause: A")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG_activity", "onStop: A")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG_activity", "onDestroy: A")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TAG_activity", "onRestart: A")
    }
}