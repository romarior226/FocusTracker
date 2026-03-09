package com.example.focustracker.pressentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                Log.d("MyReceiver" , "MyReceiver is done ")
                Toast.makeText(context, "Час виконувати завдання", Toast.LENGTH_SHORT).show()
            }

        }
    }
}