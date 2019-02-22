package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.awesome.zach.jotunheimrsandbox.R

class NewTagFragment : Fragment() {

    companion object {
        const val LOG_TAG = "NewTagFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
                             ): View? {

        val binding =
            DataBindingUtil.inflate<com.awesome.zach.jotunheimrsandbox.databinding.FragmentNewTagBinding>(
                inflater,
                R.layout.fragment_new_tag,
                container,
                false)

        return binding.root
    }
}