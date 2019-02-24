package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.ui.listeners.DialogFragmentListener

class DeleteItemsDialogFragment(private val items: List<Any>, private val listener: DialogFragmentListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Are you sure you want to delete ${items.size} items?")
                .setPositiveButton(R.string.delete) { _, _ -> listener.onPositiveClick(this, items)
                }
                .setNegativeButton(R.string.cancel) { _, _ -> listener.onNegativeClick(this)
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
