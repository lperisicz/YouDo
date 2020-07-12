package com.perisic.luka.base.extensions

import android.Manifest
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavDestination
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import java.lang.ref.WeakReference

fun setupWithNavController(
    navigationView: NavigationView,
    navController: NavController,
    callback: (Int) -> Unit = {}
) {
    navigationView.setNavigationItemSelectedListener { item ->
        val handled =
            NavigationUI.onNavDestinationSelected(item, navController)
        if (handled) {
            val parent = navigationView.parent
            if (parent is DrawerLayout) {
                parent.closeDrawer(navigationView)
            } else {
                val bottomSheetBehavior = findBottomSheetBehavior(navigationView)
                if (bottomSheetBehavior != null) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        } else {
            callback(item.itemId)
            val parent = navigationView.parent
            if (parent is DrawerLayout) {
                parent.closeDrawer(navigationView)
            } else {
                val bottomSheetBehavior = findBottomSheetBehavior(navigationView)
                if (bottomSheetBehavior != null) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        }
        handled
    }
    val weakReference =
        WeakReference(navigationView)
    navController.addOnDestinationChangedListener(
        object : OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination, arguments: Bundle?
            ) {
                val view = weakReference.get()
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this)
                    return
                }
                val menu = view.menu
                var h = 0
                val size = menu.size()
                while (h < size) {
                    val item = menu.getItem(h)
                    item.isChecked = matchDestination(destination, item.itemId)
                    h++
                }
            }
        })
}

fun findBottomSheetBehavior(view: View): BottomSheetBehavior<*>? {
    val params = view.layoutParams
    if (params !is CoordinatorLayout.LayoutParams) {
        val parent = view.parent
        return if (parent is View) {
            findBottomSheetBehavior(parent as View)
        } else null
    }
    val behavior =
        params
            .behavior
    return if (behavior !is BottomSheetBehavior<*>) {
        // We hit a CoordinatorLayout, but the View doesn't have the BottomSheetBehavior
        null
    } else behavior
}

fun matchDestination(
    destination: NavDestination,
    @IdRes destId: Int
): Boolean {
    var currentDestination: NavDestination? = destination
    while (currentDestination!!.id != destId && currentDestination.parent != null) {
        currentDestination = currentDestination.parent
    }
    return currentDestination.id == destId
}

object Animator {

    private var maxHeight = 0
    private const val duration = 250L

    fun View.collapse() {
        if (maxHeight == 0) {
            maxHeight = measuredHeight
        }
        if (layoutParams.height != 0) {
            val collapseAnimator = ValueAnimator
                .ofInt(maxHeight, 0)
                .setDuration(duration)
            collapseAnimator.addUpdateListener {
                layoutParams.height = it.animatedValue as Int
                requestLayout()
            }
            collapseAnimator.start()
        }
    }

    fun View.expand() {
        if (layoutParams.height == 0) {
            val expandAnimator = ValueAnimator
                .ofInt(0, maxHeight)
                .setDuration(duration)
            expandAnimator.addUpdateListener {
                layoutParams.height = it.animatedValue as Int
                requestLayout()
            }
            expandAnimator.start()
        }
    }

}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment.callNumber(number: String) {
    startActivity(
        Intent(Intent.ACTION_DIAL, Uri.parse("tel:${number}"))
    )
}

fun Fragment.sendMail(emailAddress: String) {
    startActivity(
        Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${emailAddress}"))
    )
}

@MainThread
fun <X, Y> mapMutable(
    source: LiveData<X>,
    mapFunction: (X) -> Y
): MediatorLiveData<Y> {
    val result = MediatorLiveData<Y>()
    result.addSource(
        source
    ) { x -> result.value = mapFunction.invoke(x) }
    return result
}

fun AppCompatActivity.requestLocationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 123)
    } else {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            123
        )
    }
}

fun Context.hasLocationPermissions(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

}