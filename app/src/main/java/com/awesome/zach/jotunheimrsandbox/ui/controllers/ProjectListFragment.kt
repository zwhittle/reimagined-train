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
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentProjectListBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.SimpleProjectAdapter
import com.awesome.zach.jotunheimrsandbox.utils.InjectorUtils
import com.awesome.zach.jotunheimrsandbox.viewmodels.MainViewModel

class ProjectListFragment : Fragment() {

    companion object {
        const val LOG_TAG = "ProjectListFragment"
    }

    // private lateinit var viewModel: ProjectListViewModel
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentProjectListBinding>(inflater,
                                                                          R.layout.fragment_project_list,
                                                                          container, false)
        val context = binding.root.context

        // val factory = InjectorUtils.provideProjectListViewModelFactory(context)
        // viewModel = ViewModelProviders.of(this, factory).get(ProjectListViewModel::class.java)

        val factory = InjectorUtils.provideMainViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        val adapter = SimpleProjectAdapter()
        binding.rvProjectList.adapter = adapter
        binding.rvProjectList.layoutManager = LinearLayoutManager(context)
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: SimpleProjectAdapter) {

        viewModel.getProjects().observe(viewLifecycleOwner, Observer { projects ->
            if (projects != null) adapter.setProjectsList(projects)
        })
    }
}