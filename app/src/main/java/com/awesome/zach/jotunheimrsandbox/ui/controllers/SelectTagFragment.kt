package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.databinding.LayoutSelectItemFragmentBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.SimpleListAdapter
import com.awesome.zach.jotunheimrsandbox.ui.adapters.SimpleProjectAdapter
import com.awesome.zach.jotunheimrsandbox.ui.adapters.SimpleTaskAdapter
import com.awesome.zach.jotunheimrsandbox.ui.adapters.TagAdapter
import com.awesome.zach.jotunheimrsandbox.ui.callbacks.ActionModeCallback
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ActionModeListener
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ItemSelectedListener
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.TagListViewModel
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.TaskListViewModel
import com.awesome.zach.jotunheimrsandbox.utils.Constants
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SelectTagFragment : Fragment(), ItemSelectedListener, ActionModeListener {

    private lateinit var binding: LayoutSelectItemFragmentBinding

    private val tagListViewModel by sharedViewModel<TagListViewModel>()
    private val taskListViewModel by sharedViewModel<TaskListViewModel>()

    private var mActionModeCallback = ActionModeCallback(this, true)
    private var actionModeEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.layout_select_item_fragment, container, false
        )
        val context = binding.root.context

        setupAdapter(arguments)
        binding.rvSelectionList.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private fun setupAdapter(args: Bundle?) {
        if (args != null) {
            if (args.containsKey(Constants.ARGUMENT_MODEL)) {
                val model = args[Constants.ARGUMENT_MODEL]

                return when (model) {
                    Constants.MODEL_TAG -> {
                        val adapter = TagAdapter(this, true)
                        binding.rvSelectionList.adapter = adapter
                        subscribeUi(adapter)
                    }
                    Constants.MODEL_PROJECT -> {
                        val adapter = SimpleProjectAdapter()
                        binding.rvSelectionList.adapter = adapter
                        subscribeUi(adapter)
                    }
                    Constants.MODEL_TASK -> {
                        val adapter = SimpleTaskAdapter()
                        binding.rvSelectionList.adapter = adapter
                        subscribeUi(adapter)
                    }
                    Constants.MODEL_LIST -> {
                        val adapter = SimpleListAdapter()
                        binding.rvSelectionList.adapter = adapter
                        subscribeUi(adapter)
                    }
                    else -> throw ClassCastException("$model passed but not handled")
                }
            }
        }
    }

    private fun subscribeUi(adapter: TagAdapter) {
         tagListViewModel.tags().observe(viewLifecycleOwner, Observer { tags ->
             if (tags != null) adapter.setTagsList(tags)
         })
         taskListViewModel.getSelectedTags().observe(viewLifecycleOwner, Observer { tags ->
             if (tags != null) {
                 adapter.setSelectedTags(tags)
                 val count = adapter.getSelectedTags().size
                 startActionMode(count)
             }
         })
    }

    private fun subscribeUi(adapter: SimpleProjectAdapter) {
        // tagListViewModel.getProjects().observe(viewLifecycleOwner, Observer { projects ->
        //     if (projects != null) adapter.setProjectsList(projects)
        // })
    }

    private fun subscribeUi(adapter: SimpleTaskAdapter) {
        // tagListViewModel.getTasks().observe(viewLifecycleOwner, Observer { tasks ->
        //     if (tasks != null) adapter.setTasksList(tasks)
        // })
    }

    private fun subscribeUi(adapter: SimpleListAdapter) {
        // tagListViewModel.getLists().observe(viewLifecycleOwner, Observer { lists ->
        //     if (lists != null) adapter.setListsList(lists)
        // })
    }

    override fun onItemSelected(item: Any) {
        if (item is Tag) {
            val adapter = binding.rvSelectionList.adapter as TagAdapter
            val count = adapter.getSelectedTags().size

//            if (count != 0) {
//                if (!actionModeEnabled) {
//                    startActionMode(count)
//                } else {
//                    mActionModeCallback.updateCount(count)
//                }
//            } else if (count == 0) {
//                if (actionModeEnabled) {
//                    finishActionMode()
//                }
//            }

            if (!actionModeEnabled) {
                startActionMode(count)
            } else {
                mActionModeCallback.updateCount(count)
            }
        // } else if (item is JHList) {
        //     tagListViewModel.updateSelectedList(item)
        //     popBackStack()
        }
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
        }
    }

    private fun actionMenuComplete() {
        val adapter = binding.rvSelectionList.adapter
        if (adapter is TagAdapter) {
            val selectedTags = adapter.getSelectedTags()
            taskListViewModel.updateSelectedTags(selectedTags)
            popBackStack()
        }
    }

    private fun finishActionMode() {
        mActionModeCallback.finishActionMode()
    }

    override fun onActionModeCreated() {
        actionModeEnabled = true
    }

    override fun onActionModeDestroyed() {
        val adapter = binding.rvSelectionList.adapter
        if (adapter is TagAdapter) {
            adapter.clearSelectedTags()
        }
        actionModeEnabled = false
    }

    private fun popBackStack() {
        findNavController().popBackStack()
    }
}