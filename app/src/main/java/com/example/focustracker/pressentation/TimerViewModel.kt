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
    val timer: StateFlow<Long> = _timer


    fun startPause() {
        if (timerJob?.isActive == true) {
            timerJob?.cancel()
        } else {
            timerJob = viewModelScope.launch {
                while (true) {
                    delay(1000)
                    _timer.value++
                }
            }
        }
    }

    fun reset() {
        timerJob?.cancel()
        _timer.value = 0
    }
}
