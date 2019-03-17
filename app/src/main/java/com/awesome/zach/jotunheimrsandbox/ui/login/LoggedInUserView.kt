package com.awesome.zach.jotunheimrsandbox.ui.login

import android.net.Uri

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val image: Uri?)
