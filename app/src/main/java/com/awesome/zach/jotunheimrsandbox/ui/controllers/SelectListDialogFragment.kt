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
import com.awesome.zach.jotunheimrsandbox.ui.adapters.SimpleListAdapter
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ItemSelectedListener
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ListListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SelectListDialogFragment(private val listener: ItemSelectedListener) : DialogFragment() {
    companion object {
        const val LOG_TAG = "SelectListDialogFragment"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: com.awesome.zach.jotunheimrsandbox.databinding.LayoutListListBinding =
            DataBindingUtil.inflate(inflater,
                                    R.layout.layout_list_list,
                                    container,
                                    false)
        val context = binding.root.context

        val viewModel by viewModel<ListListViewModel>()

        val adapter = SimpleListAdapter(listener)
        binding.rvListListPopup.adapter = adapter
        binding.rvListListPopup.layoutManager = LinearLayoutManager(context)

        subscribeUi(adapter, viewModel)

        return binding.root
    }

    private fun subscribeUi(adapter: SimpleListAdapter, vm: ListListViewModel) {
        vm.lists().observe(viewLifecycleOwner, Observer { lists ->
            if (lists != null) adapter.setListsList(lists)
        })
    }
}