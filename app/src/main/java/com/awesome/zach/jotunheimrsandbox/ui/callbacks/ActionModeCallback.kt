package com.awesome.zach.jotunheimrsandbox.ui.callbacks

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ActionModeListener

class ActionModeCallback(private val listener: ActionModeListener, private val selectionOnly: Boolean = false) : ActionMode.Callback {

    private var editButtonVisible = true
    private var deleteButtonVisibile = true

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
        if (selectionOnly) {
            hideEditButton()
            hideDeleteButton()
        }

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

        if (!selectionOnly) {
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

    private fun showDeleteButton() {
        val menuItem = mMenu.findItem(R.id.action_mode_delete)
        menuItem.isVisible = true
        deleteButtonVisibile = true
    }

    private fun hideDeleteButton() {
        val menuItem = mMenu.findItem(R.id.action_mode_delete)
        menuItem.isVisible = false
        deleteButtonVisibile = false
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