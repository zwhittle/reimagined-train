package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentListBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.SimpleTagAdapter
import com.awesome.zach.jotunheimrsandbox.utils.InjectorUtils
import com.awesome.zach.jotunheimrsandbox.viewmodels.TagListViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class ListFragment : Fragment() {

    companion object {
        const val LOG_TAG = "ListFragment"
    }

    private lateinit var viewModel: TagListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentListBinding>(inflater, R.layout.fragment_list, container, false)

        val factory = InjectorUtils.provideTagListViewModelFactory(binding.root.context)
        viewModel = ViewModelProviders.of(this, factory).get(TagListViewModel::class.java)

        val adapter = SimpleTagAdapter()
        binding.rvListFragment.adapter = adapter
        binding.rvListFragment.layoutManager = LinearLayoutManager(binding.root.context)
        subscribeUi(adapter)

        return binding.root
    }


    private fun subscribeUi(adapter: SimpleTagAdapter) {

        viewModel.getTags().observe(viewLifecycleOwner, Observer { tags ->
            if (tags != null) adapter.setTagsList(tags)
        })
    }
}
