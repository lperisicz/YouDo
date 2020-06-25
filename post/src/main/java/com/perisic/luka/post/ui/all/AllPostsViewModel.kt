package com.perisic.luka.post.ui.all

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.data.repo.PostRepository

class AllPostsViewModel(
    postRepository: PostRepository
) : ViewModel() {

    private val request = MutableLiveData(null)
    private val pagedResponse = map(request) {
        postRepository.getAllPosts()
    }
    val postsResponse = switchMap(pagedResponse) { it.pagedList }
    val networkState = switchMap(pagedResponse) { it.networkState }

}