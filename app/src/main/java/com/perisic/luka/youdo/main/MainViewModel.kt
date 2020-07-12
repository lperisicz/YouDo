package com.perisic.luka.youdo.main

import android.location.Location
import androidx.lifecycle.ViewModel
import com.perisic.luka.data.repo.AuthRepository
import com.perisic.luka.data.repo.LocationRepository
import com.perisic.luka.data.repo.UserRepository

internal class MainViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    fun logout() = authRepository.logout()

    fun getUserData() = userRepository.getUserData()

    fun updateLocation(location: Location) {
        locationRepository.updateRecentLocation(
            latitude = location.latitude,
            longitude = location.longitude
        )
    }

}