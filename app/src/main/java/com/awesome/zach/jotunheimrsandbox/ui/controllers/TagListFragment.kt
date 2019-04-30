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
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentTagListBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.TagListAdapter
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.TagListViewModel
import com.awesome.zach.jotunheimrsandbox.utils.LogUtils
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TagListFragment : Fragment() {

    companion object {
        const val LOG_TAG = "TagListFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding =
            DataBindingUtil.inflate<FragmentTagListBinding>(inflater,
                                                            R.layout.fragment_tag_list,
                                                            container,
                                                            false)

        val context = binding.root.context
        val viewModel by sharedViewModel<TagListViewModel>()
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = TagListAdapter(viewLifecycleOwner, viewModel)

        viewModel.tags().observe(viewLifecycleOwner, Observer { list ->
            LogUtils.log(LOG_TAG, "D::Tags: $list")
            adapter.submitList(list)
        })

        viewModel.uiNameTag().observe(viewLifecycleOwner, Observer { callback ->
            val inflatedView = LayoutInflater.from(context).inflate(R.layout.dialog_text, view as ViewGroup?, false)
            val input = inflatedView.findViewById(R.id.input) as EditText
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.enter_name_of_tag))
                .setView(inflatedView)
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    val text = input.text.toString()
                    callback.invoke(text)
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
                .create().show()

        })


        binding.rvTagsList.adapter = adapter
        binding.rvTagsList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvTagsList.setHasFixedSize(true)
        binding.executePendingBindings()

        return binding.root
    }

}
