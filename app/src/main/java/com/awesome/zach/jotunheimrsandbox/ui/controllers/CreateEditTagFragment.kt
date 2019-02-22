package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentTagCreateUpdateBinding

class CreateEditTagFragment : Fragment() {

    companion object {
        const val LOG_TAG = "CreateEditTagFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
                             ): View? {

        val binding =
            DataBindingUtil.inflate<FragmentTagCreateUpdateBinding>(
                inflater,
                R.layout.fragment_tag_create_update,
                container,
                false)

        return binding.root
    }
}