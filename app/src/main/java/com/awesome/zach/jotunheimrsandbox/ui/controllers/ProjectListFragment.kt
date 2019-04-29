package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentProjectListBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.ProjectListAdapter
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ProjectListViewModel
import com.awesome.zach.jotunheimrsandbox.utils.LogUtils
import org.koin.android.viewmodel.ext.android.viewModel

class ProjectListFragment : Fragment() {

    companion object {
        const val LOG_TAG = "ProjectListFragment"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentProjectListBinding>(inflater,
                                                                          R.layout.fragment_project_list,
                                                                          container,
                                                                          false)
        val context = binding.root.context
        val viewModel by viewModel<ProjectListViewModel>()
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ProjectListAdapter(viewLifecycleOwner, viewModel)

        viewModel.projects().observe(viewLifecycleOwner, Observer { list ->
            LogUtils.log(LOG_TAG, "D::Projects: $list")
            adapter.submitList(list)
        })

        viewModel.uiNameProject().observe(viewLifecycleOwner, Observer { callback ->
            val inflatedView = LayoutInflater.from(context).inflate(R.layout.dialog_text, view as ViewGroup?, false)
            val input = inflatedView.findViewById(R.id.input) as EditText
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.enter_name_of_project))
                .setView(inflatedView)
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    val text = input.text.toString()
                    callback.invoke(text)
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
                .create().show()
        })

        binding.rvProjectsList.adapter = adapter
        binding.rvProjectsList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProjectsList.setHasFixedSize(true)
        binding.executePendingBindings()

        return binding.root
    }
}