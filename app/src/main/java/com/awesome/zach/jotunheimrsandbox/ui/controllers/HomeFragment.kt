package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.databinding.LayoutHomeMenuBinding
import com.awesome.zach.jotunheimrsandbox.utils.Constants

class HomeFragment : Fragment() {
    companion object {
        const val LOG_TAG = "HomeFragment"
    }

//    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<LayoutHomeMenuBinding>(inflater, R.layout.layout_home_menu, container, false)
        val context = binding.root.context

        navController = findNavController()

        binding.tvHomeInbox.setOnClickListener {
            showInbox()
        }

        binding.tvHomeTasks.setOnClickListener {
            showTasks()
        }

        binding.tvHomeProjects.setOnClickListener {
            showProjects()
        }

        binding.tvHomeTags.setOnClickListener {
            showTags()
        }

        binding.tvHomeLists.setOnClickListener {
            showLists()
        }

        return binding.root
    }

    private fun showInbox() {
        val args = Bundle()
        args.putLong(Constants.ARGUMENT_PROJECT_ID, 0L)
        args.putString(Constants.ARGUMENT_APP_TITLE, "Inbox")
        navController.navigate(R.id.taskListFragment, args)
    }

    private fun showTasks() {
        navController.navigate(R.id.taskListFragment)
    }

    private fun showProjects() {
        navController.navigate(R.id.projectListFragment)
    }

    private fun showTags() {
        navController.navigate(R.id.tagListFragment)
    }

    private fun showLists() {
        navController.navigate(R.id.listListFragment)
    }
}