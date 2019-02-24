package com.awesome.zach.jotunheimrsandbox.ui.listeners

import androidx.fragment.app.DialogFragment

interface DialogFragmentListener {
    fun onPositiveClick(dialog: DialogFragment, items: List<Any>)
    fun onNegativeClick(dialog: DialogFragment)
}