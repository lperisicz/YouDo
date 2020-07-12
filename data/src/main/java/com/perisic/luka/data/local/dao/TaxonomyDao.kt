package com.perisic.luka.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.perisic.luka.data.local.model.Taxonomy

@Dao
internal interface TaxonomyDao : BaseDao<Taxonomy> {

    @Query("SELECT * FROM taxonomies")
    fun getAll(): LiveData<List<Taxonomy>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnoreConflict(dataList: List<Taxonomy>)

    @Query("UPDATE taxonomies SET selected = :selected")
    fun batchSelectUpdate(selected: Boolean)

    @Transaction
    fun updateSelected(dataList: List<Taxonomy>) {
        batchSelectUpdate(selected = false)
        update(dataList)
    }

}