package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.view.*
import android.widget.Adapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList
import com.awesome.zach.jotunheimrsandbox.data.entities.Project
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentNewTaskBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.JHTagAdapter
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ItemSelectedListener
import com.awesome.zach.jotunheimrsandbox.utils.Constants
import com.awesome.zach.jotunheimrsandbox.utils.Utils
import com.awesome.zach.jotunheimrsandbox.utils.Utils.hideSoftKeyboard
import com.awesome.zach.jotunheimrsandbox.utils.Utils.showSnackbar

class NewTaskFragment : Fragment(),
    ItemSelectedListener {

    companion object {
        const val LOG_TAG = "NewTaskFragment"
    }

    // private lateinit var factory: MainViewModelFactory
    // private lateinit var viewModel: MainViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: Adapter
    private lateinit var binding: FragmentNewTaskBinding

    private var mProject: Project? = null
    private var mList: JHList? = null
    private var mTags = listOf<Tag>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(inflater,
                                          R.layout.fragment_new_task,
                                          container,
                                          false)
        val context = binding.root.context

        // factory = InjectorUtils.provideMainViewModelFactory(context)
        // viewModel = activity?.run {
        //     ViewModelProviders.of(this,
        //                           factory)
        //         .get(MainViewModel::class.java)
        // } ?: throw Exception("Invalid Activity")

        binding.tagRow.setOnClickListener {
            showSelectTagsFragment()
        }

        binding.tagsLabel.setOnClickListener {
            showSelectTagsFragment()
        }

        binding.tagsValue.setOnClickListener {
            showSelectTagsFragment()
        }

        binding.recyclerView.setOnClickListener {
            showSelectTagsFragment()
        }

        binding.listValue.setOnClickListener {
            showSelectListDialog()
        }

        binding.projectValue.setOnClickListener {
            showSelectProjectDialog()
        }

        binding.dueValue.setOnClickListener {
            showSnackbar(binding.root,
                         "This ain't work yet.")
        }

        subscribeUi()

        return binding.root
    }

    override fun onDestroyView() {
        // viewModel.clearSelectedTags()
        super.onDestroyView()
    }

    private fun subscribeUi() {
        // viewModel.getSelectedTags()
        //     .observe(viewLifecycleOwner,
        //              Observer { tags ->
        //                  if (tags != null) {
        //                      mTags = tags
        //
        //                      if (tags.isNotEmpty()) {
        //                          if (binding.tagsLabel.isVisible) binding.tagsLabel.visibility =
        //                              View.GONE
        //                          if (binding.tagsValue.isVisible) binding.tagsValue.visibility =
        //                              View.GONE
        //
        //                          if (!binding.recyclerView.isVisible) {
        //                              val tagList = ArrayList<Tag>()
        //                              mTags.forEach {
        //                                  tagList.add(it)
        //                              }
        //
        //                              val adapter = JHTagAdapter(tagList,
        //                                                         View.OnClickListener { showSelectTagsFragment() })
        //                              binding.recyclerView.adapter = adapter
        //                              binding.recyclerView.layoutManager =
        //                                  LinearLayoutManager(binding.root.context,
        //                                                      LinearLayout.HORIZONTAL,
        //                                                      false)
        //                              binding.recyclerView.visibility = View.VISIBLE
        //                          }
        //                      } else {
        //                          if (binding.recyclerView.isVisible) binding.recyclerView.visibility =
        //                              View.GONE
        //                          if (!binding.tagsLabel.isVisible) binding.tagsLabel.visibility =
        //                              View.VISIBLE
        //                          if (!binding.tagsValue.isVisible) binding.tagsValue.visibility =
        //                              View.VISIBLE
        //                      }
        //                  }
        //              })
    }

    override fun onCreateOptionsMenu(menu: Menu,
                                     inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_update_task,
                         menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save_task -> actionSaveTask()
            else                  -> super.onOptionsItemSelected(item)
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
        val adapter = binding.recyclerView.adapter
        if (adapter is JHTagAdapter) {
            val tags = adapter.getTags()

            if (input.isBlank()) {
                Utils.showSnackbar(binding.root,
                                   getString(R.string.null_or_blank))
                return
            }

            // viewModel.addTaskToDb(input,
            //                       mProject,
            //                       mList,
            //                       tags)
        } else if (adapter == null) {
            // viewModel.addTaskToDb(input,
            //     mProject,
            //     mList)
        }

        hideSoftKeyboard(this)

        // activity?.supportFragmentManager?.popBackStack()
    }

    private fun showSelectTagsFragment() {
        val args = Bundle()
        args.putString(Constants.ARGUMENT_MODEL,
                       Constants.MODEL_TAG)

        val navController = findNavController()
        navController.navigate(R.id.selectItemFragment,
                               args)
    }

    private fun showSelectProjectDialog() {
        // val dialogFragment = SelectProjectDialogFragment(viewModel,
        //                                                  this)
        // fragmentManager?.let {
        //     dialogFragment.show(it,
        //                         Constants.FRAGMENT_SELECT_PROJECT_DIALOG)
        // }
    }

    private fun showSelectListDialog() {
        // val dialogFragment = SelectListDialogFragment(viewModel,
        //                                               this)
        // fragmentManager?.let {
        //     dialogFragment.show(it,
        //                         Constants.FRAGMENT_SELECT_LIST_DIALOG)
        // }
    }

    override fun onItemSelected(item: Any) {
        when (item) {
            is Project -> projectSelected(item)
            is JHList  -> listSelected(item)
        }
    }

    private fun projectSelected(item: Project) {
        mProject = item
        // val currentText = binding.etNewTaskName.text.toString()
        // val newText = "$currentText \${${item.name}}"
        // binding.etNewTaskName.setText(newText)
        val f =
            fragmentManager?.findFragmentByTag(Constants.FRAGMENT_SELECT_PROJECT_DIALOG) as SelectProjectDialogFragment
        f.dismiss()

        binding.projectValue.text = item.name
    }

    private fun listSelected(item: JHList) {
        mList = item
        val f = fragmentManager?.findFragmentByTag(Constants.FRAGMENT_SELECT_LIST_DIALOG) as SelectListDialogFragment
        f.dismiss()

        binding.listValue.text = item.name
    }
}