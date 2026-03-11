package com.example.focustracker.pressentation

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.focustracker.R
import com.example.focustracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val chargingReceiver = MyReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MAIN_Activity", "registerReceiver ")
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment).navController
        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        Log.d("MAIN_Activity", "registerReceiver called")
        val filter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(chargingReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(chargingReceiver)
    }
}