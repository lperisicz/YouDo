package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.local.dao.LocationsDao
import com.perisic.luka.data.local.model.Location

internal class LocationRepositoryImpl(
    private val locationsDao: LocationsDao
) : LocationRepository {


    override fun getRecentLocation(): LiveData<Location> {
        return locationsDao.getRecentLocation()
    }

    override fun updateRecentLocation(latitude: Double, longitude: Double) {
        locationsDao.insert(
            Location(
                latitude = latitude,
                longitude = longitude
            )
        )
    }
}