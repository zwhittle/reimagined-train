package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.app.Activity
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentTagListBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.SimpleTagAdapter
import com.awesome.zach.jotunheimrsandbox.utils.InjectorUtils
import com.awesome.zach.jotunheimrsandbox.viewmodels.TagListViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class TagListFragment : Fragment() {

    companion object {
        const val LOG_TAG = "TagListFragment"
    }

    private lateinit var viewModel: TagListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding =
            DataBindingUtil.inflate<FragmentTagListBinding>(inflater,
                                                            R.layout.fragment_tag_list,
                                                            container,
                                                            false)

        val factory = InjectorUtils.provideTagListViewModelFactory(binding.root.context)
        viewModel = ViewModelProviders.of(this,
                                          factory)
            .get(TagListViewModel::class.java)

        val adapter = SimpleTagAdapter()
        binding.rvTagList.adapter = adapter
        binding.rvTagList.layoutManager = LinearLayoutManager(binding.root.context)
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: SimpleTagAdapter) {

        viewModel.getTags()
            .observe(viewLifecycleOwner,
                     Observer { tags ->
                         if (tags != null) adapter.setTagsList(tags)
                     })
    }
}
