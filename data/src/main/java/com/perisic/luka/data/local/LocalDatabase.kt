package com.perisic.luka.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.perisic.luka.data.local.dao.TokenDao
import com.perisic.luka.data.local.model.Token

@Database(
    entities = [
        Token::class
    ],
    version = 1,
    exportSchema = false
)
internal abstract class LocalDatabase : RoomDatabase() {

    abstract fun tokenDao(): TokenDao

}