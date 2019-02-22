package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentTaskListBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.SimpleTaskAdapter
import com.awesome.zach.jotunheimrsandbox.utils.Constants
import com.awesome.zach.jotunheimrsandbox.utils.InjectorUtils
import com.awesome.zach.jotunheimrsandbox.viewmodels.TaskListViewModel
import com.awesome.zach.jotunheimrsandbox.viewmodels.TaskListViewModelFactory
import java.lang.ClassCastException

class TaskListFragment : Fragment() {

    companion object {
        const val LOG_TAG = "TaskListFragment"
    }

    private lateinit var factory: TaskListViewModelFactory
    private lateinit var viewModel: TaskListViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentTaskListBinding>(inflater,
                                                                       R.layout.fragment_task_list,
                                                                       container,
                                                                       false)
        val context = binding.root.context

        handleArguments(context)

        val adapter = SimpleTaskAdapter()

        binding.rvTaskList.adapter = adapter
        binding.rvTaskList.layoutManager = LinearLayoutManager(context)

        subscribeUi(adapter)

        return binding.root
    }

    private fun handleArguments(context: Context) {
        val args = arguments ?: Bundle()

        when {
            args.containsKey(Constants.ARGUMENT_PROJECT) -> setupFactoryForProject(context, args.getLong(Constants.ARGUMENT_PROJECT))
            args.containsKey(Constants.ARGUMENT_TAG)     -> setupFactoryForTag(context, args.getLong(Constants.ARGUMENT_TAG))
            else                                         -> setupFactoryForAll(context)
        }

        viewModel = ViewModelProviders.of(this, factory).get(TaskListViewModel::class.java)
    }

    private fun setupFactoryForAll(context: Context) {
        factory = InjectorUtils.provideTaskListViewModelFactory(context = context)
    }

    private fun setupFactoryForTag(context: Context, tagId: Long) {
        factory = InjectorUtils.provideTaskListViewModelFactory(context = context, tagId = tagId)
    }

    private fun setupFactoryForProject(context: Context, projectId: Long) {
        factory = InjectorUtils.provideTaskListViewModelFactory(context = context, projectId = projectId)
    }

    private fun subscribeUi(adapter: SimpleTaskAdapter) {

        viewModel.getTasks()
            .observe(viewLifecycleOwner,
                     Observer { tasks ->
                         if (tasks != null) adapter.setTasksList(tasks)
                     })
    }
}