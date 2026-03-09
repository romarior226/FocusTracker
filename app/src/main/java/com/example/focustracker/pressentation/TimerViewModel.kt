package com.example.focustracker.pressentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TimerViewModel() : ViewModel() {
    private var timerJob: Job? = null
    private val _timer = MutableStateFlow(0L)

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning
    val timer: StateFlow<Long> = _timer


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
