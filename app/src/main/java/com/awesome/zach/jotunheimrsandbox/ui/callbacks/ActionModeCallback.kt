package com.awesome.zach.jotunheimrsandbox.ui.callbacks

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ActionModeListener

class ActionModeCallback(private val listener: ActionModeListener) : ActionMode.Callback {

    private var editButtonVisible = true

    private var mMode: ActionMode? = null
    @MenuRes private var mMenuResId: Int = 0
    private var mTitle: String? = null
    private var mSubtitle: String? = null


    private lateinit var mMenu: Menu

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mMenu = menu
        mMode = mode
        mode.menuInflater.inflate(mMenuResId, menu)
        mode.title = mTitle
        mode.subtitle = mSubtitle
        listener.onActionModeCreated()
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        mMode = null
        listener.onActionModeDestroyed()
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        listener.onActionMenuItemSelected(item)
        finishActionMode()
        return true
    }

    fun updateCount(count: Int) {
        mMode?.title = count.toString()

        if (count > 1) {
            if (editButtonVisible) {
                hideEditButton()
            }
        } else {
            if (!editButtonVisible) {
                showEditButton()
            }
        }
    }

    private fun showEditButton() {
        val menuItem = mMenu.findItem(R.id.action_mode_edit)
        menuItem.isVisible = true
        editButtonVisible = true
    }

    private fun hideEditButton() {
        val menuItem = mMenu.findItem(R.id.action_mode_edit)
        menuItem.isVisible = false
        editButtonVisible = false
    }

    fun startActionMode(view: View,
                        @MenuRes menuResId: Int,
                        title: String? = null,
                        subtitle: String? = null) {
        mMenuResId = menuResId
        mTitle = title
        mSubtitle = subtitle
        view.startActionMode(this)
    }

    fun finishActionMode() {
        mMode?.finish()
    }
}