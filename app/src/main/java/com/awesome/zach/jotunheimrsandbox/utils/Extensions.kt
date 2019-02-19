package com.awesome.zach.jotunheimrsandbox.utils

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


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

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
    Log.d(applicationContext.packageCodePath, fragment.toString() + " added to supportFragmentManager")
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
    Log.d(applicationContext.packageCodePath, fragment.toString() + " added to supportFragmentManager")
}

fun AppCompatActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction { remove(fragment) }
    Log.d(applicationContext.packageCodePath, fragment.toString() + " removed from supportFragmentManager")
}