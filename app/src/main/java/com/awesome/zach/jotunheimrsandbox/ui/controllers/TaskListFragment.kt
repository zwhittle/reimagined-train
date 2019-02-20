package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentTaskListBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.SimpleTaskAdapter
import com.awesome.zach.jotunheimrsandbox.utils.InjectorUtils
import com.awesome.zach.jotunheimrsandbox.viewmodels.TaskListViewModel

class TaskListFragment : Fragment() {

    companion object {
        const val LOG_TAG = "TaskListFragment"
    }

    private lateinit var viewModel: TaskListViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentTaskListBinding>(inflater,
                                                                          R.layout.fragment_task_list,
                                                                          container,
                                                                          false)
        val context = binding.root.context

        val factory = InjectorUtils.provideTaskListViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(TaskListViewModel::class.java)

        val adapter = SimpleTaskAdapter()
        binding.rvTaskList.adapter = adapter
        binding.rvTaskList.layoutManager = LinearLayoutManager(context)
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: SimpleTaskAdapter) {

        viewModel.getTasks().observe(viewLifecycleOwner, Observer { tasks ->
            if (tasks != null) adapter.setTasksList(tasks)
        })
    }
}