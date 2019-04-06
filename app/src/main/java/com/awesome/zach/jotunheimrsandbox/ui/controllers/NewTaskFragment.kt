package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Project
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.databinding.LayoutNewTaskBinding
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ItemSelectedListener
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.MainViewModel
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.MainViewModelFactory
import com.awesome.zach.jotunheimrsandbox.utils.Constants
import com.awesome.zach.jotunheimrsandbox.utils.InjectorUtils
import com.awesome.zach.jotunheimrsandbox.utils.Utils
import com.awesome.zach.jotunheimrsandbox.utils.Utils.hideSoftKeyboard

class NewTaskFragment : Fragment(), ItemSelectedListener {

    companion object {
        const val LOG_TAG = "NewTaskFragment"
    }

    private lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    // private lateinit var binding: FragmentTaskCreateBinding
    private lateinit var binding: LayoutNewTaskBinding


    private var mProject: Project? = null
    private var mTags = ArrayList<Tag>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
                             ): View? {
        setHasOptionsMenu(true)

        // binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_create, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_new_task, container, false)
        val context = binding.root.context

        factory = InjectorUtils.provideMainViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        // binding.ivNewTaskSave.setOnClickListener {
        //     saveTask()
        // }
        //
        // binding.ivNewTaskProject.setOnClickListener {
        //     showSelectProjectDialog()
        // }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_update_task, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_save_task -> actionSaveTask()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun actionSaveTask(): Boolean {
        saveTask()
        fragmentManager?.popBackStack()
        return true
    }

    private fun saveTask() {
        // val input = binding.etNewTaskName.text.toString()
        val input = binding.etTaskName.text.toString()

        if (input.isBlank()) {
            Utils.showSnackbar(binding.root, getString(R.string.null_or_blank))
            return
        }

        viewModel.addTaskToDb(input, mProject)

        hideSoftKeyboard(this)

        // activity?.supportFragmentManager?.popBackStack()
    }

    private fun showSelectProjectDialog() {
        val dialogFragment = SelectProjectDialogFragment(viewModel, this)
        fragmentManager?.let { dialogFragment.show(it, Constants.FRAGMENT_SELECT_PROJECT_DIALOG)}
    }

    override fun onItemSelected(item: Any) {
        if (item is Project) {
            projectSelected(item)
        } else if (item is Tag) {
            tagSelected(item)
        }
    }

    private fun projectSelected(item: Project) {
        mProject = item
        // val currentText = binding.etNewTaskName.text.toString()
        // val newText = "$currentText \${${item.name}}"
        // binding.etNewTaskName.setText(newText)
        val f = fragmentManager?.findFragmentByTag(Constants.FRAGMENT_SELECT_PROJECT_DIALOG) as SelectProjectDialogFragment
        f.dismiss()
    }

    private fun tagSelected(item: Tag) {
        mTags.add(item)
        // val currentText = binding.etNewTaskName.text.toString()
        // val newText = "$currentText #{${item.name}}"
        // binding.etNewTaskName.setText(newText)
        // val f = fragmentManager?.findFragmentByTag(Constants.FRAGMENT_SELECT_TAG_DIALOG) as SelectTagDialogFragment
        // f.dismiss()
    }
}