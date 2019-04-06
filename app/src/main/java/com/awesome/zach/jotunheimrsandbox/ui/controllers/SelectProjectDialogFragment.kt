package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.databinding.LayoutProjectListBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.SimpleProjectAdapter
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ItemSelectedListener
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.MainViewModel

class SelectProjectDialogFragment(private val viewModel: MainViewModel,
                                  private val listener: ItemSelectedListener) : DialogFragment() {

    companion object {
        const val LOG_TAG = "SelectProjectDialogFragment"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: LayoutProjectListBinding = DataBindingUtil.inflate(inflater,
                                                                        R.layout.layout_project_list,
                                                                        container,
                                                                        false)

        // val binding = inflater.inflate(R.layout.layout_project_list, container, false)
        val context = binding.root.context

        val adapter = SimpleProjectAdapter(listener)
        binding.rvProjectListPopup.adapter = adapter
        binding.rvProjectListPopup.layoutManager = LinearLayoutManager(context)

        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: SimpleProjectAdapter) {
        viewModel.getProjects()
            .observe(viewLifecycleOwner,
                     Observer { projects ->
                         if (projects != null) adapter.setProjectsList(projects)
                     })
    }
}