package com.perisic.luka.youdo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupNavConfiguration()
        setupToolbar()
        setupNavigationMenu()
        setupBottomNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupNavConfiguration() {
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.allPostsFragment,
                R.id.myPostsFragment,
                R.id.userFragment
            ), drawerLayoutMain
        )
    }

    private fun setupToolbar() {
        NavigationUI.setupWithNavController(toolbarMain, navController, appBarConfiguration)
    }

    private fun setupNavigationMenu() {
        NavigationUI.setupWithNavController(navigationViewMain, navController)
    }

    private fun setupBottomNavigation() {
        NavigationUI.setupWithNavController(bottomNavigationHome, navController)
    }
}
