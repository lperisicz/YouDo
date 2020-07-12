package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.local.model.Location

interface LocationRepository {

    fun getRecentLocation(): LiveData<Location>

    fun updateRecentLocation(latitude: Double, longitude: Double)

}