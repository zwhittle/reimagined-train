package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.databinding.ActivityMainBinding
import com.awesome.zach.jotunheimrsandbox.utils.addFragment
import com.awesome.zach.jotunheimrsandbox.utils.replaceFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var activeFragment = ""

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        const val FRAGMENT_TAG_LIST = "TagListFragment"
        const val FRAGMENT_PROJECT_LIST = "ProjectListFragment"
        const val FRAGMENT_TASK_LIST = "TaskListFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        val binding = setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // addFragment(TagListFragment(), R.id.layoutContainerMain, FRAGMENT_TAG_LIST)
        activeFragment = FRAGMENT_TAG_LIST

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, null)

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navigationView.setupWithNavController(navController)

        fab.setOnClickListener {
            swapFragments()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
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
