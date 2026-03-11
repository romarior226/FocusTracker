package com.example.focustracker.pressentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {


    val context = getApplication<Application>()

    private var timerJob: Job? = null
    private val _timer = MutableStateFlow(0L)
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning
    val timer: StateFlow<Long> = _timer
    init {

        val prefs = getApplication<Application>()
            .getSharedPreferences("timer_prefs", Context.MODE_PRIVATE)
        _timer.value = prefs.getLong("seconds", 0L)
        val isRunningPrefs = prefs.getBoolean("is_running", false)

        if(isRunningPrefs) {
            startPause()
        }
    }

    fun startPause() {
        if (timerJob?.isActive == true) {
            timerJob?.cancel()
            _isRunning.value = false
        } else {
            timerJob = viewModelScope.launch {
                _isRunning.value = true
                while (true) {
                    delay(1000)
                    _timer.value++

                }
            }
        }
    }

    fun reset() {
        timerJob?.cancel()
        _isRunning.value = false
        _timer.value = 0
    }
}
