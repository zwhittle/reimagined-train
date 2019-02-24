package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentTaskListBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.TaskAdapter
import com.awesome.zach.jotunheimrsandbox.ui.callbacks.ActionModeCallback
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ActionModeListener
import com.awesome.zach.jotunheimrsandbox.ui.listeners.DialogFragmentListener
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ItemSelectedListener
import com.awesome.zach.jotunheimrsandbox.utils.Constants
import com.awesome.zach.jotunheimrsandbox.utils.InjectorUtils
import com.awesome.zach.jotunheimrsandbox.viewmodels.MainViewModel
import com.awesome.zach.jotunheimrsandbox.viewmodels.MainViewModelFactory

class TaskListFragment : Fragment(),
    DialogFragmentListener,
    ItemSelectedListener,
    ActionModeListener {

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

        setActionBarTitle(getString(R.string.all_tasks))
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
            args.containsKey(Constants.ARGUMENT_PROJECT_ID) -> setupFragmentForProject(context, args)
            args.containsKey(Constants.ARGUMENT_TAG_NAME)   -> setupFragmentForTag(context, args)
            else                                            -> setupFragmentForAll(context)
        }

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }

    private fun startActionMode(count: Int) {
        val a = activity
        if (a != null) {
            val toolbar = a.findViewById<Toolbar>(R.id.toolbar)
            mActionModeCallback.startActionMode(toolbar, R.menu.menu_action_mode, count.toString(), "")
        }
    }

    override fun onActionMenuItemSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.action_mode_complete -> actionMenuComplete()
            R.id.action_mode_edit     -> actionMenuEdit()
            R.id.action_mode_delete   -> actionMenuDelete()
        }
    }

    private fun actionMenuComplete() {
        val selectedTasks = adapter.getSelectedTasks()
        selectedTasks.forEach {
            it.completed = true
        }

        viewModel.updateTasks(selectedTasks)
    }

    private fun actionMenuEdit() {
        // TODO: Implement this
    }

    private fun actionMenuDelete() {
        val selectedTasks = adapter.getSelectedTasks()

        val dialog = DeleteItemsDialogFragment(selectedTasks, this)
        fragmentManager?.let { dialog.show(it, Constants.FRAGMENT_DELETE_ITEMS_DIALOG) }
    }

    override fun onPositiveClick(dialog: DialogFragment,
                                 items: List<Any>) {
        deleteSelectedItems(items)
        dialog.dismiss()
    }

    override fun onNegativeClick(dialog: DialogFragment) {
        dialog.dismiss()
    }

    private fun deleteSelectedItems(items: List<Any>) {
        if (items[0] is Task) {
            val tasks = ArrayList<Task>()

            items.forEach {
                tasks.add(it as Task)
            }

            viewModel.deleteTasks(tasks)
        }
    }

    private fun finishActionMode() {
        mActionModeCallback.finishActionMode()
    }

    private fun setupFactoryForAll(context: Context) {
        factory = InjectorUtils.provideMainViewModelFactory(context = context)
    }

    private fun setupFragmentForAll(context: Context) {
        setupFactoryForAll(context)
        setActionBarTitle(getString(R.string.all_tasks))
    }

    private fun setupFactoryForTag(context: Context, tagId: Long) {
        factory = InjectorUtils.provideMainViewModelFactory(context = context, tagId = tagId)
    }

    private fun setupFactoryForProject(context: Context, projectId: Long) {
        factory = InjectorUtils.provideMainViewModelFactory(context = context, projectId = projectId)
    }

    private fun setupFragmentForProject(context: Context, args: Bundle) {
        setupFactoryForProject(context, args.getLong(Constants.ARGUMENT_PROJECT_ID))
        setActionBarTitle(args.getString(Constants.ARGUMENT_PROJECT_NAME))
    }

    private fun setupFragmentForTag(context: Context, args: Bundle) {
        setupFactoryForTag(context, args.getLong(Constants.ARGUMENT_TAG_ID))
        setActionBarTitle(args.getString(Constants.ARGUMENT_TAG_NAME))
    }

    private fun setActionBarTitle(string: String?) {
        if (string.isNullOrBlank()) return

        val a = activity as MainActivity
        a.setActionBarTitle(string)

    }

    private fun subscribeUi() {
        viewModel.getTasks().observe(viewLifecycleOwner, Observer { tasks ->
            if (tasks != null) adapter.setTasksList(tasks)
        })
    }

    private fun attachDrawerListener() {
        val a = activity as MainActivity
        a.attachDrawerListener(
            object: DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    // respond when the drawer's position changes
                    if (actionModeEnabled) {
                        finishActionMode()
                    }
                }

                override fun onDrawerOpened(drawerView: View) {
                    // respond when the drawer is opened
                }

                override fun onDrawerClosed(drawerView: View) {
                    // respond when the drawer is closed
                }

                override fun onDrawerStateChanged(newState: Int) {
                    // respond when the drawer motion state changes
                }
            })
    }

    override fun onItemSelected(item: Any) {
        if (item is Task) {
            val count = adapter.getSelectedTasks().size

            if (count != 0) {
                if (!actionModeEnabled) {
                    startActionMode(count)
                } else {
                    mActionModeCallback.updateCount(count)
                }
            } else if (count == 0) {
                if (actionModeEnabled) {
                    finishActionMode()
                }
            }
        }
    }

    override fun onActionModeCreated() {
        attachDrawerListener()
        actionModeEnabled = true
    }

    override fun onActionModeDestroyed() {
        adapter.clearSelectedTasks()
        actionModeEnabled = false
    }
}