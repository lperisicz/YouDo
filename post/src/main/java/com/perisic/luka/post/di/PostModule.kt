package com.perisic.luka.post.di

import com.perisic.luka.post.ui.all.AllPostsViewModel
import com.perisic.luka.post.ui.create.CreatePostViewModel
import com.perisic.luka.post.ui.my.MyPostsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val PostModule = module {

    viewModel {
        AllPostsViewModel(
            postRepository = get(),
            locationRepository = get()
        )
    }

    viewModel {
        MyPostsViewModel(
            postRepository = get(),
            userRepository = get()
        )
    }

    viewModel {
        CreatePostViewModel(
            postRepository = get(),
            locationRepository = get()
        )
    }

}