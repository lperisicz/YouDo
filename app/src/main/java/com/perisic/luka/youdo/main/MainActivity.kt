package com.perisic.luka.youdo.main

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.perisic.luka.base.extensions.Animator.collapse
import com.perisic.luka.base.extensions.Animator.expand
import com.perisic.luka.base.extensions.hasLocationPermissions
import com.perisic.luka.base.extensions.hideKeyboard
import com.perisic.luka.base.extensions.requestLocationPermission
import com.perisic.luka.base.extensions.setupWithNavController
import com.perisic.luka.youdo.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val viewModel by viewModel<MainViewModel>()
    private val startDestinations = setOf(
        R.id.allPostsFragment,
        R.id.myPostsFragment,
        R.id.userFragment
    )

    private lateinit var locationObserver: LocationObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupLocationTracker()
    }

    private fun setupLocationTracker() {
        locationObserver = LocationObserver(this, lifecycle, viewModel::updateLocation)
        lifecycle.addObserver(locationObserver)
        if (hasLocationPermissions()) {
            locationObserver.enable()
        } else {
            requestLocationPermission()
        }
    }

    private fun observeData() {
        viewModel.getUserData().observe(this, Observer {
            if (it == null) {
                navController.navigate(
                    R.id.nav_graph_auth,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.nav_graph_post_all, true)
                        .setLaunchSingleTop(true)
                        .build()
                )
            } else {
                if (navController.currentDestination?.id == R.id.loginFragment) {
                    navController.navigate(
                        R.id.nav_graph_post_all,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.nav_graph_auth, true)
                            .setLaunchSingleTop(true)
                            .build()
                    )
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.getOrNull(0) == PackageManager.PERMISSION_GRANTED) {
            locationObserver.enable()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupNavConfiguration()
        setupToolbar()
        setupNavigationMenu()
        setupBottomNavigation()
        observeData()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        currentFocus?.hideKeyboard()
        if (startDestinations.contains(destination.id)) {
            bottomNavigationHome.expand()
        } else {
            bottomNavigationHome.collapse()
        }
        if (destination.id == R.id.loginFragment) {
            toolbarMain.collapse()
        } else {
            toolbarMain.expand()
        }
    }

    private fun setupNavConfiguration() {
        navController = navHostFragment.findNavController()
        navController.addOnDestinationChangedListener(this)
        appBarConfiguration = AppBarConfiguration(
            startDestinations,
            drawerLayoutMain
        )
    }

    private fun setupToolbar() {
        NavigationUI.setupWithNavController(toolbarMain, navController, appBarConfiguration)
    }

    private fun setupNavigationMenu() {
        setupWithNavController(navigationViewMain, navController) {
            if (it == R.id.action_logout) {
                viewModel.logout()
            }
        }
    }

    private fun setupBottomNavigation() {
        NavigationUI.setupWithNavController(bottomNavigationHome, navController)
    }
}
