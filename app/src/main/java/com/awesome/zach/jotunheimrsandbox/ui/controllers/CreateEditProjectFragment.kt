package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentProjectCreateEditBinding

class CreateEditProjectFragment : Fragment() {

    companion object {
        const val LOG_TAG = "CreateEditProjectFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentProjectCreateEditBinding>(
            inflater,
            R.layout.fragment_project_create_edit,
            container,
            false)

        return binding.root
    }
}