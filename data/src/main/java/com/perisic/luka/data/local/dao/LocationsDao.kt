package com.perisic.luka.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.perisic.luka.data.local.model.Location

@Dao
internal interface LocationsDao : BaseDao<Location> {

    @Query("SELECT * FROM locations LIMIT 1")
    fun getRecentLocation(): LiveData<Location>

}