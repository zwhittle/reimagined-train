package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Color
import com.awesome.zach.jotunheimrsandbox.databinding.FragmentTagCreateUpdateBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.ColorSpinnerAdapter
import com.awesome.zach.jotunheimrsandbox.utils.InjectorUtils
import com.awesome.zach.jotunheimrsandbox.utils.Utils
import com.awesome.zach.jotunheimrsandbox.viewmodels.MainViewModel
import com.awesome.zach.jotunheimrsandbox.viewmodels.MainViewModelFactory

class CreateEditTagFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        const val LOG_TAG = "CreateEditTagFragment"
    }

    private val mColors = ArrayList<Color>()

    private lateinit var selectedColor: Color
    private lateinit var spinnerAdapter: ColorSpinnerAdapter

    private lateinit var binding: FragmentTagCreateUpdateBinding
    private lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tag_create_update, container, false)

        val context = binding.root.context
        factory = InjectorUtils.provideMainViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        retrieveColorsForSpinner()
        setupSpinner()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_new_tag, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save_tag -> actionSaveTag()
            else                     -> super.onOptionsItemSelected(item)
        }
    }

    private fun actionSaveTag(): Boolean {
        saveTag()
        fragmentManager?.popBackStack()
        return true
    }

    private fun setupSpinner() {
        val context = binding.root.context

        spinnerAdapter = ColorSpinnerAdapter(context, R.layout.spinner_item_color, mColors)
        binding.spNewTagColor.adapter = spinnerAdapter
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spNewTagColor.onItemSelectedListener = this

    }

    private fun retrieveColorsForSpinner() {
        // mColors = viewModel.getColors()
        // selectedColor = mColors[0]

        viewModel.getColors().observe(viewLifecycleOwner, Observer { colors ->
            colors.forEach {
                spinnerAdapter.add(it)
            }
        })
    }

    private fun saveTag() {
        val name = binding.etNewTagName.text.toString()
        if (name.isBlank()) {
            Utils.showSnackbar(binding.root, getString(R.string.null_or_blank))
            return
        }

        viewModel.addTagToDb(name, selectedColor.colorId)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (parent.id) {
            R.id.spNewTagColor -> selectedColor = mColors[position]
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        selectedColor = mColors[0]
    }
}