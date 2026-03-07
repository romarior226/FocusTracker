package com.example.focustracker.pressentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.focustracker.R
import com.example.focustracker.data.HistoryRepository
import com.example.focustracker.data.TaskRepository
import com.example.focustracker.data.database.AppDatabase
import com.example.focustracker.databinding.FragmentHistoryBinding
import kotlinx.coroutines.launch
import kotlin.getValue

class HistoryFragment : Fragment(R.layout.fragment_history) {
    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            TaskRepository(AppDatabase.getDataBase(requireContext()).taskDao()),
            HistoryRepository(AppDatabase.getDataBase(requireContext()).historyTaskDao())
        )
    }


    private var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding
        get() = _binding ?: throw RuntimeException("FragmentTasksBinding")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = TaskAdapter(
            false,
            { task -> viewModel.deleteHistoryTask(task) },
        )
        super.onViewCreated(view, savedInstanceState)
        Log.d("HistoryFragment", "onViewCreated ")
        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.history.collect { tasks ->
                adapter.submitList(tasks)
                binding.tvTodaySummary.text = "Сьогодні: ${tasks.size} \uD83C\uDF45  "
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}