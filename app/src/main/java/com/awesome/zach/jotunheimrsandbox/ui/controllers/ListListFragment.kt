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
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentListListBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.SimpleListAdapter
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.MainViewModel
import com.awesome.zach.jotunheimrsandbox.utils.InjectorUtils

class ListListFragment : Fragment() {

    companion object {
        const val LOG_TAG = "ListListFragment"
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentListListBinding>(inflater,
                                                                       R.layout.fragment_list_list,
                                                                       container,
                                                                       false)
        val context = binding.root.context

        val factory = InjectorUtils.provideMainViewModelFactory(context)
        viewModel = activity?.run {
            ViewModelProviders.of(this,
                                  factory)
                .get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val adapter = SimpleListAdapter()
        binding.rvListList.adapter = adapter
        binding.rvListList.layoutManager = LinearLayoutManager(context)
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: SimpleListAdapter) {
        viewModel.getLists()
            .observe(viewLifecycleOwner,
                     Observer { lists ->
                         if (lists != null) adapter.setListsList(lists)
                     })
    }
}