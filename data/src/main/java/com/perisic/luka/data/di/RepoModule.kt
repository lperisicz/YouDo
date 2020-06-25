package com.perisic.luka.data.di

import com.perisic.luka.data.repo.AuthRepository
import com.perisic.luka.data.repo.AuthRepositoryImpl
import com.perisic.luka.data.repo.PostRepository
import com.perisic.luka.data.repo.PostRepositoryImpl
import org.koin.dsl.module

val RepoModule = module {

    single<AuthRepository> {
        AuthRepositoryImpl(
            authService = get()
        )
    }

    single<PostRepository> {
        PostRepositoryImpl(
            postService = get()
        )
    }

}