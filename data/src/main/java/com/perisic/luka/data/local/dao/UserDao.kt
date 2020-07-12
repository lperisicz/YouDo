package com.perisic.luka.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.perisic.luka.data.local.model.User

@Dao
internal interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM users LIMIT 1")
    fun getUserData(): LiveData<User>

}