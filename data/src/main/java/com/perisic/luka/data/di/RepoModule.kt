package com.perisic.luka.data.di

import com.perisic.luka.data.repo.*
import org.koin.dsl.module

val RepoModule = module {

    single<AuthRepository> {
        AuthRepositoryImpl(
            authService = get(),
            tokenDao = get(),
            localDatabase = get()
        )
    }

    single<PostRepository> {
        PostRepositoryImpl(
            postService = get()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(
            userService = get(),
            userDao = get(),
            contactDao = get(),
            taxonomyDao = get()
        )
    }

    single<TaxonomyRepository> {
        TaxonomyRepositoryImpl(
            taxonomyService = get(),
            taxonomyDao = get()
        )
    }

    single<ContactRepository> {
        ContactRepositoryImpl(
            contactDao = get()
        )
    }

    single<LocationRepository> {
        LocationRepositoryImpl(
            locationsDao = get()
        )
    }

}