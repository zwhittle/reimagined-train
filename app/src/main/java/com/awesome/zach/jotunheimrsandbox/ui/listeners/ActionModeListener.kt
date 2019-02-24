package com.awesome.zach.jotunheimrsandbox.ui.listeners

import android.view.MenuItem

interface ActionModeListener {
    fun onActionModeCreated()
    fun onActionModeDestroyed()
    fun onActionMenuItemSelected(item: MenuItem)
}