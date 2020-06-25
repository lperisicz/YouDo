package com.perisic.luka.data.local.di

import androidx.room.Room
import com.perisic.luka.data.local.LocalDatabase
import org.koin.dsl.module

val LocalModule = module {

    single {
        Room.databaseBuilder(
            get(),
            LocalDatabase::class.java,
            "gemDatabase"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<LocalDatabase>().tokenDao()
    }

}