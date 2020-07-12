package com.perisic.luka.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.perisic.luka.data.local.dao.*
import com.perisic.luka.data.local.model.*

@Database(
    entities = [
        Token::class,
        User::class,
        Contact::class,
        Taxonomy::class,
        Location::class
    ],
    version = 5,
    exportSchema = false
)
internal abstract class LocalDatabase : RoomDatabase() {

    abstract fun tokenDao(): TokenDao

    abstract fun userDao(): UserDao

    abstract fun contactDao(): ContactDao

    abstract fun taxonomyDao(): TaxonomyDao

    abstract fun locationsDao(): LocationsDao

}