package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentTaskCreateBinding
import com.awesome.zach.jotunheimrsandbox.utils.InjectorUtils
import com.awesome.zach.jotunheimrsandbox.utils.Utils
import com.awesome.zach.jotunheimrsandbox.viewmodels.MainViewModel
import com.awesome.zach.jotunheimrsandbox.viewmodels.MainViewModelFactory

class NewTaskFragment : Fragment() {

    companion object {
        const val LOG_TAG = "NewTaskFragment"
    }

    private lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    private lateinit var binding: FragmentTaskCreateBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_create, container, false)

        val context = binding.root.context
        factory = InjectorUtils.provideMainViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        binding.ivNewTaskSave.setOnClickListener {
            saveTask()

        }

        return binding.root
    }

    private fun saveTask() {
        val name = binding.etNewTaskName.text.toString()
        if (name.isBlank()) {
            Utils.showSnackbar(binding.root, getString(R.string.null_or_blank))
            return
        }

        viewModel.addProjectToDb(name)
        activity?.supportFragmentManager?.popBackStack()
    }


}