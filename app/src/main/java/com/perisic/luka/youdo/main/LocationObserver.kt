package com.perisic.luka.youdo.main

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

internal class LocationObserver(
    private val context: Context,
    private val lifecycle: Lifecycle,
    private val callback: (Location) -> Unit
) : LifecycleObserver {

    private var enabled = false
    private var locationManager: LocationManager? = null
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            println("LOCATION -> " + location.longitude + ":" + location.latitude)
            callback(location)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setupListener() {
        locationManager = context
            .getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager?
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun connectListener() {
        if (enabled) {
            try {
                // Request location updates
                locationManager?.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0f,
                    locationListener
                )
            } catch (ex: SecurityException) {
                println("LOCATION -> Security Exception, no location available")
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun disconnectListener() {
        if (enabled) {
            locationManager?.removeUpdates(locationListener)
        }
    }

    fun enable() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && !enabled) {
            // connect if not connected
            try {
                locationManager?.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0f,
                    locationListener
                )
            } catch (ex: SecurityException) {
                println("LOCATION -> Security Exception, no location available")
            }
        }
        enabled = true
    }

}