package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentTaskListBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.TaskAdapter
import com.awesome.zach.jotunheimrsandbox.ui.callbacks.ActionModeCallback
import com.awesome.zach.jotunheimrsandbox.ui.callbacks.ActionModeListener
import com.awesome.zach.jotunheimrsandbox.ui.callbacks.ItemSelectedListener
import com.awesome.zach.jotunheimrsandbox.utils.Constants
import com.awesome.zach.jotunheimrsandbox.utils.InjectorUtils
import com.awesome.zach.jotunheimrsandbox.utils.Utils
import com.awesome.zach.jotunheimrsandbox.viewmodels.MainViewModel
import com.awesome.zach.jotunheimrsandbox.viewmodels.MainViewModelFactory

class TaskListFragment : Fragment(), ItemSelectedListener, ActionModeListener {

    companion object {
        const val LOG_TAG = "TaskListFragment"
    }

    private lateinit var binding: FragmentTaskListBinding
    private lateinit var factory: MainViewModelFactory
    private lateinit var adapter: TaskAdapter
    private lateinit var viewModel: MainViewModel

    private var mActionModeCallback = ActionModeCallback(this)
    private var actionModeEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task_list, container, false)
        val context = binding.root.context

        handleArguments(context)

        adapter = TaskAdapter(this, true)
        binding.rvTaskList.adapter = adapter
        binding.rvTaskList.layoutManager = LinearLayoutManager(context)

        subscribeUi()

        return binding.root
    }

    private fun handleArguments(context: Context) {
        val args = arguments ?: Bundle()

        when {
            args.containsKey(Constants.ARGUMENT_PROJECT) -> setupFactoryForProject(context, args.getLong(Constants.ARGUMENT_PROJECT))
            args.containsKey(Constants.ARGUMENT_TAG)     -> setupFactoryForTag(context, args.getLong(Constants.ARGUMENT_TAG))
            else                                         -> setupFactoryForAll(context)
        }

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }

    private fun startActionMode() {
        val a = activity
        if (a != null) {
            val toolbar = a.findViewById<Toolbar>(R.id.toolbar)
            mActionModeCallback.startActionMode(toolbar, R.menu.menu_action_mode, "title", "subtitle")
            actionModeEnabled = true
        }
    }

    private fun finishActionMode() {
        mActionModeCallback.finishActionMode()
        actionModeEnabled = false
    }

    private fun setupFactoryForAll(context: Context) {
        factory = InjectorUtils.provideMainViewModelFactory(context = context)
    }

    private fun setupFactoryForTag(context: Context, tagId: Long) {
        factory = InjectorUtils.provideMainViewModelFactory(context = context, tagId = tagId)
    }

    private fun setupFactoryForProject(context: Context, projectId: Long) {
        factory = InjectorUtils.provideMainViewModelFactory(context = context, projectId = projectId)
    }

    private fun subscribeUi() {
        viewModel.getTasks().observe(viewLifecycleOwner, Observer { tasks ->
            if (tasks != null) adapter.setTasksList(tasks)
        })
    }

    override fun onItemSelected(item: Any) {
        if (item is Task) {
            val selectedTasks = adapter.getSelectedTasks()

            if (selectedTasks.isNotEmpty()) {
                if (!actionModeEnabled) {
                    startActionMode()
                }
            } else if (selectedTasks.isEmpty()) {
                if (actionModeEnabled) {
                    finishActionMode()
                }
            }

            Utils.showSnackbar(
                binding.root,
                "Selected item is ${item.name}. Total selected: ${selectedTasks.size}")
        }
    }

    override fun onActionModeDestroyed() {
        adapter.clearSelectedTasks()
    }
}