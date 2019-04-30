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
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentListListBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.ListListAdapter
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ListListViewModel
import com.awesome.zach.jotunheimrsandbox.utils.LogUtils
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ListListFragment : Fragment() {

    companion object {
        const val LOG_TAG = "ListListFragment"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentListListBinding>(inflater,
                                                                       R.layout.fragment_list_list,
                                                                       container,
                                                                       false)
        val context = binding.root.context
        val listListViewModel by sharedViewModel<ListListViewModel>()
        binding.vm = listListViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ListListAdapter(viewLifecycleOwner, listListViewModel)

        listListViewModel.lists().observe(viewLifecycleOwner, Observer { list ->
            LogUtils.log(LOG_TAG, "D::lists: $list")
            adapter.submitList(list)
        })

        listListViewModel.uiNameList().observe(viewLifecycleOwner, Observer { callback ->
            val inflatedView = LayoutInflater.from(context).inflate(R.layout.dialog_text, view as ViewGroup?, false)
            val input = inflatedView.findViewById(R.id.input) as EditText
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.enter_name_of_list))
                .setView(inflatedView)
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    val text = input.text.toString()
                    callback.invoke(text)
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
                .create().show()
        })


        binding.rvListsList.adapter = adapter
        binding.rvListsList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListsList.setHasFixedSize(true)
        binding.executePendingBindings()

        return binding.root
    }
}