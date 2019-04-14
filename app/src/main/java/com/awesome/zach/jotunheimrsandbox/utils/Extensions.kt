package com.awesome.zach.jotunheimrsandbox.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.awesome.zach.jotunheimrsandbox.ui.controllers.MainActivity

/**
 * Layout extensions
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

/**
 * Fragment extensions
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun Fragment.setActionBarTitle(title: String) {
    val a = activity as MainActivity
    a.setActionBarTitle(title)
}



fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, tag: String) {
    supportFragmentManager.inTransaction { add(frameId, fragment, tag) }
    Log.d(applicationContext.packageCodePath, "$fragment added to supportFragmentManager")
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, tag: String) {
    supportFragmentManager.inTransaction { replace(frameId, fragment, tag) }
    Log.d(applicationContext.packageCodePath, "$fragment added to supportFragmentManager")
}

fun AppCompatActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction { remove(fragment) }
    Log.d(applicationContext.packageCodePath, "$fragment removed from supportFragmentManager")
}