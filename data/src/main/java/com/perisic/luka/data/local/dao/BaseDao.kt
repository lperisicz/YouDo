package com.perisic.luka.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dataList: List<T>)

    @Update
    fun update(data: T)

    @Update
    fun update(dataList: List<T>)

    @Delete
    fun delete(data: T)

    @Delete
    fun delete(dataList: List<T>)

}