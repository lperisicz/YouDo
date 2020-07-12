package com.perisic.luka.youdo.di

import com.perisic.luka.youdo.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MainModule = module {

    viewModel {
        MainViewModel(
            authRepository = get(),
            userRepository = get(),
            locationRepository = get()
        )
    }

}