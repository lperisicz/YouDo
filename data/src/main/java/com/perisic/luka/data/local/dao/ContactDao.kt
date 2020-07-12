package com.perisic.luka.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.perisic.luka.data.local.model.Contact

@Dao
internal interface ContactDao : BaseDao<Contact> {

    @Query("SELECT * FROM contacts")
    fun getAll(): LiveData<List<Contact>>

}