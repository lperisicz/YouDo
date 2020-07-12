package com.perisic.luka.auth.di

import com.perisic.luka.auth.ui.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AuthModule = module {

    viewModel {
        LoginViewModel(
            authRepository = get(),
            userRepository = get(),
            taxonomyRepository = get()
        )
    }

}