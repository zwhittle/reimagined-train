package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.utils.addFragment
import com.awesome.zach.jotunheimrsandbox.utils.replaceFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var activeFragment = ""

    companion object {
        const val FRAGMENT_TAG_LIST = "TagListFragment"
        const val FRAGMENT_PROJECT_LIST = "ProjectListFragment"
        const val FRAGMENT_TASK_LIST = "TaskListFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // addFragment(TagListFragment(), R.id.layoutContainerMain, FRAGMENT_TAG_LIST)
        activeFragment = FRAGMENT_TAG_LIST

        fab.setOnClickListener {
            swapFragments()
        }
    }

    private fun swapFragments() {
        activeFragment = when (activeFragment) {
            FRAGMENT_TAG_LIST -> {
                // replaceFragment(ProjectListFragment(), R.id.layoutContainerMain, FRAGMENT_PROJECT_LIST)
                FRAGMENT_PROJECT_LIST
            }
            FRAGMENT_PROJECT_LIST -> {
                // replaceFragment(TaskListFragment(), R.id.layoutContainerMain, FRAGMENT_TASK_LIST)
                FRAGMENT_TASK_LIST
            }
            else -> {
                // replaceFragment(TagListFragment(), R.id.layoutContainerMain, FRAGMENT_TAG_LIST)
                FRAGMENT_TAG_LIST
            }
        }

    }
}
