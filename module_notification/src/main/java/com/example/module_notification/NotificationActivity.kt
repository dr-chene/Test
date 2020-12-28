package com.example.module_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.module_notification.databinding.ActivityNotificationBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@Route(path = "/notification/activity")
class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private val tapNotification by lazy {
        val intent = Intent(this, NotificationIntentActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.test)
            .setContentTitle("最简单的notification")
            .setContentText("只有小图标、标题、内容")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line...")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }
    private val replyNotification by lazy {
        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(REPLY_LABEL)
            build()
        }
        val conversion = Intent(this, ReplyBroadCastReceiver::class.java)
        val replyPendingIntent = PendingIntent.getBroadcast(applicationContext, 0, conversion, 0)
        val action =
            NotificationCompat.Action.Builder(R.drawable.test, REPLY_LABEL, replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build()
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.test)
            .setContentTitle("REPLY_TEST")
            .setContentText("test content")
            .addAction(action)
            .build()
    }
    private val progressNotificationBuilder by lazy {
        NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle("Picture Download")
            setContentText("Download in progress")
            setSmallIcon(R.drawable.test)
            priority = NotificationCompat.PRIORITY_LOW
            setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)

        initNotification()
        initAction()

    }

    private fun initNotification() {
        createNotificationChannel()
    }

    private fun initAction() {
        binding.moduleNotificationBtnSendTapNotification.setOnClickListener {
            sendTapNotification()
        }
        binding.moduleNotificationBtnSendReplayNotification.setOnClickListener {
            sendReplyNotification()
        }
        binding.moduleNotificationBtnSendProgressNotification.setOnClickListener {
            sendProgressNotification()
        }
    }

    private fun sendReplyNotification() {
        Snackbar.make(binding.root, "replyNotification", Snackbar.LENGTH_SHORT).show()
        with(NotificationManagerCompat.from(this)) {
            notify(REPLY_NOTIFICATION_ID, replyNotification)
        }
    }

    private fun sendTapNotification() {
        Snackbar.make(binding.root, "tapNotification", Snackbar.LENGTH_SHORT).show()
        with(NotificationManagerCompat.from(this)) {
            notify(TAP_NOTIFICATION_ID, tapNotification)
        }
    }

    private fun sendProgressNotification() {
        NotificationManagerCompat.from(this).apply {
            notify(PROGRESS_NOTIFICATION_ID, progressNotificationBuilder.build())
            CoroutineScope(Dispatchers.IO).launch {
                var progress = 0
                while (progress <= 100) {
                    delay(200)
                    progress += 5
                    progressNotificationBuilder.setProgress(PROGRESS_MAX, progress, false)
                    notify(PROGRESS_NOTIFICATION_ID, progressNotificationBuilder.build())
                }
                progressNotificationBuilder.setContentText("Download complete")
                    .setProgress(0, 0, false)
                notify(PROGRESS_NOTIFICATION_ID, progressNotificationBuilder.build())//第二次无法设置progress消失，待解决
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notifyManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notifyManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "demo"
        private const val TAP_NOTIFICATION_ID = 1
        private const val REPLY_NOTIFICATION_ID = 2
        private const val PROGRESS_NOTIFICATION_ID = 3
        private const val CHANNEL_NAME = "demo_channel_name"
        private const val CHANNEL_DESCRIPTION = "demo_channel_description"
        private const val KEY_TEXT_REPLY = "key_text_reply"
        private const val REPLY_LABEL = "reply"
        private const val PROGRESS_MAX = 100
        private const val PROGRESS_CURRENT = 0
    }

    class ReplyBroadCastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val reply = getMessageText(intent)
            Log.d("TAG_reply", "onReceive: $reply")
//            Snackbar.make(binding.root, result ?: "null", Snackbar.LENGTH_SHORT).show()
            replySuccess(context, reply)
        }

        private fun getMessageText(intent: Intent?): CharSequence? {
            return RemoteInput.getResultsFromIntent(intent)?.getCharSequence(KEY_TEXT_REPLY)
        }

        private fun replySuccess(context: Context?, reply: CharSequence?) {
            context ?: return
            val repliedNotification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.test)
                .setRemoteInputHistory(arrayOf(reply))//无效
                .setContentText("reply success!")
                .build()
            NotificationManagerCompat.from(context).apply {
                notify(REPLY_NOTIFICATION_ID, repliedNotification)
            }
        }
    }
}