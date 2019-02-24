package com.awesome.zach.jotunheimrsandbox.ui.callbacks

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ActionModeListener
import com.awesome.zach.jotunheimrsandbox.ui.listeners.OnActionItemClickListener

class ActionModeCallback(private val listener: ActionModeListener) : ActionMode.Callback {

    var onActionItemClickListener: OnActionItemClickListener? = null

    private var mMode: ActionMode? = null
    @MenuRes private var mMenuResId: Int = 0
    private var mTitle: String? = null
    private var mSubtitle: String? = null

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mMode = mode
        mode.menuInflater.inflate(mMenuResId, menu)
        mode.title = mTitle
        mode.subtitle = mSubtitle
        listener.onActionModeCreated()
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        mMode = null
        listener.onActionModeDestroyed()
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        onActionItemClickListener?.onActionItemClick(item)
        mode.finish()
        return true
    }

    fun updateCount(count: Int) {
        mMode?.title = count.toString()
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