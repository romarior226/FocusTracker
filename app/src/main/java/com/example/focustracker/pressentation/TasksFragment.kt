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
import com.example.focustracker.data.TaskRepository
import com.example.focustracker.data.database.AppDatabase
import com.example.focustracker.databinding.FragmentTasksBinding

import kotlinx.coroutines.launch

class TasksFragment : Fragment(R.layout.fragment_tasks) {


    val adapter = TaskAdapter(
        { task -> viewModel.deleteTask(task) },
        { task -> viewModel.addCompletedTask(task) },
        { task -> viewModel.updateTask(task) }

    )
    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(TaskRepository(AppDatabase.getDataBase(requireContext()).taskDao()))
    }

    private var _binding: FragmentTasksBinding? = null
    private val binding: FragmentTasksBinding
        get() = _binding ?: throw RuntimeException("FragmentTasksBinding")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tasks.collect { tasks ->
                adapter.submitList(tasks)
            }
        }
        with(binding) {
            rvTasks.adapter = adapter
            rvTasks.layoutManager = LinearLayoutManager(requireContext())
            btnAddTask.setOnClickListener {
                val task = etNewTask.text.toString()
                viewModel.addTask(task)

            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}