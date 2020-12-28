package com.example.module_main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module_main.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initAction()

    }

    private fun initAction() {
        binding.moduleAppBtnToActivity.setOnClickListener {
            ARouter.getInstance().build("/test/a/activity").navigation()
        }
        binding.moduleAppBtnToNotification.setOnClickListener {
            ARouter.getInstance().build("/notification/activity").navigation()
        }
    }
}