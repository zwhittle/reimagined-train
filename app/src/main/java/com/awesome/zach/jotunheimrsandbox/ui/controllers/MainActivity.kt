package com.awesome.zach.jotunheimrsandbox.ui.controllers

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.get
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.databinding.ActivityMainBinding
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ListListViewModel
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ProjectListViewModel
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.TagListViewModel
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.TaskListViewModel
import com.awesome.zach.jotunheimrsandbox.utils.Utils.hideSoftKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val taskListViewModel by viewModel<TaskListViewModel>()
    private val projectListViewModel by viewModel<ProjectListViewModel>()
    private val tagListViewModel by viewModel<TagListViewModel>()
    private val listListViewModel by viewModel<ListListViewModel>()

    companion object {
        const val LOG_TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this,
                                                     R.id.nav_host_fragment)
        Navigation.setViewNavController(fab,
                                        navController)
        appBarConfiguration = AppBarConfiguration(navController.graph,
                                                  binding.layoutDrawer)

        setupActionBarWithNavController(navController,
                                        appBarConfiguration)

        binding.navigationView.setupWithNavController(navController)

        fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.newTaskFragment))
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            destinationListener(controller,
                                destination,
                                arguments)
        }
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun attachDrawerListener(listener: DrawerLayout.DrawerListener) {
        layoutDrawer.addDrawerListener(listener)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun destinationListener(controller: NavController,
                                    destination: NavDestination,
                                    arguments: Bundle?) {

        val graph = navController.graph
        when (destination) {
            graph[R.id.taskListFragment]          -> {
                showFab()
                setActionBarTitle(getString(R.string.all_tasks))
            }
            graph[R.id.tagListFragment]           -> {
                hideFab()
                setActionBarTitle(getString(R.string.all_tags))
            }
            graph[R.id.projectListFragment]       -> {
                hideFab()
                setActionBarTitle(getString(R.string.all_projects))
            }
            graph[R.id.listListFragment]          -> {
                hideFab()
                setActionBarTitle("All Lists")
            }
            graph[R.id.newTaskFragment]           -> {
                hideFab()
                setActionBarTitle(getString(R.string.new_task))
            }
            graph[R.id.createEditProjectFragment] -> {
                hideFab()
                setActionBarTitle(getString(R.string.new_project))
            }
            graph[R.id.createEditTagFragment]     -> {
                hideFab()
                setActionBarTitle(getString(R.string.new_tag))
            }
            graph[R.id.homeFragment]              -> {
                showFab()
                setActionBarTitle(getString(R.string.app_name))
            }
        }

        hideSoftKeyboard(this)
    }

    private fun showFab() {
        fab.visibility = View.VISIBLE
    }

    private fun hideFab() {
        fab.visibility = View.GONE
    }
}
