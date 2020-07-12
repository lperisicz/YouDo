package com.perisic.luka.user.di

import com.perisic.luka.user.ui.UserViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val UserModule = module {

    viewModel {
        UserViewModel(
            userRepository = get(),
            contactRepository = get(),
            taxonomyRepository = get()
        )
    }

}