package com.example.focustracker.pressentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.focustracker.R
import com.example.focustracker.databinding.FragmentTimerBinding
import kotlinx.coroutines.launch

class TimerFragment : Fragment(R.layout.fragment_timer) {


    private var _binding: FragmentTimerBinding? = null
    private val binding: FragmentTimerBinding
        get() = _binding ?: throw RuntimeException("FragmentTimerBinding")

    private val viewModel: TimerViewModel by viewModels()

    fun getTime() {
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.timer.collect { time ->
                    tvTimer.text = time.toString()
                    Log.d("GET_TIME", "time $time")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding
        super.onViewCreated(view, savedInstanceState)
        getTime()
        binding.btnStartPause.setOnClickListener {
            val command = if (viewModel.isRunning.value) {
                TimerService.COMMAND_PAUSE
            } else TimerService.COMMAND_START

            ContextCompat.startForegroundService(
                requireContext(),
                TimerService.newIntent(
                    requireContext(),
                    viewModel.timer.value,
                    command = command
                )
            )
            Log.d("TIMER_FRAGMENT", "btn on pause")
            btnStartPause()
        }
        binding.btnReset.setOnClickListener {
            ContextCompat.startForegroundService(
                requireContext(),
                TimerService.newIntent(
                    requireContext(),
                    0,
                    TimerService.COMMAND_STOP
                )
            )
            Log.d("TIMER_FRAGMENT", "btn on reset")
            btnReset()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun btnStartPause() {
        viewModel.startPause()
    }

    private fun btnReset() {
        viewModel.reset()
    }
}