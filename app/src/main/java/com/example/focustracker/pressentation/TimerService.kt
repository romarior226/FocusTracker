package com.example.focustracker.pressentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.content.edit

class TimerService : Service() {
    private val notificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }
    private var job: Job? = null


    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(
            NOTIFICATION_ID,
            createNotification("00:00")
        )
    }

    val scope = CoroutineScope(Dispatchers.IO)
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val seconds = intent?.getLongExtra(EXTRA_SECONDS, 0L) ?: 0L
        val command = intent?.getStringExtra(EXTRA_COMMAND) ?: COMMAND_START

        when (command) {
            COMMAND_START -> {
                job?.cancel()
                job = scope.launch {
                    getSharedPreferences("timer_prefs" ,Context.MODE_PRIVATE)
                        .edit { putBoolean("is_running", true )
                        }
                    var time = seconds
                    while (true) {
                        delay(1000)
                        time++
                        getSharedPreferences("timer_prefs" ,Context.MODE_PRIVATE)
                            .edit {
                                putLong("seconds", time)
                            }
                        notificationManager.notify(NOTIFICATION_ID, createNotification(time.toString()))
                    }
                }
            }
            COMMAND_PAUSE -> {
                getSharedPreferences("timer_prefs" ,Context.MODE_PRIVATE)
                    .edit { putBoolean("is_running", false )
                    }
                job?.cancel()
                notificationManager.notify(NOTIFICATION_ID, createNotification("⏸ $seconds"))
            }
            COMMAND_STOP -> {
                job?.cancel()
                stopSelf()
            }
        }
        return START_STICKY
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(notificationChannel)

    }

    private fun createNotification(seconds: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(NOTIFICATION_NAME)
            .setContentText(seconds)
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
            .build()


    }

    companion object {

        const val EXTRA_COMMAND = "extra_command"
        const val EXTRA_SECONDS = "Extra sec"
        const val CHANNEL_ID = "channel_id"
        const val CHANNEL_NAME = "channel_name"
        const val NOTIFICATION_ID = 1

        const val COMMAND_START = "start"
        const val COMMAND_PAUSE = "pause"

        const val COMMAND_STOP = "stop"
        const val NOTIFICATION_NAME = "Time service"
        fun newIntent(context: Context, seconds: Long, command: String): Intent {
            return Intent(context, TimerService::class.java).apply {
                putExtra(EXTRA_SECONDS, seconds)
                putExtra(EXTRA_COMMAND, command)
            }
        }
    }

}